(ns caseworker.routes
  (:require [compojure.api.core :refer [route-middleware]]
            [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE context undocumented]]
            [compojure.route :as route]
            [caseworker.api.session.routes :as sessions]
            [caseworker.google-auth :as gauth]
            [caseworker.middleware.auth :as auth]
            [ring.middleware.cookies :as cookies]
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
                      :consumes ["application/json"]
                      :produces ["application/json"]}}}

    (route-middleware [cookies/wrap-cookies]

      (context "/api" []
        #'sessions/session-routes
        (route-middleware [auth/wrap-auth]))

      (undocumented

        (GET "/" [:as req]
          (if (gauth/verify-token (get-in req [:cookies "MSC_GOOGLE_AUTH" :value]))
            (response/resource-response "index.html" {:root "public"})
            (response/redirect "/login")))

        (GET "/login" [:as req]
          (if (gauth/verify-token (get-in req [:cookies "MSC_GOOGLE_AUTH" :value]))
            (response/redirect "/")
            (response/resource-response "login.html" {:root "public"})))

        (GET "/health" []
          (response/response "OK"))

        (route/resources "/")
        (route/not-found "404 Not found")))))
