# Trek Readiness

How close is real-world technology to Star Trek's? Each technology is rated one capability at a time, on a four-step scale from fiction to shipping. Every rating links to evidence, and every change is logged with a reason.

Live at `/star-trek-tracker/`. This is an unofficial fan project. Star Trek is a trademark of CBS Studios Inc. and Paramount.

## How it works

```
data/tech.json  --scripts/build.mjs-->  index.html + tech/<id>/index.html   (committed, served by GitHub Pages)
data/tech.json  <--scripts/update.ts--  news feeds + Anthropic API           (weekly, opens a PR)
```

`data/tech.json` is the single source of truth. Read `AGENTS.md` for the data model, the meaning of each status and the rules for changing them.

## Requirements

Node 22.18 or newer. Nothing to install: there are no dependencies.

## Run the site

```sh
npm run build     # validates the data, regenerates index.html and tech/*/index.html
npm run serve     # then open http://localhost:8000/
```

`npm run serve` uses `python3 -m http.server`. Any static server works, because all links are relative.

To change a rating by hand, edit `data/tech.json` (add a history entry, update the capability), then run `npm run build` and `npm test`, and commit the generated HTML along with the data.

## Run the weekly updater

Offline demo. It uses synthetic stories and canned model output, calls no network, and writes nothing:

```sh
npm run update:sample
```

You will see two accepted changes and four rejected by the guardrails, and the pull request body it would open.

Against real feeds and the real model, still writing nothing:

```sh
export ANTHROPIC_API_KEY=...
npm run update:dry
```

A real run edits `data/tech.json` and writes `pr-body.md`. It never touches git; the workflow does that.

Options: `--stories <file>`, `--verdicts <file>` (skip the model and use saved answers), `--today YYYY-MM-DD`, `--pr-body <file>`. Environment: `TREK_MODEL` (default `claude-opus-5-5`), `TREK_FALLBACKS=off` to skip the server-side refusal fallback, `ANTHROPIC_BASE_URL`.

### Set up the news source

The updater reads RSS or Atom feeds listed in `scripts/sources.json`:

```json
{ "lookback_days": 8, "max_stories": 120, "max_changes_per_run": 8,
  "feeds": [ { "name": "Example Wire", "url": "https://example.com/feed.xml" } ] }
```

`feeds` is empty at the moment, so a real run does nothing and prints a warning. Add your feeds there.

### Turn on the weekly run

GitHub only runs workflows from the repository root, so the workflow ships in `ci/` and needs a one-time copy:

```sh
mkdir -p .github/workflows
cp star-trek-tracker/ci/weekly-update.yml .github/workflows/trek-readiness-weekly.yml
```

Then add a repository secret named `ANTHROPIC_API_KEY`, and turn on Settings, Actions, General, "Allow GitHub Actions to create and approve pull requests".

The workflow runs Sundays at 14:17 UTC (and on demand). It runs the tests, runs the updater, and if anything changed it rebuilds the site, pushes a new branch and opens a PR. It never commits to the default branch. No changes means no PR.

## Tests

```sh
npm test
```

Covers the data validator (including the append-only history check), the feed parser, every guardrail, and the PR body sanitising.

## Layout

| Path | Purpose |
| --- | --- |
| `data/tech.json` | The data |
| `scripts/build.mjs` | Static site generator |
| `scripts/validate.mjs` | Schema, evidence and history checks |
| `scripts/statuses.mjs` | The four statuses and their definitions |
| `scripts/update.ts` | Weekly updater |
| `scripts/sources.json` | Updater settings and feed list |
| `scripts/fixtures/` | Synthetic stories and model output for offline runs |
| `style.css`, `filter.js` | The only CSS and JS |
| `ci/weekly-update.yml` | The workflow, to copy into `.github/workflows/` |
| `TODO.md` | Things that need a human to verify |
