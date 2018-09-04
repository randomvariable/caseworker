(ns msc.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [msc.core :as core]))

(deftest fake-test
  (testing "pointless test"
    (is (= 1 1))))
