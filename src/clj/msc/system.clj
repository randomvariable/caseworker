(ns msc.system
  (:require [com.stuartsierra.component :as component]
            [msc.components.handler :as handler]
            [msc.components.postgres :as postgres]
            [msc.routes :as routes]
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
