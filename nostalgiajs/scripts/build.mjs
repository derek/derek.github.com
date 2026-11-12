// Nostalgia.js build. No dependencies: `node scripts/build.mjs` from nostalgiajs/.
// Reads essays.json, archive.json and content/*.html, then writes every page,
// the landing page and the feed. Generated files are committed, because
// GitHub Pages serves this folder as it is.
import { readFileSync, writeFileSync, mkdirSync, existsSync, readdirSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = join(dirname(fileURLToPath(import.meta.url)), '..');
const SITE = 'https://derek.io/nostalgiajs/';
const NAME = 'Nostalgia.js';
const TAGLINE = 'AI Essays on Web History';
const BERRY = "'member when...? Yeah, I remember!"; // plain text, safe inside an attribute
const ISSUES = 'https://github.com/derek/derek.github.com/issues/new';

// Topic groups, in the order the landing page shows them.
const TOPICS = [
  ['browsers', 'Browsers and the platform'],
  ['language', 'The language'],
  ['libraries', 'Libraries and tools'],
  ['people', 'People and culture'],
];
const RESERVED = new Set(['about', 'content', 'scripts', 'og', 'index', 'feed']);

const read = (p) => readFileSync(join(ROOT, p), 'utf8');
const write = (p, s) => {
  mkdirSync(dirname(join(ROOT, p)), { recursive: true });
  writeFileSync(join(ROOT, p), s);
};
const esc = (s) => String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
const fail = (msg) => { console.error('build: ' + msg); process.exit(1); };

// ---------- load and validate ----------
const { essays } = JSON.parse(read('essays.json'));
const archive = existsSync(join(ROOT, 'archive.json')) ? JSON.parse(read('archive.json')) : {};
const topicName = Object.fromEntries(TOPICS);
const seen = new Set();

for (const e of essays) {
  for (const k of ['slug', 'title', 'description', 'topic', 'era', 'published']) {
    if (e[k] === undefined) fail(`${e.slug || '(no slug)'}: missing "${k}"`);
  }
  if (!/^[a-z0-9]+(-[a-z0-9]+)*$/.test(e.slug)) fail(`${e.slug}: slug must be lowercase words joined by hyphens`);
  if (/^\d{4}-\d\d/.test(e.slug)) fail(`${e.slug}: slugs do not start with a date`);
  if (RESERVED.has(e.slug)) fail(`${e.slug}: reserved name`);
  if (seen.has(e.slug)) fail(`${e.slug}: duplicate slug`);
  seen.add(e.slug);
  if (!topicName[e.topic]) fail(`${e.slug}: unknown topic "${e.topic}" (use ${TOPICS.map((t) => t[0]).join(', ')})`);
  if (!Array.isArray(e.era) || !Number.isInteger(e.era[0]) || !(e.era[1] === null || Number.isInteger(e.era[1]))) {
    fail(`${e.slug}: era must be [startYear, endYear] or [startYear, null] for "to today"`);
  }
  if (Number.isNaN(Date.parse(e.published))) fail(`${e.slug}: published must be an ISO date-time`);
  if (!existsSync(join(ROOT, 'content', e.slug + '.html'))) fail(`${e.slug}: content/${e.slug}.html is missing`);
  e.body = read(`content/${e.slug}.html`).trim();
  if (!e.body.startsWith('<p class="lede">')) fail(`${e.slug}: content must start with <p class="lede">`);
  if (!/<h2>The takeaway<\/h2>/.test(e.body)) fail(`${e.slug}: content needs a "The takeaway" section`);
  if (!/<section class="sources"/.test(e.body)) fail(`${e.slug}: content needs a sources section`);
  if (/<time[\s>]/.test(e.body)) fail(`${e.slug}: essays carry no publication date`);
  for (const m of e.body.matchAll(/href="\.\.\/([^"/#]+)\/?[^"]*"/g)) {
    if (!essays.some((x) => x.slug === m[1]) && m[1] !== 'about') fail(`${e.slug}: links to unknown essay "../${m[1]}/"`);
  }
  for (const r of e.related || []) {
    if (!essays.some((x) => x.slug === r)) fail(`${e.slug}: related essay "${r}" does not exist`);
  }
  const text = e.body.replace(/<pre[\s\S]*?<\/pre>/g, ' ').replace(/<section class="sources"[\s\S]*$/, ' ').replace(/<[^>]+>/g, ' ');
  e.minutes = Math.max(1, Math.round(text.split(/\s+/).filter(Boolean).length / 220));
  e.eraText = e.era[1] === null ? `${e.era[0]} to today` : e.era[0] === e.era[1] ? String(e.era[0]) : `${e.era[0]} to ${e.era[1]}`;
  e.url = SITE + e.slug + '/';
}
for (const f of readdirSync(join(ROOT, 'content'))) {
  if (f.endsWith('.html') && !f.startsWith('_') && !seen.has(f.slice(0, -5))) fail(`content/${f} has no entry in essays.json`);
}

// Order by the era an essay covers, earliest first, then by title.
const byEra = (a, b) => a.era[0] - b.era[0] || (a.era[1] ?? 9999) - (b.era[1] ?? 9999) || a.title.localeCompare(b.title);

// Related essays: explicit picks first, then the same topic, then the nearest era.
function related(e, n = 3) {
  const picks = (e.related || []).map((s) => essays.find((x) => x.slug === s));
  const rest = essays
    .filter((x) => x !== e && !picks.includes(x))
    .sort((a, b) => (a.topic === e.topic ? 0 : 1) - (b.topic === e.topic ? 0 : 1)
      || Math.abs(a.era[0] - e.era[0]) - Math.abs(b.era[0] - e.era[0]) || a.title.localeCompare(b.title));
  return [...picks, ...rest].slice(0, n);
}

// ---------- shared page parts ----------
const FAVICON = "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 32 32'%3E%3Crect width='32' height='32' rx='6' fill='%239a3412'/%3E%3Cpath d='M12 10l-6 6 6 6M20 10l6 6-6 6' fill='none' stroke='%23fff' stroke-width='3' stroke-linecap='round' stroke-linejoin='round'/%3E%3C/svg%3E";

function page({ path, title, description, up, ogImage, ogType = 'website', main }) {
  const canonical = SITE + path;
  return `<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
<title>${esc(title)}</title>
<meta name="description" content="${esc(description)}">
<link rel="canonical" href="${canonical}">
<meta property="og:site_name" content="${NAME}">
<meta property="og:type" content="${ogType}">
<meta property="og:title" content="${esc(title.replace(` · ${NAME}`, ''))}">
<meta property="og:description" content="${esc(description)}">
<meta property="og:url" content="${canonical}">
<meta property="og:image" content="${SITE}og/${ogImage}">
<meta property="og:image:width" content="1200">
<meta property="og:image:height" content="630">
<meta name="twitter:card" content="summary_large_image">
<link rel="icon" href="${FAVICON}">
<link rel="stylesheet" href="${up}style.css">
<link rel="alternate" type="application/rss+xml" title="${NAME}" href="${up}feed.xml">
</head>
<body>
<header class="bar">
  <div class="in">
    <a class="brand" href="${up || './'}">${NAME}<span>${TAGLINE}</span></a>
    <picture>
      <source srcset="${up}berry.png" media="(prefers-reduced-motion: reduce)">
      <img src="${up}berry.gif" width="48" height="48" alt="${BERRY}" title="${BERRY}">
    </picture>
  </div>
</header>
<nav class="strip" aria-label="${NAME}">
  <div class="in">
    <a href="${up || './'}">Essays</a>
    <a href="${up}about/">About</a>
    <a class="xml" href="${up}feed.xml"><img src="${up}buttons/xml.png" width="36" height="14" alt="RSS feed" title="RSS feed"></a>
  </div>
</nav>
<div class="wrap">
  <main>
${main}
  </main>

  <footer>
    <p class="buttons">
      <a href="${up || './'}"><img src="${up}buttons/nostalgiajs.png" width="88" height="31" alt="${NAME}"></a>
      <img src="${up}buttons/any-browser.png" width="88" height="31" alt="Best viewed with any browser">
      <img src="${up}buttons/notepad.png" width="88" height="31" alt="Made with Notepad">
    </p>
    <p>${NAME} · ${TAGLINE} · <a href="${up}about/">How these essays are made</a></p>
  </footer>
</div>
</body>
</html>
`;
}

// Add an "archived" link after each source that has a snapshot in archive.json.
function withArchiveLinks(body) {
  const i = body.indexOf('<section class="sources"');
  const sources = body.slice(i).replace(/<li>(\s*)<a href="([^"]+)">([\s\S]*?)<\/li>/g, (all, sp, href, rest) => {
    const snap = archive[href.replace(/&amp;/g, '&')];
    return snap ? `<li>${sp}<a href="${href}">${rest} <a class="archived" href="${esc(snap)}">(archived)</a></li>` : all;
  });
  return body.slice(0, i) + sources;
}

