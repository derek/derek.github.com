// Static site build. Zero dependencies.
//   node scripts/build.mjs
// Reads data/tech.json, validates it, and writes index.html and tech/<id>/index.html
// into the star-trek-tracker/ directory (the directory GitHub Pages serves).
// style.css and filter.js are hand-written and live next to index.html.

import { mkdirSync, rmSync, writeFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { dirname, join } from 'node:path';
import { STATUSES, LABELS, DEFINITIONS } from './statuses.mjs';
import { validateData, loadData } from './validate.mjs';

const ROOT = join(dirname(fileURLToPath(import.meta.url)), '..');

const esc = (s) =>
  String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#39;');

const MONTHS = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
const date = (d) => {
  const [y, m, day] = d.split('-').map(Number);
  return `<time datetime="${d}">${day} ${MONTHS[m - 1]} ${y}</time>`;
};
const host = (u) => new URL(u).hostname.replace(/^www\./, '');
const badge = (status) => `<span class="b s-${status}">${LABELS[status]}</span>`;

// Most real first, for the filter and the tally.
const DISPLAY_ORDER = [...STATUSES].reverse();

const FAVICON =
  "data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 32 32'%3E%3Crect width='32' height='32' rx='6' fill='%23111'/%3E%3Cpath d='M16 5l3.2 8.3L28 14l-6.8 5.5L23.5 28 16 23.2 8.5 28l2.3-8.5L4 14l8.8-.7z' fill='%23fff'/%3E%3C/svg%3E";

function page({ title, description, depth, body, extraHead = '', script = '' }) {
  const up = '../'.repeat(depth);
  return `<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>${esc(title)}</title>
<meta name="description" content="${esc(description)}">
<meta name="color-scheme" content="light dark">
<link rel="icon" href="${FAVICON}">
<script>document.documentElement.className="js"</script>
<link rel="stylesheet" href="${up}style.css">
${extraHead}</head>
<body>
${body}
${script}</body>
</html>
`;
}

function footer(data, depth) {
  const up = '../'.repeat(depth);
  const latest = data.technologies.flatMap((t) => t.capabilities.map((c) => c.updated)).sort().pop();
  return `<footer>
<p>Last assessment: ${date(latest)}. Ratings come from <a href="${up}data/tech.json">data/tech.json</a>, and every change is logged with a reason and a source.</p>
<p>An unofficial fan project. Star Trek is a trademark of CBS Studios Inc. and Paramount. Descriptions here are written in our own words.</p>
</footer>`;
}

function indexPage(data) {
  const caps = data.technologies.flatMap((t) => t.capabilities);
  const tally = Object.fromEntries(STATUSES.map((s) => [s, caps.filter((c) => c.status === s).length]));

  const legend = DISPLAY_ORDER.map((s) => `<div><dt>${badge(s)}</dt><dd>${esc(DEFINITIONS[s])}</dd></div>`).join('\n');
  const tallyHtml = DISPLAY_ORDER.map((s) => `<li>${badge(s)} <span>${tally[s]}</span></li>`).join('');

  const cards = data.technologies
    .map((t) => {
      const rows = t.capabilities
        .map((c) => `<li data-status="${c.status}"><span class="n">${esc(c.name)}</span>${badge(c.status)}</li>`)
        .join('');
      return `<li class="card"><h3><a href="tech/${t.id}/">${esc(t.name)}</a></h3><ul class="caps">${rows}</ul></li>`;
    })
    .join('\n');

  const options = JSON.stringify(DISPLAY_ORDER.map((s) => [s, LABELS[s]]));

  const body = `<a class="skip" href="#grid">Skip to the technologies</a>
<header>
<h1>Trek Readiness</h1>
<p class="lede">How close is real-world technology to Star Trek's? Each technology is rated one capability at a time, because a tricorder-like sensor can be on sale while tricorder-like diagnosis is still a lab project.</p>
</header>
<main>
<section aria-labelledby="legend-h">
<h2 id="legend-h">How to read the ratings</h2>
<dl class="legend">
${legend}
</dl>
<h2>At a glance</h2>
<p class="small">${caps.length} capabilities across ${data.technologies.length} technologies.</p>
<ul class="tally">${tallyHtml}</ul>
</section>
<section aria-labelledby="tech-h">
<h2 id="tech-h">Technologies</h2>
<div class="filters" id="filters" data-options="${esc(options)}"></div>
<p id="count" aria-live="polite"></p>
<ul class="grid" id="grid">
${cards}
</ul>
</section>
</main>
${footer(data, 0)}`;

  return page({
    title: 'Trek Readiness',
    description: "Tracking how close real-world technology is to Star Trek's, one capability at a time.",
    depth: 0,
    body,
    script: '<script src="filter.js" defer></script>\n',
  });
}

function detailPage(data, t) {
  const capHtml = t.capabilities
    .map(
      (c) => `<li>
<h3>${esc(c.name)}</h3>
<div class="meta">${badge(c.status)}<span class="small">Assessed ${date(c.updated)}</span></div>
<p>${esc(c.note)}</p>
${
  c.evidence_url
    ? `<p class="small">Evidence: <a href="${esc(c.evidence_url)}" rel="noopener">${esc(host(c.evidence_url))}</a></p>`
    : '<p class="small">No evidence link: nothing real to point to.</p>'
}
</li>`
    )
    .join('\n');

  const entries = [...t.history].reverse();
  const histHtml = entries.length
    ? `<ol class="timeline">
${entries
  .map((h) => {
    const what = h.from
      ? `moved from ${LABELS[h.from]} to ${LABELS[h.to]}`
      : `first rated ${LABELS[h.to]}`;
    return `<li><p>${date(h.date)} <strong>${esc(h.capability)}</strong> ${what}</p><p>${esc(h.reason)}</p><p class="small">Source: <a href="${esc(h.source_url)}" rel="noopener">${esc(host(h.source_url))}</a></p></li>`;
  })
  .join('\n')}
</ol>`
    : '<p>Nothing has changed yet.</p>';

  const body = `<a class="skip" href="#caps">Skip to capabilities</a>
<nav class="crumb" aria-label="Breadcrumb"><a href="../../">All technologies</a></nav>
<header>
<h1>${esc(t.name)}</h1>
<p class="trek">${esc(t.trek_description)}</p>
</header>
<main>
<h2>Closest today</h2>
<p class="closest">${esc(t.closest_today)}</p>
<h2 id="caps">Capabilities</h2>
<ul class="detail">
${capHtml}
</ul>
<h2>History: why ratings changed</h2>
${histHtml}
</main>
${footer(data, 2)}`;

  return page({
    title: `${t.name} | Trek Readiness`,
    description: `${t.name}: ${t.closest_today}`,
    depth: 2,
    body,
  });
}

export function build() {
  const data = loadData();
  const errors = validateData(data);
  if (errors.length) {
    console.error('Refusing to build, data/tech.json is invalid:\n' + errors.map((e) => `  - ${e}`).join('\n'));
    process.exit(1);
  }
  rmSync(join(ROOT, 'tech'), { recursive: true, force: true });
  writeFileSync(join(ROOT, 'index.html'), indexPage(data));
  for (const t of data.technologies) {
    const dir = join(ROOT, 'tech', t.id);
    mkdirSync(dir, { recursive: true });
    writeFileSync(join(dir, 'index.html'), detailPage(data, t));
  }
  console.log(`Built index.html and ${data.technologies.length} detail pages.`);
}

if (process.argv[1] === fileURLToPath(import.meta.url)) build();
