(ns msc.components.handler
  (:require [com.stuartsierra.component :as component]))

(defrecord Handler [handler-fn]
  component/Lifecycle
  (start [this]
    (assoc this :handler (handler-fn this)))
  (stop [this]
    (dissoc this :handler)))

(def make-handler ->Handler)
