(ns user
  (:require [clojure.edn :as edn]
            [clojure.java.jdbc :as jdbc]
            [clojure.repl :refer :all]
            [clojure.test :refer [run-tests run-all-tests]]
            [caseworker.components.figwheel :as figwheel]
            [caseworker.components.sass :as sass]
            [caseworker.config :as c]
            [caseworker.migrations :refer [migrate! rollback!]]
            [caseworker.system :as system]
            [figwheel-sidecar.repl-api :refer [cljs-repl]]
            [reloaded.repl :as repl :refer [system go init start stop reset reset-all]]))

(defn make-dev-system
  []
  (-> (system/make-system c/env)
      (assoc :sass     (sass/make-sass-watcher "sass.edn")
             :figwheel (figwheel/make-figwheel "figwheel.edn")
             )))

(reloaded.repl/set-init! #'make-dev-system)
