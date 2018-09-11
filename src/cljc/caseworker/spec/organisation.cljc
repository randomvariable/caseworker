(ns caseworker.spec.organisation
  (:require #?(:clj  [clojure.spec.alpha :as s]
               :cljs [cljs.spec.alpha :as s])
            [spec-tools.spec :as spec]))

(s/def ::organisation-id spec/integer?)
(s/def ::name            spec/string?)
(s/def ::slug            spec/string?)
(s/def ::created-by      spec/integer?)
(s/def ::created-at      spec/inst?)
(s/def ::updated-by      spec/integer?)
(s/def ::updated-at      spec/inst?)
(s/def ::deleted-by      (s/nilable spec/integer?))
(s/def ::deleted-at      (s/nilable spec/inst?))
