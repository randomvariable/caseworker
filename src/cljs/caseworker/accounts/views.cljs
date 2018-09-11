(ns caseworker.accounts.views
  (:require [caseworker.re-frame :refer [with-subs]]
            [caseworker.accounts.events :as e]
            [caseworker.accounts.subs :as s]
            [caseworker.time :as t]
            [cljs-time.format :as tf]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn page
  []
  (with-subs [accounts [::s/accounts]]
    [:div [:h1 "Users"]
     [:table.table.table-bordered.table-striped
      [:thead
       [:tr
        [:th "Name"]
        [:th "Email"]
        [:th "Created"]]]
      [:tbody
       (for [{:keys [account-id name email created-at]} accounts]
         ^{:key account-id}
         [:tr
          [:td name]
          [:td [:a {:href (str "mailto:" email)} email]]
          [:td (t/unparse created-at :full)]])]]]))
