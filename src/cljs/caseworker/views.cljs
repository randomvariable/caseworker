(ns caseworker.views
  (:require
    [re-frame.core :as re-frame]
    [reagent.core :as reagent]
    [caseworker.subs :as s]
    [caseworker.layout.views :as layout]))

(defn home-panel []
  (let [name (re-frame/subscribe [::s/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [:a {:href "#/about"}
       "go to About Page"]]]))


;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])


(defn panels
  [panel-name]
  (case panel-name
   :home-panel [layout/default [home-panel]]
   :about-panel [layout/default [about-panel]]
   [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::s/active-panel])]
    [show-panel @active-panel]))
