(ns caseworker.spec.session
  (:require [clojure.spec.alpha :as s]
            [spec-tools.spec :as spec]))

(s/def ::google-auth-token spec/string?)
