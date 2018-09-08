(ns caseworker.api.session.routes-test
  (:require [clojure.test :as t :refer [deftest testing is are run-tests]]
            [clojure.spec.alpha :as spec]
            [clojure.spec.test.alpha :as spec-test]
            [expound.alpha :as expound] 
            [caseworker.routes :as routes]
            [caseworker.spec.session :as s]
            [caseworker.test-helper :as th]
            [ring.mock.request :as mock]))

(defn contains-session-cookie?
  []
  #(some (partial re-find (re-pattern (str "CASEWORKER_GOOGLE_AUTH=" (-> % :args :token) ";")))
         (get-in % [:ret :headers "Set-Cookie"])))

(defn expires-session-cookie?
  []
  #(some (partial re-find (re-pattern #"CASEWORKER_GOOGLE_AUTH=;Expires=Thu, 01 Jan 1970"))
         (get-in % [:ret :headers "Set-Cookie"])))

(deftest session-test
  (th/with-test-system
    [system]

    (th/integration-test
      "Sessions are stored in a cookie when created."
      [token ::s/google-auth-token]

      :test (-> (mock/request :post "/api/session")
                (mock/json-body {:google-auth-token token})
                (th/test-handler system))

      :spec (spec/and (th/has-status? 200)
                      (th/has-body? {:success true})
                      (contains-session-cookie?)))

    (th/integration-test
      "Sessions cookies are removed when the session is deleted."
      [token ::s/google-auth-token]

      :test (-> (mock/request :delete "/api/session")
                (mock/header "Cookie" (str "CASEWORKER_GOOGLE_AUTH_TOKEN=" token ";"))
                (th/test-handler system))

      :spec (spec/and (th/has-status? 200)
                      (th/has-body? {:success true})
                      (expires-session-cookie?)))))
