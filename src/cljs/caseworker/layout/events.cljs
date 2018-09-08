(ns caseworker.layout.events
  (:require
   [re-frame.core :as re-frame]
   [caseworker.layout.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 [(re-frame/path db/path)]
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
  ::toggle-navbar
 [(re-frame/path db/path)]
  (fn [db _]
    (update db :navbar-expanded? not)))

(re-frame/reg-event-db
  ::toggle-dropdown
 [(re-frame/path db/path)]
  (fn [db [_ k]]
    (update db :open-dropdown #(when (not= k %) k))))
