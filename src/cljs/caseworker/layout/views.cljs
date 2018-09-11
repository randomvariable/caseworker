(ns caseworker.layout.views
  (:require
    [re-frame.core :as re-frame]
    [reagent.core :as reagent]
    [caseworker.google-auth :as gauth]
    [caseworker.layout.events :as e]
    [caseworker.layout.subs :as s]
    [caseworker.re-frame :refer [with-subs]]))

(defn nav-item
  [current-page page-name url icon-class label]
  [:li.nav-item (when (= page-name current-page) {:class "active"})
   [:a.nav-link
    {:href (str "#" url)}
    [:span.feather {:class icon-class}] (str " " label)
    (when (= page-name current-page)
      [:span.sr-only "(current)"])]])

(defn main-nav
  []
  (with-subs [current-page [:caseworker.subs/current-page]]
    [:ul.navbar-nav.mr-auto
     [nav-item current-page :dashboard "/"          "icon-home"        "Dashboard"]
     [nav-item current-page :people    "/people"    "icon-users"       "People"]
     [nav-item current-page :cases     "/cases"     "icon-briefcase"   "Cases"]
     [nav-item current-page :resources "/resources" "icon-file"        "Resources"]
     [nav-item current-page :reports   "/reports"   "icon-bar-chart-2" "Reports"]]))

(defn user-menu
  []
  (with-subs [current-user  [:caseworker.subs/current-user]
              open-dropdown [::s/open-dropdown]]
    (when current-user
      [:div.dropdown-menu.dropdown-menu-right
       {:class (when (= open-dropdown :account) "show")
        :style {:min-width "200px" :white-space "nowrap"}}
       [:span.dropdown-item-text (:name current-user)]
       [:span.dropdown-item-text "Walthamstow Migrants' Action Group"]
       [:div.dropdown-divider]
       [:a.dropdown-item {:href "#/accounts"} [:span.feather.icon-users] " Manage users"]
       [:a.dropdown-item {:href "#" :on-click gauth/logout} [:span.feather.icon-log-out] " Log out"]])))

(defn settings-menu
  []
  (with-subs [open-dropdown [::s/open-dropdown]]
    [:div.dropdown-menu.dropdown-menu-right
     (when (= open-dropdown :settings) {:class "show"})
     [:a.dropdown-item {:href "#"} "Action"]
     [:a.dropdown-item {:href "#"} "Another action"]
     [:a.dropdown-item {:href "#"} "Something else here"]]))

(defn navbar
  []
  (with-subs [navbar-expanded? [::s/navbar-expanded?]
              open-dropdown    [::s/open-dropdown]]
    [:nav.navbar.navbar-expand-md.fixed-top.navbar-dark.bg-dark
     {:style {:border-bottom "5px solid #ccbb55"}}
     [:a.navbar-brand {:href "/"} [:img {:src "/img/logo-with-text.svg"}]]       
     [:button.navbar-toggler.p-0.border-0
      {:data-toggle "offcanvas"
       :type "button"
       :on-click #(do (re-frame/dispatch [::e/toggle-navbar])
                      (.stopPropagation %)
                      (.preventDefault %))}
      [:span.navbar-toggler-icon]]
     [:div.navbar-collapse.offcanvas-collapse (when navbar-expanded? {:class "open"})
      [main-nav] 
      [:ul.navbar-nav.navbar-right
       [:li.nav-item.dropdown 
        [:a#dropdown01.nav-link.dropdown-toggle
         {:aria-expanded "false"
          :aria-haspopup "true"
          :data-toggle "dropdown"
          :on-click #(do (re-frame/dispatch [::e/toggle-dropdown :settings])
                         (.stopPropagation %)
                         (.preventDefault %))
          :href "#"}
         [:span.feather.icon-settings] [:span.d-inline.d-sm-inline.d-md-none " Settings"]]
        [settings-menu]]
       [:li.nav-item.dropdown 
        [:a#dropdown01.nav-link.dropdown-toggle
         {:aria-expanded "false"
          :aria-haspopup "true"
          :data-toggle "dropdown"
          :on-click #(do (re-frame/dispatch [::e/toggle-dropdown :account])
                         (.stopPropagation %)
                         (.preventDefault %))
          :href "#"}
         [:span.feather.icon-user] [:span.d-inline.d-sm-inline.d-md-none " Account"]]
        [user-menu]]]]]))

(defn default [& forms]
  [:div#main-content [navbar] (into [:div.container-fluid{:style {:height "100%"}}] forms)])
