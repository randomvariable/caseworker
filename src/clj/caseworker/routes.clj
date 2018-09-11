(ns caseworker.routes
  (:require [compojure.api.core :refer [route-middleware]]
            [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE context undocumented]]
            [compojure.route :as route]
            [caseworker.api.account.routes :as accounts]
            [caseworker.api.session.routes :as sessions]
            [caseworker.config :as c]
            [caseworker.middleware.auth :as auth]
            [caseworker.spec.organisation :as org]
            [ring.middleware.cookies :as cookies]
            [ring.util.response :as response]
            [selmer.parser :as selmer]))

(defn routes
  [system]
  (compojure/api
    {:components system
     :coercion :spec
     :swagger {:ui "/docs"
               :spec "/swagger.json"
               :data {:info {:title "Caseworker API"
                             :description "API Backend to Caseworker"}
                      :consumes ["application/json"]
                      :produces ["application/json"]}}}

    (route-middleware
      [cookies/wrap-cookies
       auth/wrap-auth]

      (context "/api" [:as req]
        #'accounts/account-routes
        #'sessions/session-routes)

      (undocumented

        (GET "/" []
          (response/redirect "/login"))

        (GET "/login" [:as req]
          (if (:identity req)
            (response/redirect "/wmag")
            (-> (selmer/render-file "templates/login.html" (select-keys c/env [:google-client-id]))
                (response/response))))

        (GET "/health" []
          (response/response "OK"))

        (route/resources "/")

        (GET "/:org-code" [:as req]
          :path-params [org-code :- ::org/org-code]
          (if (:identity req)
            (-> (selmer/render-file "templates/index.html" (select-keys c/env [:google-client-id]))
                (response/response))
            (response/resource-response "401.html" {:root "public"})))

        (route/not-found "404 Not found")))))
