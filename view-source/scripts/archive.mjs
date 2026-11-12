// Finds an Internet Archive copy for every source link in the essays and
// records it in archive.json. The build then adds an "(archived)" link beside
// each source. Run: `node scripts/archive.mjs` (add `--save` to ask the
// archive to capture pages it has no copy of). Needs curl and network access.
import { readFileSync, writeFileSync, readdirSync, existsSync } from 'node:fs';
import { execFileSync } from 'node:child_process';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const ROOT = join(dirname(fileURLToPath(import.meta.url)), '..');
const FILE = join(ROOT, 'archive.json');
const save = process.argv.includes('--save');
const archive = existsSync(FILE) ? JSON.parse(readFileSync(FILE, 'utf8')) : {};

const curl = (...args) => {
  try { return execFileSync('curl', ['-sS', '-m', '40', ...args], { encoding: 'utf8' }); } catch { return ''; }
};
function lookup(url) {
  const out = curl('-G', 'https://archive.org/wayback/available', '--data-urlencode', `url=${url}`);
  try {
    const snap = JSON.parse(out).archived_snapshots.closest;
    return snap && snap.available && snap.status === '200' ? snap.url.replace(/^http:/, 'https:') : null;
  } catch { return null; }
}

const urls = new Set();
for (const f of readdirSync(join(ROOT, 'content'))) {
  if (!f.endsWith('.html') || f.startsWith('_')) continue;
  const html = readFileSync(join(ROOT, 'content', f), 'utf8');
  const sources = html.slice(html.indexOf('<section class="sources"'));
  for (const m of sources.matchAll(/<li>\s*<a href="(https?:\/\/[^"]+)"/g)) urls.add(m[1].replace(/&amp;/g, '&'));
}

let found = 0, missing = [];
for (const url of urls) {
  if (archive[url] || /^https?:\/\/web\.archive\.org\//.test(url)) continue;
  let snap = lookup(url);
  if (!snap && save) {
    curl('-o', '/dev/null', '-m', '90', `https://web.archive.org/save/${url}`);
    snap = lookup(url);
  }
  if (snap) { archive[url] = snap; found++; } else missing.push(url);
}

const sorted = Object.fromEntries(Object.keys(archive).sort().map((k) => [k, archive[k]]));
writeFileSync(FILE, JSON.stringify(sorted, null, 2) + '\n');
console.log(`archive: ${found} new, ${Object.keys(sorted).length} total, ${missing.length} without a copy`);
for (const u of missing) console.log('  no copy: ' + u);
