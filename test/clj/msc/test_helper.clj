(ns msc.test-helper
  (:require [cheshire.core :as json]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [msc.config :as c]
            [msc.system :as system]
            [ragtime.jdbc :as jdbc]
            [ragtime.repl :as ragtime]))

(def test-env
  (update c/env :db assoc :user "msc_test" :dbname "msc_test"))

(def config
  {:datastore  (jdbc/sql-database test-env)
   :migrations (jdbc/load-resources "migrations")})

(defn parse-json
  [body]
  (cond (string? body) (json/decode body keyword)
        (some? body)   (json/decode-stream (io/reader body) keyword)))

(defn test-handler
  [request system]
  (let [response ((get-in system [:app :handler]) request)]
    (if (= "application/json; charset=utf-8"
           (get-in response [:headers "Content-Type"]))
      (update response :body parse-json)
      response)))

(defmacro with-test-system
  [[system-binding] & forms]
  `(let [system#         (component/start (system/make-system test-env))
         ~system-binding system#]
     (try ~@forms
       (finally
         (component/stop system#)))))
