(ns caseworker.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [caseworker.re-frame :refer [with-subs]]
            [caseworker.subs :as s]
            [caseworker.layout.views :as layout]
            [caseworker.cases.views :as cases]
            [caseworker.dashboard.views :as dashboard]
            [caseworker.people.views :as people]
            [caseworker.reports.views :as reports]
            [caseworker.resources.views :as resources]))

(defn pages
  [page-name]
  (case page-name
    :dashboard [layout/default [dashboard/page]]
    :people    [layout/default [people/page]]
    :cases     [layout/default [cases/page]]
    :resources [layout/default [resources/page]]
    :reports   [layout/default [reports/page]]
    [:div]))

(defn show-page
  [page-name]
  [pages page-name])

(defn main-page
  []
  [show-page @(re-frame/subscribe [::s/current-page])])
