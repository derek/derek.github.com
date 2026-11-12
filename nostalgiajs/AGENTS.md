# AGENTS.md: Nostalgia.js

Instructions for AI agents and humans working in this folder. Keep this file current: if you change a convention, change it here in the same commit.

## What this is

Nostalgia.js is a collection of essays on web history, with the tagline "AI Essays on Web History". Served by GitHub Pages at `/nostalgiajs/`. The essays cover the web from the 1990s through the 2010s, mostly through JavaScript, and include the people and culture of that time (careers, communities, arguments) as well as the technology. An essay may run up to the present when its story does.

Audience: JavaScript and web developers aged 40 to 60 in Derek's community, many of whom lived this history. The essays are written by an AI. Derek Gathright edits the collection and picks most of the topics.

These are essays, not blog posts. They are not dated, they do not say "today" or "this week", and the site does not promise a schedule. Say "essay" and "collection", never "post", "blog" or "daily".

The site was first called Boomer.js and then, briefly, View Source. `../boomerjs/` and `../view-source/` now hold only redirect pages to the new addresses. Leave them alone.

## Hard rules

1. **Stay in this folder.** Do not edit anything outside `nostalgiajs/`.
2. **No dependencies.** One build script in plain Node, no npm packages, no frameworks, no CDN scripts, no tracking, no web fonts. The published pages use no JavaScript.
3. **Never edit generated files by hand.** `index.html`, `feed.xml`, `about/index.html` and every `<slug>/index.html` are written by `scripts/build.mjs`. Change the sources (`essays.json`, `content/`, `style.css`, the script) and rebuild. Commit the generated files with the change, because GitHub Pages serves this folder as it is.
4. **No author name, and one quiet disclosure.** The essays have no byline and the author has no name. The tagline and the About page say the essays are written by AI. Do not add disclaimers, apologies or "as an AI" asides to an essay.
5. **Never invent facts.** No made-up quotes, statistics, version numbers, release dates, people or anecdotes. Open a real source for every date, version, quote and attribution before you write it. Quote people only in words you have seen on the page. If you cannot confirm a claim, drop it or state it less firmly ("around 2001", "by the early 2000s"). Label your own interpretations ("my best guess", "I'd argue"). Code samples must be real code from the period or code that runs as shown, not pseudo-code in disguise.
6. **Never claim personal memories.** Do not write "I remember writing this" or invent a past. Do not dwell on the absence either: no "I wasn't there" or "the people who were there will know". Tell the story with the confidence of someone who did the reading. Use "you" for the developer's experience.
7. **No punching down.** Gently roasting a technology is fine. Mocking the people who used it is not. Most of them shipped real products under real constraints.
8. **Do not assume anyone's gender.** Refer to people by name or with "they" unless a source you opened states their pronouns.

## Voice

One consistent voice across every essay: first person, dry, warm, curious and a little self-deprecating, like the funniest person at a user group who also reads the spec. "I" is for opinions and judgments, never for memories. Contractions, plain words. One joke per section is plenty. Avoid corporate filler, listicle hype, emoji and the "It's not X, it's Y" pattern. Respect the reader's time: no padding.

## Essay shape

Target 2,200 to 3,000 words of prose (about 10 to 13 minutes, plus code). Readers are experienced developers, so go deep and skip the basics. Every essay has, in this order:

1. **Lede and hook**: a one-sentence lede, then a concrete artifact (a line of code, a tag, an error message, a product name), not a generic intro.
2. **Setup**: what the world looked like before, so the change makes sense.
3. **What happened**: the facts, in order, with the real code from the time. Show the mechanism, not just the headline.
4. **Why**: the forces behind it (market, standards, engines, tooling, money). Give each force its own paragraph. This is the part readers will argue with, so be fair.
5. **How people coped**: the workarounds, tools and culture around the problem.
6. **What it left behind**: reflection with at least three non-obvious takeaways, each tied to something in today's code or industry. Every essay needs at least one connection the reader probably has not made (an old idea that survived under a new name, a standard that ratified a proprietary feature, a fix that caused the next problem).
7. **The takeaway**: a final section under the heading "The takeaway", one or two paragraphs that state the essay's conclusion outright. A reader who skips to the end should get the point. Never end on a code block, a list or a question to the reader.
8. **Sources and further reading**: the last thing in the essay.

Section headings should describe the content of this essay. The list above is the order of ideas, not a set of headings to copy, apart from "The takeaway".

**Link your sources.** When an essay mentions an essay, blog post, spec, book or project site, link it inline at the mention and list it again under "Sources and further reading" with author and year. Only link URLs you have opened and confirmed load and say what you claim. If you cannot open a source, name it without a link. Link to other Nostalgia.js essays as `../their-slug/` where they are relevant.

## Files

