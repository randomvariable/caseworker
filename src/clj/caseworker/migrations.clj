(ns caseworker.migrations
  (:require [caseworker.config :as c]
            [ragtime.jdbc :as jdbc]
            [ragtime.repl :as ragtime]))

(def config
  {:datastore  (jdbc/sql-database (c/env :db))
   :migrations (jdbc/load-resources "migrations")})

(defn migrate
  []
  (ragtime/migrate config))

(defn rollback
  []
  (ragtime/rollback config))
