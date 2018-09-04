(ns msc.api.session.routes
  (:require [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE defroutes context]]
            [msc.config :as c]
            [msc.spec.session :as s]
            [ring.util.response :as response]
            [taoensso.timbre :as log]))

(defroutes session-routes
  (context "/session" []
    :tags ["Sessions"]

    (POST "/" []
      :summary "Create a new session using a google auth token"
      :body-params [google-auth-token :- ::s/google-auth-token]
      :return {:success boolean?}
      (-> (response/response {:success true})
          (response/set-cookie "MSC_GOOGLE_AUTH" google-auth-token
                               {:expires "Tue, 19 Jan 2038 00:00:01 GMT"
                                :path "/"
                                :secure (not= "development" (:environment c/env))
                                :http-only true})))

    (DELETE "/" []
      :summary "Delete the user's current session"
      :return {:success boolean?}
      (-> (response/response {:success true})
          (response/set-cookie "MSC_GOOGLE_AUTH" ""
                               {:expires "Thu, 01 Jan 1970 00:00:01 GMT" :path "/"})))))
