(ns caseworker.api.session.routes
  (:require [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE defroutes context]]
            [caseworker.config :as c]
            [caseworker.spec.organisation :as org]
            [caseworker.spec.session :as session]
            [ring.util.response :as response]
            [taoensso.timbre :as log]))

(defroutes session-routes
  (context "/session" []
    :tags ["Sessions"]

    (POST "/" []
      :summary "Create a new session using a google auth token"
      :body-params [google-auth-token :- ::session/google-auth-token]
      :return {:success boolean? :org-code ::org/org-code}
      (-> (response/response {:success true :org-code "wmag"})
          (response/set-cookie "CASEWORKER_GOOGLE_AUTH" google-auth-token
                               {:expires "Tue, 19 Jan 2038 00:00:01 GMT"
                                :path "/"
                                :secure (not= "development" (:environment c/env))
                                :http-only true})))

    (DELETE "/" []
      :summary "Delete the user's current session"
      :return {:success boolean?}
      (-> (response/response {:success true})
          (response/set-cookie "CASEWORKER_GOOGLE_AUTH" ""
                               {:expires "Thu, 01 Jan 1970 00:00:01 GMT" :path "/"})))))
