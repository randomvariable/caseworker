(ns caseworker.util)

(defn map-keys
  "Map a function over the keys of a map, returning a new map."
  [f m]
  (into {} (map (fn [[k v]] [(f k) v]) m)))
