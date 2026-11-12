// Draws the link-preview cards (1200x630 PNG) that show when an essay is
// shared: og/default.png for the site and og/<slug>.png per essay.
// Run: `node scripts/og.mjs` (add a slug to draw only that one). Needs
// Playwright with Chromium, which the cloud sessions have pre-installed.
// The build works without it and falls back to og/default.png.
import { readFileSync, mkdirSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { createRequire } from 'node:module';

const ROOT = join(dirname(fileURLToPath(import.meta.url)), '..');
const { chromium } = createRequire(import.meta.url)('playwright');
const { essays } = JSON.parse(readFileSync(join(ROOT, 'essays.json'), 'utf8'));
const only = process.argv[2];
const berry = 'data:image/png;base64,' + readFileSync(join(ROOT, 'berry.png')).toString('base64');
const esc = (s) => String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');

const card = (title, sub) => `<!doctype html><meta charset="utf-8"><style>
  * { box-sizing: border-box; margin: 0; }
  body { width: 1200px; height: 630px; background: #fff; color: #000080; display: flex; flex-direction: column;
    font-family: Georgia, "Times New Roman", "DejaVu Serif", serif; }
  .bar { background: #000080; color: #fff; padding: 44px 80px 40px; border-bottom: 12px solid #ffcc00; display: flex; align-items: center; }
  .brand { font-weight: 700; font-size: 58px; letter-spacing: 0.02em; }
  .sub { font: 28px Verdana, "DejaVu Sans", Arial, sans-serif; color: #ffffcc; margin-top: 6px; }
  img { margin-left: auto; width: 120px; height: 120px; border: 3px solid #ffcc00; }
  h1 { margin: auto 80px; font-size: ${title.length > 48 ? 66 : 82}px; line-height: 1.12; }
</style><div class="bar"><div><div class="brand">Nostalgia.js</div><div class="sub">${esc(sub)}</div></div><img src="${berry}"></div><h1>${esc(title)}</h1>`;

mkdirSync(join(ROOT, 'og'), { recursive: true });
const browser = await chromium.launch();
const page = await browser.newPage({ viewport: { width: 1200, height: 630 }, deviceScaleFactor: 1 });
const jobs = [];
if (!only) jobs.push(['default', 'Essays about the early web', 'AI Essays on Web History']);
for (const e of essays) if (!only || only === e.slug) jobs.push([e.slug, e.title, 'AI Essays on Web History']);
for (const [name, title, sub] of jobs) {
  await page.setContent(card(title, sub));
  await page.screenshot({ path: join(ROOT, 'og', name + '.png') });
}
await browser.close();
console.log(`og: drew ${jobs.length} card(s)`);
