(ns caseworker.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [caseworker.config :as c]
            [caseworker.migrations :as migrations]
            [caseworker.system :as system]
            [taoensso.timbre :as log]))

(defn -main
  []
  (migrations/migrate)
  (let [system (component/start (system/make-system c/env))]
    (.addShutdownHook (Runtime/getRuntime)
                      (Thread. ^Runnable #(component/stop system)))
    (println (slurp (io/resource "logo.txt")))
    (log/info "Caseworker has started. Press Ctrl-C to quit.")))

(Thread/setDefaultUncaughtExceptionHandler
  (reify Thread$UncaughtExceptionHandler
    (uncaughtException [_ thread ex]
      (log/error ex "Uncaught exception on" (.getName thread)))))
