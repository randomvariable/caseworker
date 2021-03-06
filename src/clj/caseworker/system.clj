(ns caseworker.system
  (:require [com.stuartsierra.component :as component]
            [caseworker.components.handler :as handler]
            [caseworker.components.postgres :as postgres]
            [caseworker.routes :as routes]
            [ring.component.jetty :as jetty]))

(defn make-system
  [{:keys [db jetty] :as config}]
  (-> (component/system-map
        :app   (handler/make-handler #'routes/routes)
        :db    (postgres/make-connection-pool db)
        :jetty (jetty/jetty-server jetty))
      (component/system-using
        {:app   [:db]
         :jetty [:app]})))
