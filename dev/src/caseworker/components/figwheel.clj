(ns caseworker.components.figwheel
  (:require [figwheel-sidecar.repl-api :as figwheel]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]))

(defrecord Figwheel []
  component/Lifecycle
  (start [config]
    (figwheel/start-figwheel! config)
    config)
  (stop [config]
    config))

(defn make-figwheel
  [config]
  (-> (io/resource config)
      (slurp)
      (edn/read-string)
      (map->Figwheel)))
