(ns caseworker.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::current-page
 (fn [db _]
   (:current-page db)))

(re-frame/reg-sub
 ::current-user
 (fn [db _]
   (:current-user db)))
