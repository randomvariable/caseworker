(ns caseworker.layout.navigation-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [caseworker.core :as core]
            [caseworker.layout.events :as e]
            [caseworker.layout.subs :as s]
            [caseworker.layout.views :as v]
            [day8.re-frame.test :as rf-test]
            [re-frame.core :as re-frame]))

(deftest navigation-test
  (testing "collapsible navbar and dropdowns toggle correctly"
    (rf-test/run-test-sync
      (re-frame/dispatch [:caseworker.events/initialize])
      (re-frame/dispatch [:caseworker.events/set-current-page :dashboard])
      (let [navbar-expanded? (re-frame/subscribe [::s/navbar-expanded?])
            open-dropdown    (re-frame/subscribe [::s/open-dropdown])
            current-page     (re-frame/subscribe [::s/current-page])]

        (is (false? @navbar-expanded?))
        (is (nil? @open-dropdown))
        (is (= :dashboard @current-page))

        ;; the navbar can be toggled open and closed
        (re-frame/dispatch [::e/toggle-navbar])
        (is (true? @navbar-expanded?))

        (re-frame/dispatch [::e/toggle-navbar])
        (is (false? @navbar-expanded?))

        ;; individual dropdowns can be toggled open and closed
        (re-frame/dispatch [::e/toggle-dropdown :account])
        (is (= :account @open-dropdown))

        (re-frame/dispatch [::e/toggle-dropdown :account])
        (is (nil? @open-dropdown))

        ;; opening one dropdown overrides any that are already open
        (re-frame/dispatch [::e/toggle-dropdown :account])
        (is (= :account @open-dropdown))

        (re-frame/dispatch [::e/toggle-dropdown :settings])
        (is (= :settings @open-dropdown))

        ;; clicking outside of the dropdowns closes any open dropdown
        (re-frame/dispatch [::e/toggle-dropdown nil])
        (is (nil? @open-dropdown))

        ;; multiple clicks outside of the dropdown have no effect
        (re-frame/dispatch [::e/toggle-dropdown nil])
        (is (nil? @open-dropdown))))))
