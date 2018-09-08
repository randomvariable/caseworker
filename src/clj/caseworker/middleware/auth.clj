(ns caseworker.middleware.auth
  (:require [caseworker.google-auth :as gauth]))

(defn wrap-auth
  [handler]
  (fn [{:keys [headers] :as req}]
    (if-let [user (gauth/verify-token (get headers "CASEWORKER_GOOGLE_AUTH"))]
      (-> (assoc-in req :identity user)
          (handler))
      {:status 401
       :body  "Not authenticated"})))