function issueLink(e) {
  const q = new URLSearchParams({
    title: `Correction: ${e.title}`,
    body: `Essay: ${e.url}\n\nWhat is wrong:\n\nWhat it should say, with a source if you have one:\n`,
  });
  return `${ISSUES}?${q}`;
}

// ---------- essay pages ----------
for (const e of essays) {
  const og = existsSync(join(ROOT, 'og', e.slug + '.png')) ? e.slug + '.png' : 'default.png';
  const corrections = (e.corrections || []).length
    ? `\n      <section class="corrections" aria-labelledby="corrections-h">
        <h2 id="corrections-h">Corrections</h2>
        <ul>
${e.corrections.map((c) => `          <li>${esc(c.date)}: ${esc(c.note)}</li>`).join('\n')}
        </ul>
      </section>\n`
    : '';
  const rel = related(e);
  const main = `    <article>
      <p class="meta">${esc(topicName[e.topic])} · ${e.eraText} · ${e.minutes} min read</p>
      <h1>${esc(e.title)}</h1>

${withArchiveLinks(e.body)}
${corrections}
      <p class="mistake">Spot a mistake? <a href="${esc(issueLink(e))}">Open an issue</a> and it will be checked and corrected.</p>
    </article>

    <nav class="related" aria-labelledby="related-h">
      <h2 id="related-h">Related essays</h2>
      <ul>
${rel.map((r) => `        <li><a href="../${r.slug}/">${esc(r.title)}</a> <span>${esc(topicName[r.topic])} · ${r.eraText}</span></li>`).join('\n')}
      </ul>
      <p><a href="../">All essays</a></p>
    </nav>`;
  write(`${e.slug}/index.html`, page({
    path: e.slug + '/', title: `${e.title} · ${NAME}`, description: e.description,
    up: '../', ogImage: og, ogType: 'article', main,
  }));
}

