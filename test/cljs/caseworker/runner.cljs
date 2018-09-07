(ns caseworker.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [caseworker.core-test]))

(doo-tests 'caseworker.core-test)
