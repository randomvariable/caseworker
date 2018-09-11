(ns caseworker.config
  (:require [clojure.string :as str]
            [config.core :as c]))

(defn database-url->datasource
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
    (get c/env :database-url)        (assoc-in [:db] (database-url->datasource (:database-url c/env)))
    (get c/env :docker-db-port-5432) (update-in [:db :subname] (fnil str/replace "") #"5432" (:docker-db-port-5432 c/env))))
