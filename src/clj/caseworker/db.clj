(ns caseworker.db
  (:require [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as csk-extras]
            [clojure.java.jdbc :as jdbc]
            [taoensso.timbre :as log]))

(def map->kebab-case
  (partial csk-extras/transform-keys csk/->kebab-case-keyword))

(def map->snake-case
  (partial csk-extras/transform-keys csk/->snake_case_string))

(defn get-by-id
  [db table id]
  (let [table (csk/->snake_case_string table)
        p-key (str table "_id")]
    (->> (jdbc/get-by-id db table id p-key)
         (map->kebab-case))))

(defn create!
  [db table user record]
  (let [table         (csk/->snake_case_string table)
        history-table (str (name table) "_history")]
    (->> (assoc record
                :created-by (:account-id user)
                :updated-by (:account-id user))
         (map->snake-case)
         (jdbc/insert! db table)
         (first)
         (jdbc/insert! db history-table)
         (first)
         (map->kebab-case))))

(defn update!
  [db table record]
  (let [table         (csk/->snake_case_string table)
        history-table (str (name table) "_history")]
    (->> (map->snake-case record)
         (jdbc/insert! db table)
         (jdbc/insert! db history-table)
         (map->kebab-case))))
