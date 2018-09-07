(ns caseworker.components.google-auth
  (:require [ajax.core :as ajax]
            [caseworker.util :as u]
            [reagent.core :as reagent]))

(defn initialize
  []
  (if-not (exists? js/gapi)
    (js/setTimeout initialize 100)
    (.load js/gapi "auth2" #(.. js/gapi -auth2 init))))

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
           #(doto (.-auth2 js/gapi)
                  (.init)
                  (.. getAuthInstance signOut (then delete-session))))))

(defn reagent-render
  []
  [:button.btn.btn-secondary {:on-click logout} "Log out"])

(def logout-button
  (reagent/create-class {:component-did-mount initialize
                         :reagent-render reagent-render}))
