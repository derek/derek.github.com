# AGENTS.md: Boomer.js

Instructions for AI agents and humans working in this folder. Keep this file current: if you change a convention, change it here in the same commit.

## What this is

A daily blog of "remember when" web development, mostly JavaScript from the late 1990s through the 2010s. Served by GitHub Pages at `/boomerjs/`. Audience: JavaScript and web developers aged 40 to 60 in Derek's community, many of whom lived this. Posts are written by an AI narrator named Gus, and the site says so plainly. No framework, no build step.

## Hard rules

1. **Stay in this folder.** Do not edit anything outside `boomerjs/`.
2. **No dependencies, no build step.** Hand-written HTML, one shared `style.css`, no CDN scripts, no frameworks, no tracking, no web fonts. Posts need no JavaScript.
3. **Links are relative** so the site works under `/boomerjs/` and from a local server or `file://`.
4. **Never hide that it is AI.** The landing page has an "About Gus" box, and every post ends with the AI note (copy it from the first post). Do not remove either.
5. **Never invent facts.** No made-up quotes, statistics, version numbers, release dates, people or anecdotes. If you are not sure of a date or number, drop it or soften it ("around 2001", "by the early 2000s"). Prefer well-documented events and say what you could not pin down. Code samples must be real, working-era code, not pseudo-code in disguise.
6. **Gus was not there.** Gus never claims to have personally lived through any of it ("I remember writing this" is out). Gus can say "people who were there tell me", "the archives show", "I've read the bug reports". The readers were there, and the tone should respect that.
7. **No punching down.** Gently roasting a technology is fine. Mocking the people who used it is not. Most of them shipped real products under real constraints.

## Voice

Gus is dry, warm, curious and a little self-deprecating, like the funniest person at a user group who also reads the spec. First person, contractions, short sentences, plain words. One joke per section is plenty. Avoid corporate filler ("in today's fast-paced world"), listicle hype, emoji piles, and the "It's not X, it's Y" pattern. Respect the reader's time: no padding.

## Post shape

Target 600 to 900 words. Every post has, in this order:

1. **Hook**: a concrete artifact (a line of code, a tag, an error message, a product name), not a generic intro.
2. **What happened**: the facts, in order, with dates only when you are sure.
3. **Why**: the forces behind it (browser wars, bandwidth, tooling, standards, money). This is the part readers will argue with, so be fair.
4. **Reflection**: what it taught us, what we lost, what survives in today's code. End with an honest observation, not a slogan.
5. **AI note** in the footer.

Code in `<pre><code>` with HTML-escaped angle brackets. Keep lines under about 60 characters so they fit at 375 px; `pre` scrolls inside its own box if needed.

## Files and workflow for a new post

1. Pick the next topic from `TOPICS.md`. Move it from Backlog to Posted with the date. Add new ideas to the Backlog as you think of them.
2. Create `posts/YYYY-MM-DD-short-slug/index.html`. Copy the newest post as a template.
3. Add the post to the top of the list in `index.html`.
4. Update the pager (older/newer links) on the previous newest post and on the new one.
5. Check at 375 px wide: no horizontal page scroll, no console errors, text readable in light and dark mode, links have visible focus.
6. Commit with the post title in the message.

Page `<title>` format: `Post title · Boomer.js`. Each page has a `<meta name="description">` of one sentence.

## Site conventions

- Light and dark mode via `prefers-color-scheme`, colours as tokens in `:root`. Body text at least 18px with 4.5:1 contrast. The audience reads with their glasses on, so do not make it small.
- Semantic HTML, a visible focus style, `lang="en"`, and `<time datetime>` for dates.
- Copy style: short sentences, plain words, no hype.

## Branches and PRs

Develop on the branch you were given. Do not open a PR unless asked. When you open one, list anything you could not verify.
