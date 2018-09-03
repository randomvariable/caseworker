(ns msc.util)

(defn redirect-to
  [url]
  (set! (.-loction js/window) url))
