(ns caseworker.views
  (:require
    [re-frame.core :as re-frame]
    [reagent.core :as reagent]
    [caseworker.components.google-auth :as google-auth]
    [caseworker.subs :as subs]))

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [:a {:href "#/about"}
       "go to About Page"]]
     ]))


;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])


;; main



(defn- panels [panel-name]
  (let [open? (reagent/atom false)]
    (fn []
      [:div
       [:nav.navbar.navbar-expand-md.fixed-top.navbar-dark.bg-dark
        {:style {:border-bottom "5px solid #ccbb55"}}
        [:a.navbar-brand {:href "/"}
         [:img {:src "/img/logo-with-text.svg"}]]
        [:button.navbar-toggler.p-0.border-0
         {:data-toggle "offcanvas"
          :type "button"
          :on-click #(swap! open? not)}
         [:span.navbar-toggler-icon]]
        [:div#navbarsExampleDefault.navbar-collapse.offcanvas-collapse (when @open? {:class "open"})
         [:ul.navbar-nav.mr-auto
          [:li.nav-item.active
           [:a.nav-link
            {:href "#"}
            [:span.feather.icon-home] " Dashboard"
            [:span.sr-only "(current)"]]]
          [:li.nav-item [:a.nav-link {:href "#"} [:span.feather.icon-users] " People"]]
          [:li.nav-item [:a.nav-link {:href "#"} [:span.feather.icon-file] " Resources"]]
          [:li.nav-item [:a.nav-link {:href "#"} [:span.feather.icon-bar-chart-2] " Reports"]]
          ]
         [:ul.navbar-nav.navbar-right
          [:li.nav-item.dropdown
           [:a#dropdown01.nav-link.dropdown-toggle
            {:aria-expanded "false",
             :aria-haspopup "true",
             :data-toggle "dropdown",
             :href "#"}
            [:span.feather.icon-settings] [:span.d-inline.d-sm-inline.d-md-none " Settings"]]
           [:div.dropdown-menu
            {:aria-labelledby "dropdown01"}
            [:a.dropdown-item {:href "#"} "Action"]
            [:a.dropdown-item {:href "#"} "Another action"]
            [:a.dropdown-item {:href "#"} "Something else here"]]]
          [:li.nav-item.dropdown
           [:a#dropdown01.nav-link.dropdown-toggle
            {:aria-expanded "false",
             :aria-haspopup "true",
             :data-toggle "dropdown",
             :href "#"}
            [:span.feather.icon-user][:span.d-inline.d-sm-inline.d-md-none " Account"]]
           [:div.dropdown-menu
            [google-auth/logout-button]]]]]]])))


(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
