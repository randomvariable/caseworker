(ns caseworker.middleware.auth
  (:require [caseworker.google-auth :as gauth]
            [taoensso.timbre :as log]))

(defn wrap-auth
  [handler]
  (fn [{:keys [cookies] :as req}]
    (if-let [token (get-in cookies ["CASEWORKER_GOOGLE_AUTH" :value])] 
      (->> (gauth/verify-token token)
           (assoc req :identity)
           (handler))
      (handler req))))
