(ns caseworker.events
  (:require
   [re-frame.core :as re-frame]
   [caseworker.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-current-page
 (fn [db [_ current-page]]
   (assoc db :current-page current-page)))
