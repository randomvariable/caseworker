(ns caseworker.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require
   [secretary.core :as secretary]
   [goog.events :as gevents]
   [goog.history.EventType :as EventType]
   [re-frame.core :as re-frame]
   [caseworker.events :as events]
   ))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (re-frame/dispatch [::events/set-current-page :dashboard]))

  (defroute "/people" []
    (re-frame/dispatch [::events/set-current-page :people]))

  (defroute "/cases" []
    (re-frame/dispatch [::events/set-current-page :cases]))

  (defroute "/resources" []
    (re-frame/dispatch [::events/set-current-page :resources]))

  (defroute "/reports" []
    (re-frame/dispatch [::events/set-current-page :reports]))

  (defroute "/accounts" []
    (re-frame/dispatch-sync [:caseworker.accounts.events/initialize])
    (re-frame/dispatch [::events/set-current-page :accounts]))

  (hook-browser-navigation!))
