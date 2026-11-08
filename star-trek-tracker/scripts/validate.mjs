// Validates data/tech.json. Zero dependencies.
//
//   node scripts/validate.mjs                    check data/tech.json
//   node scripts/validate.mjs --base old.json    also check history is append-only vs old.json
//
// Also imported by the site build and the weekly updater.

import { readFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { dirname, join } from 'node:path';
import { STATUSES } from './statuses.mjs';

const ISO_DATE = /^\d{4}-\d{2}-\d{2}$/;
const ID = /^[a-z0-9]+(?:-[a-z0-9]+)*$/;

const isHttps = (u) => {
  if (typeof u !== 'string') return false;
  try {
    return new URL(u).protocol === 'https:';
  } catch {
    return false;
  }
};
const isDate = (d) => typeof d === 'string' && ISO_DATE.test(d) && !Number.isNaN(Date.parse(d));
const text = (v, max) => typeof v === 'string' && v.trim().length > 0 && v.length <= max;

export function validateData(data) {
  const errors = [];
  const err = (where, msg) => errors.push(`${where}: ${msg}`);

  if (!data || !Array.isArray(data.technologies)) {
    return ['root: expected an object with a "technologies" array'];
  }

  const ids = new Set();
  for (const t of data.technologies) {
    const at = `tech "${t?.id ?? '?'}"`;
    if (!ID.test(t.id ?? '')) err(at, 'id must be lowercase kebab-case');
    if (ids.has(t.id)) err(at, 'duplicate id');
    ids.add(t.id);
    if (!text(t.name, 80)) err(at, 'name missing or too long');
    if (!text(t.trek_description, 320)) err(at, 'trek_description missing or over 320 characters');
    if (!text(t.closest_today, 320)) err(at, 'closest_today missing or over 320 characters');
    if (!Array.isArray(t.capabilities) || t.capabilities.length === 0) {
      err(at, 'needs at least one capability');
      continue;
    }
    if (!Array.isArray(t.history)) {
      err(at, 'history must be an array');
      continue;
    }

    const caps = new Map();
    for (const c of t.capabilities) {
      const cat = `${at} / "${c?.name ?? '?'}"`;
      if (!text(c.name, 90)) err(cat, 'name missing or too long');
      if (caps.has(c.name)) err(cat, 'duplicate capability name');
      caps.set(c.name, c);
      if (!STATUSES.includes(c.status)) err(cat, `status must be one of ${STATUSES.join(', ')}`);
      if (!text(c.note, 300)) err(cat, 'note missing or over 300 characters');
      if (!isDate(c.updated)) err(cat, 'updated must be YYYY-MM-DD');
      if (c.status !== 'fiction' && !isHttps(c.evidence_url)) {
        err(cat, `status "${c.status}" requires an https evidence_url`);
      }
      if (c.evidence_url != null && !isHttps(c.evidence_url)) err(cat, 'evidence_url must be https or null');
    }

    // History: chronological, chained per capability, ends at the current status.
    const last = new Map();
    let prevDate = '';
    t.history.forEach((h, i) => {
      const hat = `${at} history[${i}]`;
      if (!isDate(h.date)) err(hat, 'date must be YYYY-MM-DD');
      if (h.date < prevDate) err(hat, 'history must be in chronological order');
      prevDate = h.date;
      if (!caps.has(h.capability)) err(hat, `unknown capability "${h.capability}"`);
      if (h.from !== null && !STATUSES.includes(h.from)) err(hat, '"from" must be null or a status');
      if (!STATUSES.includes(h.to)) err(hat, '"to" must be a status');
      if (!text(h.reason, 500)) err(hat, 'reason missing or over 500 characters');
      if (!isHttps(h.source_url)) err(hat, 'source_url must be https');
      const prev = last.get(h.capability);
      if (prev === undefined && h.from !== null && h.from !== 'fiction') {
        err(hat, 'first entry for a capability must start from null or "fiction"');
      }
      if (prev !== undefined && h.from !== prev) err(hat, `"from" is ${h.from} but the previous entry ended at ${prev}`);
      if (h.from === h.to) err(hat, '"from" and "to" are the same');
      last.set(h.capability, h.to);
    });
    for (const c of t.capabilities) {
      const end = last.get(c.name);
      if (c.status !== 'fiction' && end === undefined) err(`${at} / "${c.name}"`, 'non-fiction capability has no history entry');
      if (end !== undefined && end !== c.status) {
        err(`${at} / "${c.name}"`, `status is ${c.status} but history ends at ${end}`);
      }
    }
  }
  return errors;
}

// History may only grow: every entry in the old file must still be there, unchanged, in order.
export function checkAppendOnly(oldData, newData) {
  const errors = [];
  const byId = new Map(newData.technologies.map((t) => [t.id, t]));
  for (const old of oldData.technologies) {
    const now = byId.get(old.id);
    if (!now) {
      errors.push(`tech "${old.id}": removed (history is append-only)`);
      continue;
    }
    old.history.forEach((h, i) => {
      if (JSON.stringify(now.history[i]) !== JSON.stringify(h)) {
        errors.push(`tech "${old.id}" history[${i}]: changed or removed (history is append-only)`);
      }
    });
  }
  return errors;
}

export const DATA_PATH = join(dirname(fileURLToPath(import.meta.url)), '..', 'data', 'tech.json');

export function loadData(path = DATA_PATH) {
  return JSON.parse(readFileSync(path, 'utf8'));
}

if (process.argv[1] === fileURLToPath(import.meta.url)) {
  const args = process.argv.slice(2);
  const data = loadData();
  let errors = validateData(data);
  const bi = args.indexOf('--base');
  if (bi !== -1) errors = errors.concat(checkAppendOnly(loadData(args[bi + 1]), data));
  if (errors.length) {
    console.error(`data/tech.json: ${errors.length} problem(s)\n` + errors.map((e) => `  - ${e}`).join('\n'));
    process.exit(1);
  }
  const caps = data.technologies.reduce((n, t) => n + t.capabilities.length, 0);
  console.log(`data/tech.json OK: ${data.technologies.length} technologies, ${caps} capabilities`);
}
