# msc

[![Build Status](https://semaphoreci.com/api/v1/walmag/msc/branches/master/badge.svg)](https://semaphoreci.com/walmag/msc)

Administration software for Migrant Support Centres.

## Development Mode

To run msc locally you will need:

 * docker/docker-compose
 * leiningen
 * sass

If you want to be able to connect to the local dev database, you'll need `psql` too (you can use a docker container for this if you prefer).

### Run application:

```
make up                                 # start service dependencies
lein do clean, cljsbuild once dev, repl # make sure you have a fresh CLJS build and start the REPL
user=> (go)                             ;; start the app (including figwheel and sass watcher components)
```

Sass will automatically rebuild any changed files in `src/sass`.
Figwheel will automatically push cljs or sass changes to the browser.

Browse to [http://localhost:9000](http://localhost:9000) to see the running app.

### Run tests:

Install karma and headless chrome

```
npm install -g karma-cli
npm install karma karma-cljs-test karma-chrome-launcher --save-dev
```

And then run your tests

```
lein clean
lein doo chrome-headless test once
```

Please note that [doo](https://github.com/bensu/doo) can be configured to run cljs.test in many JS environments (phantom, chrome, ie, safari, opera, slimer, node, rhino, or nashorn).

## Deploying to staging

TBD, probably heroku

## Deploying to production

TBD

## API Docs

When you have the app running, visit [http://localhost:9000](http://localhost:9000) to see Swagger docs for the API.
