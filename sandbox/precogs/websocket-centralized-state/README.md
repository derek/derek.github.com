# WebSocket Server State Prototype

This prototype is experimenting with the perspective that the server should always be the source-of-truth for precog status.  Each team (or inbox?) will have its own precog state object that is broadcast out to teammates.

## Instructions

```
$ npm install
$ nodemon index.js
```

## Thoughts

* Should the server state support partial fetches (by conversation ID(s)), or should we always return everything?  It shouldn't be that large of an object, right?  We're likely just talking about a few dozen items?

* Both the server and client should have timeout logic.  The server, being the source of truth, should certainly have the ability to control and timeout precogs.  But the client should as well as a fallback in case something goes wrong on the server, so precogs don't get "stuck".  The client takes over control of the precog when it loses confidence that the server is managing the state.  In other words, once we eclipse a certain timeout and haven't heard anything, confidence has dropped below a given threshold, so we should kill the precog ourselves.

* Client shouldn't refresh the precogs at the hard-coded TIMEOUT, but should instead be at ${TIMEOUT} or the soonest ending known precog, whichever comes first.  Or, should the server push the precog state at the sooner interval, and the client's timeout just be the safety net?

* Client should apply the precog state at page load, to ensure any active precogs are utilized.
