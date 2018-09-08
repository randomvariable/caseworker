(ns caseworker.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [caseworker.layout.navigation-test]))

(doo-tests 'caseworker.layout.navigation-test)