| Path | What it is |
| --- | --- |
| `essays.json` | The list of published essays. The single source of truth for titles, topics, eras and order. |
| `content/<slug>.html` | The body of one essay, as an HTML fragment. |
| `content/_about.html` | The body of the About page. |
| `archive.json` | Internet Archive copies of source links, filled in by `scripts/archive.mjs`. |
| `og/*.png` | Link-preview cards, drawn by `scripts/og.mjs`. |
| `style.css` | The one stylesheet. |
| `scripts/build.mjs` | Generates every page, the landing page and the feed. |
| `TOPICS.md` | Essay ideas, with Derek's "Up next" queue at the top. |
| `<slug>/index.html`, `index.html`, `about/index.html`, `feed.xml` | Generated. Do not edit. |

### An entry in `essays.json`

```json
{
  "slug": "jquery-and-the-dollar-sign",
  "title": "jQuery 1.0: what $ really replaced",
  "description": "One sentence, used on the landing page, in link previews and in the feed.",
  "topic": "libraries",
  "era": [2005, null],
  "published": "2026-11-12T04:41:10+00:00"
}
```

- `slug`: lowercase words joined by hyphens. It is the essay's address, so never change it after publishing. No dates in slugs.
- `topic`: one of `browsers` (Browsers and the platform), `language` (The language), `libraries` (Libraries and tools) or `people` (People and culture). The landing page groups essays by topic.
- `era`: the years the story covers, `[start, end]`, or `[start, null]` when it runs to the present. Shown on the page as "1997 to 2001" or "2005 to today", and used to order essays within a topic.
- `published`: the real UTC time the essay was first published, as an ISO date-time. It is used only inside the RSS feed. It is never shown on a page, and never changed afterwards.
- `related` (optional): up to three slugs to list first under "Related essays". Without it, the build picks essays from the same topic and the nearest era.
- `corrections` (optional): a list of `{ "date": "2026-12-01", "note": "What changed." }`, shown at the end of the essay.

### A content fragment

`content/<slug>.html` starts with `<p class="lede">…</p>` and ends with the sources section. It contains no `<h1>`, no title, no date, no byline and no navigation; the build adds those. Copy the shape of an existing fragment, including the sources section:

```html
<section class="sources" aria-labelledby="sources-h">
  <h2 id="sources-h">Sources and further reading</h2>
  <ul>
    <li><a href="https://example.org/page">Title of the source</a>, Author, 2006</li>
  </ul>
</section>
```

Code goes in `<pre tabindex="0"><code>…</code></pre>` with `&lt;`, `&gt;` and `&amp;` escaped. About 35 characters fit across a 375 px screen; longer lines scroll inside the `pre`, which is fine, but keep the opening snippet within 35 so the hook is never clipped. Do not bend period code out of shape to fit.

## Commands

Run from `nostalgiajs/`. Needs Node 22 or newer. There is nothing to install.

| Command | What it does |
| --- | --- |
| `npm run build` | Validates `essays.json` and the fragments, then regenerates every page and the feed. |
| `npm run archive` | Looks up an Internet Archive copy for each source link and records it in `archive.json`. Add `-- --save` to ask the archive to capture pages it does not have. Needs network access. |
| `npm run og` | Draws the link-preview cards. Add `-- <slug>` to draw one. Needs Playwright with Chromium. |
| `npm run serve` | Local server on port 8000. Open `/nostalgiajs/`. |

## Adding an essay

1. Pick the topic from `TOPICS.md`: the first item under "Up next" if that list has any, otherwise your choice from the lists.
2. Research. Open the sources and collect the exact dates, versions and quotes you will use.
3. Write `content/<slug>.html` and add the entry to `essays.json`.
4. **Second-pass fact check.** Have a separate reviewer (a subagent that has not seen your research notes) read the finished fragment, reopen every linked source, and try to find errors: wrong dates, versions, quotes that do not appear on the page, claims the source does not support. Fix or cut everything it finds. Do not skip this, even when you are confident.
5. Delete the topic's line from `TOPICS.md` and renumber "Up next". Add any good new ideas to the lists.
6. Run `npm run archive`, then `npm run og -- <slug>`, then `npm run build`.
7. Check the new essay and the landing page at 375 px wide in light and dark mode: no horizontal page scroll, no console errors, no broken local links.
8. Commit everything, generated files included, with the essay title in the message. List in the commit body anything you could not verify.

## Correcting an essay

Readers report mistakes through the "Spot a mistake?" link, which opens a GitHub issue. Check the report against a source before changing anything. Fix the fragment, and if the fix changes what the essay says (a date, a name, a claim), add a `corrections` entry to `essays.json` with the date and a one-sentence note. Typos need no note. Rebuild and commit.

## Site conventions

- Light and dark mode via `prefers-color-scheme`, colours as tokens in `:root`. Body text at least 18px with 4.5:1 contrast. The audience reads with their glasses on, so do not make it small.
- Semantic HTML, a visible focus style, `lang="en"`.
- Links inside the site are relative so it works under `/nostalgiajs/` and from a local server. The feed, canonical links and link-preview tags use absolute URLs, which they require.
- Everything from `essays.json` is HTML-escaped by the build. Keep it that way.
- Copy style: short sentences, plain words, no hype.

## Branches and PRs

Develop on the branch you were given. Do not open a PR unless asked. When you open one, list anything you could not verify.
