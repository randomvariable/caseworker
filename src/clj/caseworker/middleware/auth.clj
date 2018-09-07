(ns caseworker.middleware.auth
  (:require [caseworker.google-auth :as gauth]))

(defn wrap-auth
  [handler]
  (fn [{:keys [headers] :as req}]
    (if-let [user (gauth/verify-token (get headers "MSC_GOOGLE_AUTH"))]
      (-> (assoc-in req :identity user)
          (handler))
      {:status 401
       :body  "Not authenticated"})))
