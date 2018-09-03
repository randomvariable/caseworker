(ns user
  (:require [clojure.edn :as edn]
            [clojure.java.jdbc :as jdbc]
            [clojure.repl :refer :all]
            [clojure.test :refer [run-tests run-all-tests]]
            [msc.components.figwheel :as figwheel]
            [msc.components.sass :as sass]
            [msc.config :as c]
            [msc.migrations :refer [migrate! rollback!]]
            [msc.system :as system]
            [reloaded.repl :as repl :refer [system go init start stop reset reset-all]]))

(defn make-dev-system
  []
  (-> (system/make-system c/env)
      (assoc :sass     (sass/make-sass-watcher "sass.edn")
             :figwheel (figwheel/make-figwheel "figwheel.edn")
             )))

(reloaded.repl/set-init! #'make-dev-system)
