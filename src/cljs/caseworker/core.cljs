(ns caseworker.core
  (:require [day8.re-frame.http-fx] 
            [goog.events :as e]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [caseworker.events :as events]
            [caseworker.routes :as routes]
            [caseworker.views :as views]
            [caseworker.config :as config]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-page]
                  (.getElementById js/document "app")))

(defn init-global-listeners
  []
  (e/listen js/window "click"
            #(re-frame/dispatch [:caseworker.layout.events/toggle-dropdown nil])))

(defn ^:export init []
  (init-global-listeners)
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize])
  (dev-setup)
  (mount-root))
