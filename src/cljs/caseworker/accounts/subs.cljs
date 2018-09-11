(ns caseworker.accounts.subs
  (:require
    [caseworker.accounts.db :as db]
    [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::db
  (fn [db _]
    (get-in db db/path)))

(re-frame/reg-sub
 ::accounts
 :<- [::db]
 (fn [db _]
   (:accounts db)))
