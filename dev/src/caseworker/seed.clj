(ns caseworker.seed
  (:require [camel-snake-kebab.core :as csk]
            [caseworker.api.account.handler :as accounts]
            [caseworker.api.organisation.handler :as orgs]
            [caseworker.db :as db]
            [clojure.java.jdbc :as jdbc]
            [reloaded.repl :refer [system]]
            [taoensso.timbre :as log]))

(defn clean-up-old-data!
  [db]
  (log/info "Deleting old data")
  (doseq [table [:organisation-account :organisation]]
    (let [table         (csk/->snake_case_string table)
          history-table (str table "_history")]
      (jdbc/delete! db history-table [])
      (jdbc/delete! db table [])))
  (jdbc/delete! db :account_history ["account_id != -1"])
  (jdbc/delete! db :account ["account_id != -1"])
  (log/info "Finished deleting old data\n"))

(defn create-test-user!
  [db system-user name email]
  (log/info " * " name)
  (accounts/create-account! db system-user {:name name :email email}))

(defn create-test-organisation!
  [db system-user name slug]
  (log/info " * " name)
  (orgs/create-organisation! db system-user {:name name :slug slug}))

(defn create-test-organisation-account!
  [db system-user organisation account]
  (log/info " * " (:name account) "@" (:name organisation))
  (orgs/create-organisation-account! db system-user organisation account))

(defn seed
  [db]
  (if-not (re-find #"localhost" (:subname db))
    (log/error "You can only run seed scripts on your local database.")
    (let [system-user (db/get-by-id db :account -1)]

      (log/info "[SEEDING DEVELOPMENT DATABASE]")
      (clean-up-old-data! db)

      (let [_         (log/info "Creating users")

            russell   (create-test-user! db system-user "Russell"   "russell@caseworker-oss.org")
            naadir    (create-test-user! db system-user "Naadir"    "naadir@caseworker-oss.org")
            christian (create-test-user! db system-user "Christian" "christian@caseworker-oss.org")

            _         (log/info "Creating organisations")
            wmag      (create-test-organisation! db system-user "Walthamstow Migrants' Action Group" "wmag")
            test-org  (create-test-organisation! db system-user "Test Organisation #2"               "test-org")
            _         (log/info "Creating organisation accounts")
            _         (create-test-organisation-account! db system-user wmag     russell)
            _         (create-test-organisation-account! db system-user wmag     naadir)
            _         (create-test-organisation-account! db system-user wmag     christian)
            _         (create-test-organisation-account! db system-user test-org russell)
            _         (create-test-organisation-account! db system-user test-org naadir)
            _         (create-test-organisation-account! db system-user test-org christian)
            ]
        (log/info "[SEED COMPLETE]")))))
