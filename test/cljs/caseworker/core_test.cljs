(ns caseworker.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [caseworker.core :as core]))

(deftest fake-test
  (testing "pointless test"
    (is (= 1 1))))
