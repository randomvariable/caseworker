(ns msc.routes
  (:require [compojure.api.core :refer [route-middleware]]
            [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE context undocumented]]
            [ring.middleware.cookies :as cookies]
            [compojure.route :as route]
            [msc.api.session.routes :as sessions]
            [ring.util.response :as response]))

(defn routes
  [system]
  (compojure/api
    {:components system
     :coercion :spec
     :swagger {:ui "/docs"
               :spec "/swagger.json"
               :data {:info {:title "MSC API"
                             :description "API Backend to MSC"}
                      :tags [{:name "api", :description "some apis"}]
                      :consumes ["application/json"]
                      :produces ["application/json"]}}}

    (route-middleware [cookies/wrap-cookies]

      (context "/api" []
        #'sessions/session-routes)

      (undocumented

        (GET "/"       [] (response/resource-response "index.html" {:root "public"}))
        (GET "/login"  [] (response/resource-response "login.html" {:root "public"}))
        (GET "/health" [] (response/response "OK"))

        (route/resources "/")
        (route/not-found "404 Not found")))))
