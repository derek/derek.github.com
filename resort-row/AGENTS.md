# AGENTS.md: Resort Row

A board game for 2 to 4 players (human or computer) about building resorts along a road. Served by GitHub Pages at `/resort-row/`.

## Rules

1. **Stay in this folder.** Do not edit files outside `resort-row/`.
2. **No dependencies, no build step.** One `index.html` with inline CSS and vanilla JS. No CDN scripts, no frameworks, no tracking.
3. **Original game.** It plays in the spirit of 1980s hotel-building board games, but the name, resorts, board and art are original. Do not copy the names, artwork or layout of any published game.
4. **Links are relative** so the page works under `/resort-row/`, from a local server, or from `file://`.
5. **Copy style:** short sentences, plain words. If you change a rule, update the "How to play" dialog in the same commit.

## Code map (all in `index.html`)

- `LOTS`, `PATTERN`, `CORNERS`: board data (resort prices, stages, nightly rates, space types). Balance changes go here.
- `S`: the whole saved game state (players, lots, entrances, turn). Saved to `localStorage` under `resort-row-save-v1`. If you change its shape, bump the key.
- `UI`: transient view state (menus, hover, selected lot). Never saved.
- Turn flow: `startTurn` → `roll` → `land` → action phase → `endTurn` → `advance`. Money always moves through `charge()`, which auto-sells buildings and handles bankruptcy.
- Computer players: `aiRoll`, `aiAct`. They act through the same functions as human buttons.
- `window.__resortRow` exposes state for automated checks only.

## Checks

Run an all-computer game to the end in headless Chromium (emulate `reducedMotion: 'reduce'` to speed it up): no console errors, and the game reaches `phase === 'over'`. Open the page at 375 px wide: no horizontal scroll, and every action is reachable by keyboard and touch. Check light and dark mode.
