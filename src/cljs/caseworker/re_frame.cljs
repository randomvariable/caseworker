(ns caseworker.re-frame
  (:require [accountant.core :as accountant]
            [re-frame.core :as re-frame]
            [taoensso.timbre :as log])
  (:require-macros caseworker.re-frame))

(re-frame/reg-fx
  :change-route
  (fn [new-route query]
    (accountant/navigate! new-route (or query {}))))
