(ns caseworker.accounts.events
  (:require [ajax.core :as ajax]
            [caseworker.accounts.db :as db]
            [caseworker.spec.account :as acc]
            [cljs-time.format :as tf]
            [re-frame.core :as re-frame]
            [taoensso.timbre :as log]))

(re-frame/reg-event-fx
 ::initialize
 [(re-frame/path db/path)]
 (fn [_ _]
   {:db         db/default-db
    :http-xhrio {:method          :get
                 :uri             "/api/accounts"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::fetch-accounts-success]
                 :on-failure      [::fetch-accounts-failure]}}))

(re-frame/reg-event-db
  ::fetch-accounts-success
  [(re-frame/path db/path)]
  (fn [db [_ accounts]]
    (assoc db :accounts
           (map #(update % :created-at tf/parse) accounts))))

(re-frame/reg-event-db
  ::fetch-accounts-failure
  [(re-frame/path db/path)]
  (fn [db [_ accounts]]
    (log/warn "Failed to fetch accounts")
    db))