// ---------- landing page ----------
const groups = TOPICS.map(([id, label]) => [label, essays.filter((e) => e.topic === id).sort(byEra)]).filter(([, list]) => list.length);
write('index.html', page({
  path: '', title: `${NAME} · ${TAGLINE}`,
  description: `${TAGLINE}: what happened on the web from the 1990s to the 2010s, why it happened, and what it left behind.`,
  up: '', ogImage: 'default.png',
  main: `    <h1 class="hidden">${NAME}: ${TAGLINE}</h1>
    <p class="intro">Essays on the web we built by hand, from the 1990s through the 2010s: what happened, why, and what it left behind. Each one is written by an AI from sources it opened and checked, and the sources are linked. <a href="about/">More about how they are made</a>.</p>

${groups.map(([label, list]) => `    <section class="group" aria-labelledby="g-${list[0].topic}">
      <h2 id="g-${list[0].topic}">${esc(label)}</h2>
      <ul class="posts">
${list.map((e) => `        <li>
          <h3><a href="${e.slug}/">${esc(e.title)}</a></h3>
          <p class="meta">${e.eraText} · ${e.minutes} min read</p>
          <p>${esc(e.description)}</p>
        </li>`).join('\n')}
      </ul>
    </section>`).join('\n\n')}`,
}));

// ---------- about page ----------
write('about/index.html', page({
  path: 'about/', title: `About · ${NAME}`,
  description: `How the ${NAME} essays are written, checked and corrected.`,
  up: '../', ogImage: 'default.png',
  main: `    <article>
      <h1>About ${NAME}</h1>
${read('content/_about.html').trim()}
    </article>`,
}));

// ---------- feed (full text) ----------
const absolute = (html) => html.replace(/href="\.\.\/([^"]*)"/g, `href="${SITE}$1"`);
const newest = [...essays].sort((a, b) => Date.parse(b.published) - Date.parse(a.published)).slice(0, 30);
const rfc822 = (iso) => new Date(iso).toUTCString();
write('feed.xml', `<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom" xmlns:content="http://purl.org/rss/1.0/modules/content/">
<channel>
  <title>${NAME}</title>
  <link>${SITE}</link>
  <atom:link href="${SITE}feed.xml" rel="self" type="application/rss+xml"/>
  <description>${TAGLINE}</description>
  <language>en</language>
  <lastBuildDate>${rfc822(newest[0].published)}</lastBuildDate>
${newest.map((e) => `  <item>
    <title>${esc(e.title)}</title>
    <link>${e.url}</link>
    <guid isPermaLink="true">${e.url}</guid>
    <pubDate>${rfc822(e.published)}</pubDate>
    <description>${esc(e.description)}</description>
    <content:encoded><![CDATA[${absolute(withArchiveLinks(e.body)).replace(/]]>/g, ']]]]><![CDATA[>')}]]></content:encoded>
  </item>`).join('\n')}
</channel>
</rss>
`);

console.log(`built ${essays.length} essays, landing page, about page and feed`);
