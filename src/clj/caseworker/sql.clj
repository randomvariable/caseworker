(ns caseworker.sql
  (:require [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as csk-extras]
            [hugsql.core :as hugsql]
            [taoensso.timbre :as log]))

(defmacro defsql
  [& args]
  (let [files (vec (drop-last 2 args))
        alias (last args)]
    `(let [current-namespace# *ns*
           query-namespace#   (symbol (str (ns-name *ns*) ".sql"))]
       (create-ns query-namespace#)
       (in-ns query-namespace#)
       (require '[hugsql.core :as hugsql])
       (doseq [file# ~files]
         (hugsql/def-db-fns file# {:command-options [{:identifiers csk/->kebab-case-keyword}]}))
       (in-ns (ns-name current-namespace#))
       (alias '~alias query-namespace#))))
