(ns caseworker.events
  (:require [ajax.core :as ajax]
            [re-frame.core :as re-frame]
            [caseworker.db :as db]
            [taoensso.timbre :as log]))

(re-frame/reg-event-fx
 ::initialize
 (fn [_ _]
   {:db db/default-db
    :http-xhrio {:method          :get
                 :uri             "/api/session"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::fetch-current-user-success]
                 :on-failure      [::fetch-current-user-failure]}}))

(re-frame/reg-event-db
  ::fetch-current-user-success
  (fn [db [_ user]]
    (assoc db :current-user user)))

(re-frame/reg-event-db
  ::fetch-current-user-failure
  (fn [db [_ user]]
    (log/warn "Failed to fetch current user")
    db))

(re-frame/reg-event-db
 ::set-current-page
 (fn [db [_ current-page]]
   (assoc db :current-page current-page)))
