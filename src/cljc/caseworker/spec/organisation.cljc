(ns caseworker.spec.organisation
  (:require #?(:clj  [clojure.spec.alpha :as s]
               :cljs [cljs.spec.alpha :as s])
            [spec-tools.spec :as spec]))

(s/def ::org-code spec/string?)
