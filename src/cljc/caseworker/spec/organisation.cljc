(ns caseworker.spec.organisation
  (:require [clojure.spec.alpha :as s]
            [spec-tools.spec :as spec]))

(s/def ::org-code spec/string?)
