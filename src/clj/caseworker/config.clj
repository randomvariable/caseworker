(ns caseworker.config
  (:require [clojure.string :as str]
            [config.core :as c]))

(defn datasource
  [url]
  (let [uri               (java.net.URI. url)
        user-and-password (some-> (.getUserInfo uri) (str/split #":"))]
    {:classname   "org.postgresql.Driver"
     :subprotocol "postgresql"
     :user        (get user-and-password 0)
     :password    (get user-and-password 1)
     :subname     (if (= -1 (.getPort uri))
                    (format "//%s%s" (.getHost uri) (.getPath uri))
                    (format "//%s:%s%s" (.getHost uri) (.getPort uri) (.getPath uri)))}))

(def env
  (cond-> c/env
    (get c/env :port)                (assoc-in [:jetty :port] (:port c/env))
    (get c/env :docker-db-port-5432) (assoc-in [:db :port] (:docker-db-port-5432 c/env))
    (get c/env :database-url)        (assoc-in [:db] (datasource (:database-url c/env)))))
