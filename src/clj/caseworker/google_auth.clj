(ns caseworker.google-auth
  (:require [caseworker.config :as c]
            [caseworker.util :as u]
            [taoensso.timbre :as log])
  (:import [com.google.api.client.googleapis.auth.oauth2 GoogleIdTokenVerifier$Builder]
           [com.google.api.client.json.jackson2 JacksonFactory]
           [com.google.api.client.http.javanet NetHttpTransport]))

(def token-verifier
  "A token verifier instance for the configured google client ID."
  (let [jsonFactory (JacksonFactory.)
        transport   (NetHttpTransport.)]
    (.. (GoogleIdTokenVerifier$Builder. transport jsonFactory)
        (setAudience (list (c/env :google-client-id)))
        (build))))

(defn verify-token
  [token]
  "Returns GoogleTokenId instance if the token is valid"
  (when-let [payload (some->> token (.verify token-verifier) .getPayload)]
    {:subject  (.getSubject payload)
     :email    (get payload "email")
     :name     (str (get payload "given_name") " " (get payload "family_name"))
     :locale   (get payload "locale")}))
