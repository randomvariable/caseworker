(ns caseworker.api.organisation.handler
  (:require [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as csk-extras]
            [caseworker.db :as db]
            [caseworker.sql :as sql]
            [clojure.java.jdbc :as jdbc]
            [taoensso.timbre :as log]))

(sql/defsql "caseworker/api/organisation/queries.sql" :as q)

(defn create-organisation!
  [db user organisation]
  (db/create! db :organisation user organisation))

(defn create-organisation-account!
  [db user
   {:keys [organisation-id] :as organisation}
   {:keys [account-id] :as account}]
  (->> {:organisation-id organisation-id :account-id account-id}
       (db/create! db :organisation-account user)))

(defn organisations-for-account
  [db {:keys [account-id] :as account}]
  (q/organisations-for-account db {:account-id account-id}))
