(ns msc.views
  (:require
   [re-frame.core :as re-frame]
   [msc.components.google-auth :as google-auth]
   [msc.subs :as subs]))

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
  [:div
   [:nav.navbar.navbar-dark.fixed-top.bg-blue.flex-md-nowrap.p-0.shadow
    [:a.navbar-brand.col-sm-3.col-md-2.mr-0 {:href "#" :style {:text-transform "uppercase" :font-weight "bold" :font-family "Arial" :font-size "1.2em" :letter-spacing "2px"}}
     [:img {:src "/img/logo-small.png" :style {:height "30px" :margin "-7px 5px -5px 0"}}]
     " Caseworker"]
    [:input.form-control.form-control-dark.w-100
     {:aria-label "Search", :placeholder "Search", :type "text"}]
    [:ul.navbar-nav.px-3
     [:li.nav-item.text-nowrap
      [google-auth/logout-button]]]]

   [:div.container-fluid
    [:div.row
     [:nav.col-md-2.d-none.d-md-block.bg-blue.sidebar
      [:div.sidebar-sticky
       [:ul.nav.flex-column
        [:li.nav-item
         [:a.nav-link.active
          {:href "#"}
          [:span.feather.icon-home] " Dashboard "
          [:span.sr-only "(current)"]]]
        [:li.nav-item
         [:a.nav-link
          {:href "#"}
          [:span.feather.icon-users] " People"]]
        [:li.nav-item
         [:a.nav-link
          {:href "#"}
          [:span.feather.icon-file] " Resources"]]
        [:li.nav-item
         [:a.nav-link
          {:href "#"}
          [:span.feather.icon-bar-chart-2] " Reports"]]
        ]
       [:h6.sidebar-heading.d-flex.justify-content-between.align-items-center.px-3.mt-4.mb-1
        [:span "Saved reports"]
        [:a.d-flex.align-items-center.text-white
         {:href "#"}
         [:span.feather.icon-plus-circle]]]
       [:ul.nav.flex-column.mb-2
        [:li.nav-item
         [:a.nav-link
          {:href "#"}
          [:span.feather.icon-file-text] " Current month"]]
        [:li.nav-item
         [:a.nav-link
          {:href "#"}
          [:span.feather.icon-file-text] " Year to date"]]
        [:li.nav-item
         [:a.nav-link
          {:href "#"}
          [:span.feather.icon-file-text] " Some other report"]]]]]
     [:main.col-md-9.ml-sm-auto.col-lg-10.px-4
      {:role "main"}
      [:div.d-flex.justify-content-between.flex-wrap.flex-md-nowrap.align-items-center.pt-3.pb-2.mb-3.border-bottom
       [:h1.h2 "Dashboard"]

       [:div.btn-toolbar.mb-2.mb-md-0
        [:div.btn-group.mr-2
         [:button.btn.btn-sm.btn-outline-secondary "Share"]
         [:button.btn.btn-sm.btn-outline-secondary "Export"]]
        [:button.btn.btn-sm.btn-outline-secondary.dropdown-toggle
         [:span {:data-feather "calendar"}]
         "\n                This week\n              "]]]
      (case panel-name
        :home-panel [home-panel]
        :about-panel [about-panel]
        [:div])
      [:h2 "Section title"]
      [:div.table-responsive
       [:table.table.table-striped.table-sm
        [:thead
         [:tr
          [:th "#"]
          [:th "Header"]
          [:th "Header"]
          [:th "Header"]
          [:th "Header"]]]
        [:tbody
         [:tr
          [:td "1,001"]
          [:td "Lorem"]
          [:td "ipsum"]
          [:td "dolor"]
          [:td "sit"]]
         [:tr
          [:td "1,002"]
          [:td "amet"]
          [:td "consectetur"]
          [:td "adipiscing"]
          [:td "elit"]]
         [:tr
          [:td "1,003"]
          [:td "Integer"]
          [:td "nec"]
          [:td "odio"]
          [:td "Praesent"]]
         [:tr
          [:td "1,003"]
          [:td "libero"]
          [:td "Sed"]
          [:td "cursus"]
          [:td "ante"]]
         [:tr
          [:td "1,004"]
          [:td "dapibus"]
          [:td "diam"]
          [:td "Sed"]
          [:td "nisi"]]
         [:tr
          [:td "1,005"]
          [:td "Nulla"]
          [:td "quis"]
          [:td "sem"]
          [:td "at"]]
         [:tr
          [:td "1,006"]
          [:td "nibh"]
          [:td "elementum"]
          [:td "imperdiet"]
          [:td "Duis"]]
         [:tr
          [:td "1,007"]
          [:td "sagittis"]
          [:td "ipsum"]
          [:td "Praesent"]
          [:td "mauris"]]
         [:tr
          [:td "1,008"]
          [:td "Fusce"]
          [:td "nec"]
          [:td "tellus"]
          [:td "sed"]]
         [:tr
          [:td "1,009"]
          [:td "augue"]
          [:td "semper"]
          [:td "porta"]
          [:td "Mauris"]]
         [:tr
          [:td "1,010"]
          [:td "massa"]
          [:td "Vestibulum"]
          [:td "lacinia"]
          [:td "arcu"]]
         [:tr
          [:td "1,011"]
          [:td "eget"]
          [:td "nulla"]
          [:td "Class"]
          [:td "aptent"]]
         [:tr
          [:td "1,012"]
          [:td "taciti"]
          [:td "sociosqu"]
          [:td "ad"]
          [:td "litora"]]
         [:tr
          [:td "1,013"]
          [:td "torquent"]
          [:td "per"]
          [:td "conubia"]
          [:td "nostra"]]
         [:tr
          [:td "1,014"]
          [:td "per"]
          [:td "inceptos"]
          [:td "himenaeos"]
          [:td "Curabitur"]]
         [:tr
          [:td "1,015"]
          [:td "sodales"]
          [:td "ligula"]
          [:td "in"]
          [:td "libero"]]]]]]]]])


(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
