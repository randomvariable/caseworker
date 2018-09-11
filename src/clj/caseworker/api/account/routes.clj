(ns caseworker.api.account.routes
  (:require [compojure.api.sweet :as compojure :refer [GET POST PUT DELETE defroutes context]]
            [caseworker.api.account.handler :as h]
            [caseworker.config :as c]
            [caseworker.spec.account :as acc]
            [clojure.spec.alpha :as s]
            [ring.util.response :as response]
            [spec-tools.core :as st]
            [spec-tools.spec :as sts]
            [spec-tools.transform :as stt]
            [taoensso.timbre :as log]))

(defroutes account-routes
  (context "/accounts" []
    :tags ["Accounts"]

    (GET "/" []
      :summary    "Returns a list of the user accounts in the organisation."
      :components [db]
      :return     (s/coll-of (s/keys :req-un [::acc/account-id ::acc/email ::acc/name ::acc/created-at]))
      (->> (h/accounts-for-organisation db)
           (map #(select-keys % [:account-id :email :name :created-at]))
           (response/response)))))
