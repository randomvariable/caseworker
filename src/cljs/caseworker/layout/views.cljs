(ns caseworker.layout.views
  (:require
    [re-frame.core :as re-frame]
    [reagent.core :as reagent]
    [caseworker.google-auth :as gauth]
    [caseworker.layout.events :as e]
    [caseworker.layout.subs :as s]
    [caseworker.re-frame :refer [with-subs]]))

(defn _navbar
  [navbar-expanded? open-dropdown]
  [:nav.navbar.navbar-expand-md.fixed-top.navbar-dark.bg-dark
   {:style {:border-bottom "5px solid #ccbb55"}}
   [:a.navbar-brand {:href "/"} [:img {:src "/img/logo-with-text.svg"}]]       
   [:button.navbar-toggler.p-0.border-0
    {:data-toggle "offcanvas"
     :type "button"
     :on-click #(do (re-frame/dispatch [::e/toggle-navbar])
                    (.stopPropagation %))}
    [:span.navbar-toggler-icon]]
   [:div.navbar-collapse.offcanvas-collapse (when navbar-expanded? {:class "open"})
    [:ul.navbar-nav.mr-auto
     [:li.nav-item.active
      [:a.nav-link
       {:href "#"}
       [:span.feather.icon-home] " Dashboard"
       [:span.sr-only "(current)"]]]
     [:li.nav-item [:a.nav-link {:href "#"} [:span.feather.icon-users] " People"]]
     [:li.nav-item [:a.nav-link {:href "#"} [:span.feather.icon-file] " Resources"]]
     [:li.nav-item [:a.nav-link {:href "#"} [:span.feather.icon-bar-chart-2] " Reports"]]]
    [:ul.navbar-nav.navbar-right
     [:li.nav-item.dropdown 
      [:a#dropdown01.nav-link.dropdown-toggle
       {:aria-expanded "false"
        :aria-haspopup "true"
        :data-toggle "dropdown"
        :on-click #(do (re-frame/dispatch [::e/toggle-dropdown :settings])
                       (.stopPropagation %))
        :href "#"}
       [:span.feather.icon-settings] [:span.d-inline.d-sm-inline.d-md-none " Settings"]]
      [:div.dropdown-menu.dropdown-menu-right
       (when (= open-dropdown :settings) {:class "show"})
       [:a.dropdown-item {:href "#"} "Action"]
       [:a.dropdown-item {:href "#"} "Another action"]
       [:a.dropdown-item {:href "#"} "Something else here"]]]
     [:li.nav-item.dropdown 
      [:a#dropdown01.nav-link.dropdown-toggle
       {:aria-expanded "false"
        :aria-haspopup "true"
        :data-toggle "dropdown"
        :on-click #(do (re-frame/dispatch [::e/toggle-dropdown :account])
                       (.stopPropagation %))
        :href "#"}
       [:span.feather.icon-user] [:span.d-inline.d-sm-inline.d-md-none " Account"]]
      [:div.dropdown-menu.dropdown-menu-right
       (when (= open-dropdown :account) {:class "show"})
       [:a.dropdown-item {:href "#" :on-click gauth/logout}
        "Log out"]]]]]])

(defn navbar
  []
  (let [navbar-expanded? (re-frame/subscribe [::s/navbar-expanded?])
        open-dropdown    (re-frame/subscribe [::s/open-dropdown])]
    (fn [] [_navbar @navbar-expanded? @open-dropdown])))

(defn default [& forms]
  (into [:div [navbar]] forms))
