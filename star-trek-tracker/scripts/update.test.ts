// Run with: node --test scripts/*.test.ts
import { test } from 'node:test';
import assert from 'node:assert/strict';
import { parseFeed, selectStories, parseProposals, vet, applyChanges, plain, prBody } from './update.ts';
import { validateData, checkAppendOnly, loadData } from './validate.mjs';

const data = loadData();
const URL1 = 'https://example.com/a';
const base = {
  story_url: URL1,
  tech_id: 'tricorder',
  capability: 'Handheld medical imaging', // currently prototype
  proposed_status: 'shipping',
  new_note: 'Now sold directly to consumers online.',
  reason: 'The maker says anyone can now order the device online.',
  evidence_type: 'product_on_sale',
  confidence: 'high',
  consumer_can_buy_today: true,
  working_demonstration: true,
};
const run = (over: object, urls = new Set([URL1])) => vet([{ ...base, ...over }], data, urls, 8);

test('seed data is valid', () => {
  assert.deepEqual(validateData(data), []);
});

test('parses RSS with CDATA, entities and relative dates', () => {
  const xml = `<rss><channel><item><title><![CDATA[Robots &amp; more]]></title><link>http://news.test/x</link>
    <pubDate>Fri, 06 Nov 2026 10:00:00 GMT</pubDate><description>&lt;p&gt;Hello &amp;amp; welcome&lt;/p&gt;</description></item>
    <item><title>No date</title><link>https://news.test/y</link></item></channel></rss>`;
  const s = parseFeed(xml, 'Test');
  assert.equal(s.length, 1);
  assert.equal(s[0].title, 'Robots & more');
  assert.equal(s[0].url, 'https://news.test/x'); // upgraded to https
  assert.equal(s[0].published, '2026-11-06');
  assert.equal(s[0].summary, 'Hello & welcome');
});

test('parses Atom entries with href links', () => {
  const xml = `<feed><entry><title>Atom story</title><link rel="alternate" href="https://news.test/z"/>
    <updated>2026-11-05T08:00:00Z</updated><summary>Short.</summary></entry></feed>`;
  const s = parseFeed(xml, 'Atom');
  assert.equal(s[0].url, 'https://news.test/z');
  assert.equal(s[0].published, '2026-11-05');
});

test('selectStories drops old items and duplicates', () => {
  const mk = (url: string, published: string) => ({ title: 't', url, published, source: 's', summary: '' });
  const out = selectStories([mk('https://a.test/1', '2026-11-07'), mk('https://a.test/1', '2026-11-07'), mk('https://a.test/2', '2026-10-01')], '2026-11-08', 8, 50);
  assert.equal(out.length, 1);
});

test('parseProposals tolerates code fences and rejects junk', () => {
  assert.equal(parseProposals('```json\n{"proposals":[]}\n```').length, 0);
  assert.throws(() => parseProposals('no json here'));
  assert.throws(() => parseProposals('{"other":1}'));
});

test('accepts a well-supported one-step promotion', () => {
  assert.equal(run({}).accepted.length, 1);
});

test('rejects a source URL that was not fetched (hallucinated)', () => {
  assert.match(run({}, new Set(['https://example.com/other'])).rejected[0].why, /not one of the stories/);
});

test('rejects shipping unless a consumer can buy it today', () => {
  assert.equal(run({ consumer_can_buy_today: false }).accepted.length, 0);
  assert.equal(run({ evidence_type: 'working_demonstration' }).accepted.length, 0);
});

test('rejects prototype without a working demonstration', () => {
  const r = run({ tech_id: 'cloaking-device', capability: 'Hiding a small object from one band of light', proposed_status: 'prototype', evidence_type: 'working_demonstration', working_demonstration: false });
  assert.equal(r.accepted.length, 0);
});

test('rejects skipping a step, low confidence, same status, unknown capability', () => {
  assert.match(run({ tech_id: 'warp-drive', capability: 'Faster-than-light travel', proposed_status: 'prototype', working_demonstration: true, evidence_type: 'working_demonstration' }).rejected[0].why, /Skips a step/);
  assert.equal(run({ confidence: 'low' }).accepted.length, 0);
  assert.equal(run({ proposed_status: 'prototype' }).accepted.length, 0);
  assert.equal(run({ capability: 'Nope' }).accepted.length, 0);
});

test('downgrades need discontinued or refuted evidence', () => {
  const down = { proposed_status: 'research', consumer_can_buy_today: false };
  assert.equal(run({ ...down, evidence_type: 'product_on_sale' }).accepted.length, 0);
  assert.equal(run({ ...down, evidence_type: 'discontinued_or_refuted' }).accepted.length, 1);
});

test('one change per capability and a cap per run', () => {
  const two = vet([base, { ...base }], data, new Set([URL1]), 8);
  assert.equal(two.accepted.length, 1);
  const capped = vet([base, { ...base, tech_id: 'androids', capability: 'General-purpose household work' }], data, new Set([URL1]), 1);
  assert.equal(capped.accepted.length, 1);
  assert.match(capped.rejected[0].why, /cap/);
});

test('applyChanges appends history, updates the capability and stays valid and append-only', () => {
  const next = applyChanges(data, [base], '2026-11-15');
  const t = next.technologies.find((x: any) => x.id === 'tricorder');
  const cap = t.capabilities.find((c: any) => c.name === base.capability);
  assert.equal(cap.status, 'shipping');
  assert.equal(cap.evidence_url, URL1);
  const last = t.history[t.history.length - 1];
  assert.deepEqual([last.from, last.to, last.date, last.source_url], ['prototype', 'shipping', '2026-11-15', URL1]);
  assert.deepEqual(validateData(next), []);
  assert.deepEqual(checkAppendOnly(data, next), []);
  // original untouched
  assert.equal(data.technologies.find((x: any) => x.id === 'tricorder').capabilities.find((c: any) => c.name === base.capability).status, 'prototype');
});

test('append-only check catches rewritten history', () => {
  const bad = structuredClone(data);
  bad.technologies[0].history[0].reason = 'Edited later';
  assert.equal(checkAppendOnly(data, bad).length, 1);
});

test('validator rejects non-fiction without evidence and mismatched history', () => {
  const bad = structuredClone(data);
  bad.technologies[0].capabilities[0].evidence_url = null;
  assert.ok(validateData(bad).some((e) => /requires an https evidence_url/.test(e)));
  const bad2 = structuredClone(data);
  bad2.technologies[0].capabilities[0].status = 'prototype';
  assert.ok(validateData(bad2).some((e) => /history ends at/.test(e)));
});

test('untrusted text cannot ping people or link issues in the PR body', () => {
  assert.equal(plain('hey @octocat see #123 <b>x</b>'), 'hey (at)octocat see No. 123 bx/b');
  const body = prBody(data, [{ ...base, reason: 'Thanks @octocat for the scoop, see #5.' }], [], { scanned: 1, failures: [], today: '2026-11-15' });
  assert.ok(!body.includes('@octocat'));
});
