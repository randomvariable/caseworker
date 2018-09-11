(ns caseworker.spec.account
  (:require #?(:clj  [clojure.spec.alpha :as s]
               :cljs [cljs.spec.alpha :as s])
            [spec-tools.spec :as spec]))

(s/def ::account-id spec/integer?)
(s/def ::oauth-id   spec/string?)
(s/def ::email      spec/string?)
(s/def ::name       spec/string?)
(s/def ::created-by spec/integer?)
(s/def ::created-at spec/inst?)
(s/def ::updated-by spec/integer?)
(s/def ::updated-at spec/inst?)
(s/def ::deleted-by (s/nilable spec/integer?))
(s/def ::deleted-at (s/nilable spec/inst?))
