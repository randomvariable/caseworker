(ns caseworker.spec.session
  (:require #?(:clj  [clojure.spec.alpha :as s]
               :cljs [cljs.spec.alpha :as s])
            [spec-tools.spec :as spec]))

(s/def ::google-auth-token spec/string?)
