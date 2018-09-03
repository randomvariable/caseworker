(ns msc.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [msc.core-test]))

(doo-tests 'msc.core-test)
