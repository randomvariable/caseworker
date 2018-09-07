(ns caseworker.components.postgres
  (:require [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as csk-extras]
            [clojure.string :as s]
            [com.stuartsierra.component :as component]
            [hugsql.core :as hugsql]
            [hugsql.adapter :as adapter]
            [caseworker.util :as u])
  (:import [com.mchange.v2.c3p0 ComboPooledDataSource]))

(defn split-nested-key
  "Split a keyword on double-underscores"
  [k]
  (->> (s/split (name k) #"__")
       (map keyword)))

(defn nest
  "Takes a flat (single-level) map, and interprets keys containing
  double underscores as nested map keys."
  [m]
  (if-not (map? m)
    m
    (->> (u/map-keys split-nested-key m)
         (reduce-kv assoc-in {}))))

(defn result-one-snake->kebab
  [this result options]
  (some->> (hugsql.adapter/result-one this result options)
           (nest)
           (csk-extras/transform-keys csk/->kebab-case-keyword)))

(defn result-many-snake->kebab
  [this result options]
  (->> (hugsql.adapter/result-many this result options)
       (map (comp (partial csk-extras/transform-keys csk/->kebab-case-keyword)
                  nest))))

(defmethod hugsql.core/hugsql-result-fn :1    [sym] 'caseworker.components.postgres/result-one-snake->kebab)
(defmethod hugsql.core/hugsql-result-fn :one  [sym] 'caseworker.components.postgres/result-one-snake->kebab)
(defmethod hugsql.core/hugsql-result-fn :*    [sym] 'caseworker.components.postgres/result-many-snake->kebab)
(defmethod hugsql.core/hugsql-result-fn :many [sym] 'caseworker.components.postgres/result-many-snake->kebab)

(defrecord ConnectionPool
  [config]
  component/Lifecycle
  (start [this]
    (assoc this :datasource
           (doto (ComboPooledDataSource.)
             (.setDriverClass (:classname config))
             (.setJdbcUrl (str "jdbc:" (:subprotocol config) ":" (:subname config)))
             (.setUser (:user config))
             (.setPassword (:password config))
             (.setMaxIdleTimeExcessConnections (* 30 60))
             (.setMaxConnectionAge (* 8 60 60))
             (.setTestConnectionOnCheckout true))))
  (stop [this]
    (when-let [datasource (:datasource this)]
      (.close datasource))
    (dissoc this :datasource)))

(def make-connection-pool ->ConnectionPool)
