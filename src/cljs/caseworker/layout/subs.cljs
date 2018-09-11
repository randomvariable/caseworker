(ns caseworker.layout.subs
  (:require
    [caseworker.layout.db :as db]
    [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::db
  (fn [db _]
    (get-in db db/path)))

(re-frame/reg-sub
 ::navbar-expanded?
 :<- [::db]
 (fn [db _]
   (:navbar-expanded? db)))

(re-frame/reg-sub
 ::open-dropdown
 :<- [::db]
 (fn [db _]
   (:open-dropdown db)))
