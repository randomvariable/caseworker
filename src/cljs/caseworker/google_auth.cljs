(ns caseworker.google-auth
  (:require [ajax.core :as ajax]
            [caseworker.util :as u]
            [reagent.core :as reagent]))

(defn delete-session
  []
  (ajax/DELETE "/api/session"
    {:handler       #(set! (.-location js/window) "/login")
     :error-handler #(js/alert "There was a problem logging you out - please try again.")}))

(defn logout
  []
  (if-not (exists? js/gapi)
    (js/setTimeout logout 100)
    (.load js/gapi "auth2"
           #(.. js/gapi -auth2 init
                (then (fn [auth] (.. auth signOut (then delete-session))))))))

