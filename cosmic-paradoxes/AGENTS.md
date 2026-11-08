# AGENTS.md: Cosmic Paradoxes

Interactive explainers on paradoxes in relativity, cosmology and the search for life. Served by GitHub Pages at `/cosmic-paradoxes/`.

## Rules

1. **Stay in this folder.** Do not edit files outside `cosmic-paradoxes/`.
2. **No dependencies, no build step.** Hand-written HTML, one shared `style.css`, and an inline vanilla JS `<script>` per page. No CDN scripts, no frameworks, no tracking.
3. **Links are relative** so the site works under `/cosmic-paradoxes/` and from a local server or `file://`.
4. **Facts:** use standard textbook values (H0 about 70 km/s/Mpc, horizons rounded). Say plainly which paradoxes are resolved and which are open (only Fermi is open). Do not invent statistics; soften a claim you cannot source.
5. **Copy style:** short sentences, plain words, no hype. Each page: story, demo, callout, a collapsed "Go deeper" box with the math, Wikipedia links, prev/next pager.

## Layout

`index.html` (landing, cards) and one folder per paradox: `twin/`, `olbers/`, `fermi/`, `barn/`, `horizon/`. The top nav is repeated in every page. If you add a page, add it to every nav, the landing cards, and the pager chain.

## Checks

Open each page at 375 px wide: no horizontal scroll, no console errors, controls usable by touch and keyboard, canvases have an `aria-label`, and results are also given as text in an `aria-live` readout.
