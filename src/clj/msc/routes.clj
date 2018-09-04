(ns msc.routes
  (:require [compojure.api.core :refer [route-middleware]]
            [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE context]]
            [ring.middleware.cookies :as cookies]
            [compojure.route :as route]
            [msc.api.session.routes :as sessions]
            [ring.util.response :as response]))

(defn routes
  [system]
  (compojure/api
    {:coercion :spec}

    (route-middleware [cookies/wrap-cookies]

      (GET "/health" []
        (-> (response/response "OK")
            (response/content-type "text/plain; charset=utf-8")))

      (GET "/" []
        (response/resource-response "index.html" {:root "public"}))

      (GET "/login" []
        (response/resource-response "login.html" {:root "public"}))

      (context "/api" []
        #'sessions/routes)

      (route/resources "/"))))
