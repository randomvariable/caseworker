(ns caseworker.db
  (:require [caseworker.layout.db :as layout]))

(def default-db
  {:layout       layout/default-db
   :current-page :dashboard})
