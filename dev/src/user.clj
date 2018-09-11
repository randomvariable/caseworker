(ns user
  (:require [clojure.edn :as edn]
            [clojure.java.jdbc :as jdbc]
            [clojure.repl :refer :all]
            [clojure.test :refer [run-tests run-all-tests]]
            [caseworker.components.figwheel :as figwheel]
            [caseworker.components.sass :as sass]
            [caseworker.config :as c]
            [caseworker.seed :as seed]
            [caseworker.system :as system]
            [figwheel-sidecar.repl-api :refer [cljs-repl]]
            [ragtime.jdbc :as rjdbc]
            [ragtime.repl :as ragtime]
            [reloaded.repl :as repl :refer [system go init start stop system reset reset-all]]))

;; Dev system

(defn make-dev-system
  []
  (-> (system/make-system c/env)
      (assoc :sass     (sass/make-sass-watcher "sass.edn")
             :figwheel (figwheel/make-figwheel "figwheel.edn"))))

(reloaded.repl/set-init! #'make-dev-system)

;; DB migrations and seed

(def config
  {:datastore  (rjdbc/sql-database (c/env :db))
   :migrations (rjdbc/load-resources "migrations")})

(defn migrate  [] (ragtime/migrate config))
(defn rollback [] (ragtime/rollback config))
(defn seed     [] (seed/seed (:db c/env)))
