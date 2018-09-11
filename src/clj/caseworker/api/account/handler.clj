(ns caseworker.api.account.handler
  (:require [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as csk-extras]
            [caseworker.db :as db]
            [caseworker.sql :as sql]
            [clojure.java.jdbc :as jdbc]
            [taoensso.timbre :as log]))

(sql/defsql "caseworker/api/account/queries.sql" :as q)

(defn create-account!
  [db user account]
  (db/create! db :account user account))

(defn accounts-for-organisation
  [db]
  (q/accounts-for-organisation db))
