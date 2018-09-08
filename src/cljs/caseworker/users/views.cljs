(ns caseworker.users.views
  (:require [caseworker.re-frame :refer [with-subs]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn page
  []
  [:div [:h1 "Users"]])
