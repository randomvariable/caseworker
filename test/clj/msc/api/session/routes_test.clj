(ns msc.api.session.routes-test
  (:require [clojure.test :as t :refer [deftest testing is are run-tests]]
            [msc.routes :as routes]
            [msc.test-helper :as th]
            [ring.mock.request :as mock]))

(deftest create-session-test
  (testing "It stores the given google token in a cookie"
    (th/with-test-system [{:keys [app] :as system}]

      (let [{:keys [status headers body]}
            (-> (mock/request :post "/api/session")
                (mock/json-body {:google-auth-token "abc-123"})
                (th/test-handler system))]

        (is (= 200 status))
        (is (= {:success true} body))
        (is (some #(re-find #"MSC_GOOGLE_AUTH=abc-123" %)
                            (get headers "Set-Cookie")))))))

(deftest delete-session-test
  (testing "It clears the session cookie by setting its expiry to the start of time"
    (th/with-test-system [{:keys [app] :as system}]

      (let [{:keys [status headers body]}
            (-> (mock/request :delete "/api/session")
                (mock/header "Cookie" "MSC_GOOGLE_AUTH_TOKEN=abc-123")
                (th/test-handler system))]

        (is (= 200 status))
        (is (= {:success true} body))
        (is (some #(re-find #"MSC_GOOGLE_AUTH=;Expires=Thu, 01 Jan 1970 00:00:01" %)
                            (get headers "Set-Cookie")))))))
