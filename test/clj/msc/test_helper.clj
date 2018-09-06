(ns msc.test-helper
  (:require [cheshire.core :as json]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as spec]
            [clojure.spec.test.alpha :as spec-test]
            [com.stuartsierra.component :as component]
            [expound.alpha :as expound] 
            [msc.config :as c]
            [msc.system :as system]
            [ragtime.jdbc :as jdbc]
            [ragtime.repl :as ragtime]))

(set! spec/*explain-out* expound/printer)

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

(defmacro integration-test
  [description args-and-specs & {:keys [test spec]}]
  (let [args      (take-nth 2 args-and-specs)
        arg-specs (interleave (map keyword args) (take-nth 2 (rest args-and-specs)))]
    `(clojure.test/testing ~description
       (let [test-fn#   (fn [~@args] ~test)
             test-spec# (spec/fspec :args (spec/cat ~@arg-specs) :fn ~spec)
             result#    (spec-test/check-fn test-fn# test-spec#)]
         (clojure.test/is (true? (or (not (:failure result#))
                                     (expound/explain-result result#))))))))

(defn has-status?
  [status]
  #(= status (get-in % [:ret :status])))

(defn has-body?
  [body]
  #(= body (get-in % [:ret :body])))
