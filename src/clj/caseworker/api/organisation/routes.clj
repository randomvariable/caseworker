(ns caseworker.api.organisation.routes
  (:require [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE defroutes context]]
            [caseworker.api.organisation.handler :as h]
            [caseworker.config :as c]
            [caseworker.spec.account :as acc]
            [caseworker.spec.organisation :as org]
            [clojure.spec.alpha :as s]
            [ring.util.response :as response]
            [spec-tools.core :as st]
            [spec-tools.spec :as sts]
            [spec-tools.transform :as stt]
            [taoensso.timbre :as log]))

(defroutes organisation-routes
  (context "/organisations" []
    :tags ["Organisation"]

    (GET "/:account-id" []
      :summary     "Returns a list of the user accounts in the organisation."
      :path-params [account-id :- ::acc/account-id]
      :components  [db]
      :return      (s/coll-of (s/keys :req-un [::org/organisation-id ::org/name ::org/slug]))
      (->> (h/organisations-for-account db)
           (map #(select-keys % [:organisation-id :name :slug]))
           (response/response)))))
