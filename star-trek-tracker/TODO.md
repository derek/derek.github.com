# TODO: needs a human

Items I could not confirm, or that are judgement calls. Delete an item when it is resolved.

## Evidence checked only lightly

All 63 evidence URLs returned HTTP 200 on 2026-11-08, and each opens a real page. I read the page text for only some of them, to check the claim in the note. These need a closer read before you rely on them:

- [ ] Genie 3 page for "AI-generated interactive worlds" (Holodeck). The page is script-rendered and I could not read it. The status is `prototype`, but access may have widened since. If consumers can now use it, it may be `shipping`.
- [ ] HaptX (Holodeck, touch feedback) and Engineered Arts Ameca (Androids, expressive faces). I assumed both are sold to businesses and researchers but did not confirm from the pages.
- [ ] Butterfly (Tricorder, handheld imaging). Confirm it is not sold to the general public. If it is, it is `shipping`.
- [ ] Prenuvo (Bio-beds, full-body scanning). It is a paid scan service, not a product. Decide whether that counts as `shipping`.
- [ ] Needle-free insulin injectors (Hypospray). Availability to patients varies by country, and the evidence link is Wikipedia only.
- [ ] Cultured meat (Food synthesis). Retail availability is changing. Check whether it is now bought by consumers in any market.
- [ ] AliveCor (Tricorder, heart sensing). The note says "cleared by regulators in some countries". The linked page is the vendor's home page, so confirm the clearance claim.

## Evidence quality

- [ ] Many `evidence_url` values are Wikipedia articles. They are real and relevant, but primary sources (papers, regulators, vendors) would be stronger.

## Judgement calls on status

- [ ] Quantum teleportation and atom-probe mapping (Transporter) are rated `prototype` because each narrow capability works in a lab. You may prefer `research`.
- [ ] The Humane AI Pin (Comm badge) is rated `prototype` because it was sold and then discontinued. You may prefer `fiction` for a badge you can no longer buy.
- [ ] The scale's `shipping` means a consumer can buy it. Mature professional technology (ion thrusters, stealth aircraft, brain stimulation, AI scan reading) therefore shows as `prototype`. Consider a fifth step such as "In professional use" if that reads oddly.

## Updater

- [ ] **Add the news source.** The prompt left the feed description blank, so `scripts/sources.json` has no feeds and a real run does nothing yet.
- [ ] **Not tested against the live Anthropic API.** There is no key in the build environment. The request shape follows the API docs and was tested against a local mock that captured the headers and body. Do one `npm run update:dry` with a real key before trusting the Sunday run.
- [ ] **Copy the workflow to the repo root.** GitHub only runs workflows in `.github/workflows/`, and I was told not to touch files outside this folder. See the README.
- [ ] The workflow uses `actions/checkout@v4` and `actions/setup-node@v4`. These are the only third-party-ish dependencies. Say if you want them pinned to commit SHAs.
- [ ] The default model is `claude-opus-5-5`, set with `TREK_MODEL`. Change it if you want something cheaper. The job runs once a week over about 120 stories.

## Hosting and repo

- [ ] The repo's default branch is `master`, not `main`.
- [ ] Hosting is the existing GitHub Pages setup. I did not choose Cloudflare Pages or Vercel.
- [ ] Lighthouse was run locally against a plain static server (no gzip, no cache headers). Re-run it against the live URL. Expect the same scores, with less transfer because Pages compresses.
