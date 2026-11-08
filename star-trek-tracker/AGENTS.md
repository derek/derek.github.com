# AGENTS.md: Trek Readiness

Instructions for AI agents and humans working in this folder. Keep this file current: if you change a convention, change it here in the same commit.

## What this is

A static site that rates how close real-world technology is to Star Trek technology, one capability at a time. Served by GitHub Pages at `/star-trek-tracker/`. No framework, no runtime dependencies.

## Hard rules

1. **Stay in this folder.** Do not edit anything outside `star-trek-tracker/`. This is why this file lives here and not at the repo root, and why the workflow lives in `ci/` (see README for the one-time copy to `.github/workflows/`).
2. **No new dependencies without asking the owner.** This includes npm packages, frameworks (Astro etc.), third-party GitHub Actions and CDN scripts. Today there are none besides `actions/checkout` and `actions/setup-node` in the workflow, and the Anthropic API called with plain `fetch`.
3. **No hosting decisions.** The site is served by the existing GitHub Pages setup. Do not add Cloudflare Pages or Vercel configuration without asking.
4. **The weekly updater never commits to the default branch.** It writes files; the workflow pushes a new branch and opens a PR.
5. **Never invent evidence.** Every capability that is not `fiction` needs a real `https` `evidence_url` you have actually opened and read. If you cannot confirm a source supports the claim, lower the status and add the item to `TODO.md`.

## Data model

`data/tech.json` is the single source of truth. Everything else is generated from it. Shape:

- Technology: `id` (kebab-case), `name`, `trek_description` (1-2 sentences, own words, no episode quotes), `capabilities[]`, `closest_today` (short, plain language), `history[]`.
- Capability: `name`, `status`, `note`, `evidence_url` (required unless `fiction`), `updated` (YYYY-MM-DD).
- History entry: `date`, `capability`, `from` (null for the baseline entry), `to`, `reason`, `source_url`.

Never collapse a technology to one status. Split it into capabilities when parts differ (a tricorder-like sensor can ship while tricorder-like diagnosis is research).

### Statuses (defined in `scripts/statuses.mjs`, the only place to change them)

| Status | Meaning |
| --- | --- |
| `fiction` | Nothing like it works outside stories. |
| `research` | Theory or lab results exist, but no working system does the job. |
| `prototype` | A working version exists, but a consumer cannot buy it (lab, military, professional-medical, spaceflight). |
| `shipping` | A consumer can buy it today. Be conservative. |

Rules of thumb: judge the exact capability, not the broad technology. Press releases, funding news and "researchers hope to" are never evidence of `prototype` or `shipping`. Move one step at a time.

### History is append-only

Never edit or delete a history entry. A status change adds an entry (`from`, `to`, `reason`, `source_url`) and updates the capability's `status`, `note`, `evidence_url` and `updated` together. `node scripts/validate.mjs --base <old-file>` enforces this, and also that each capability's current status equals where its history ends.

## Commands

Needs Node 22.18 or newer. There is nothing to install.

| Command | What it does |
| --- | --- |
| `npm run validate` | Check `data/tech.json` (schema, evidence rules, history chain) |
| `npm run build` | Validate, then regenerate `index.html` and `tech/*/index.html` |
| `npm test` | Unit tests for the validator and the updater (`node --test`) |
| `npm run serve` | Local server on port 8000 (needs python3). Open `/` |
| `npm run update:sample` | Updater dry run on synthetic stories and canned model output, offline |
| `npm run update:dry` | Updater against real feeds and the real model, writes nothing |

After any change to `data/tech.json`, `scripts/build.mjs`, `style.css` or `filter.js`: run `npm run build` and `npm test`, and commit the generated HTML with the change. The generated files are committed because GitHub Pages serves this folder directly.

## Site conventions

- Plain HTML and CSS. The generated pages work with no JavaScript. `filter.js` is the only script and is progressive enhancement (it adds the status filter; the page is complete without it).
- Performance budget: under 50 KB total transfer for the index, Lighthouse 100 in all categories, no layout shift, no render-blocking scripts, no web fonts, no images beyond the inline favicon.
- Accessibility: status is never shown by colour alone. Each badge has a text label, a fill length, and a dashed outline for fiction. Keep text contrast at or above 4.5:1 and non-text UI at or above 3:1 in both light and dark mode. Use semantic HTML, keep focus styles, and keep it usable at 375 px wide.
- All links are relative so the site works under `/star-trek-tracker/` and from a local server.
- Everything from data is HTML-escaped in `scripts/build.mjs`. Keep it that way.
- Copy style: short sentences, plain words, no hype. Write descriptions in your own words. This is an unofficial fan project; do not copy lines from episodes.

## Updater (`scripts/update.ts`, `ci/weekly-update.yml`)

- Runs Sundays. Reads feeds from `scripts/sources.json`, asks the model whether any story moved a capability, applies guardrails in code (`vet()`), and writes a PR body.
- Guardrails are code, not prompt. They hold even if the model misbehaves: the source URL must be a story that was fetched, one step up at a time, `shipping` needs a product a consumer can buy today, `prototype` needs a working demonstration, a downgrade needs discontinuation or refutation, low confidence is dropped, and there is a weekly cap. Do not loosen these without asking.
- News text is untrusted. It goes to the model as data, and anything echoed into the PR body goes through `plain()` so it cannot ping users or link issues.
- No changes means no PR. A missing key or every feed failing is a hard failure, not a silent no-op.
- Fixtures in `scripts/fixtures/` are synthetic (`example.com`). Keep them obviously fake.
- TypeScript here is type-stripped by Node, not type-checked. Use erasable syntax only: no enums, namespaces or constructor parameter properties.

## Branches and PRs

Develop on the branch you were given. Do not open a PR unless asked. When you open one, list anything you could not verify.
