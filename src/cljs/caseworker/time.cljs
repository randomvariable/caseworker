(ns caseworker.time
  (:require [cljs-time.format :as tf]))

(def formats
  {:full (tf/formatter "dd/MM/yyyy HH:mm:ss")})

(defn unparse
  [t f]
  (tf/unparse (get formats :full) t))
