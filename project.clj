(defproject msc "0.1.0-SNAPSHOT"
  :dependencies [[camel-snake-kebab "0.4.0"]
                 [cljs-ajax "0.7.4"]
                 [cljsjs/react-bootstrap "0.31.5-0" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [com.layerware/hugsql "0.4.9"]
                 [com.mchange/c3p0 "0.9.5.2"]
                 [com.stuartsierra/component "0.3.2"]
                 [com.taoensso/timbre "4.10.0"]
                 [compojure "1.6.1"]
                 [com.google.api-client/google-api-client "1.25.0"]
                 [com.google.oauth-client/google-oauth-client "1.25.0"]
                 [expound "0.7.1"]
                 [metosin/compojure-api "2.0.0-alpha25"]
                 [metosin/spec-tools "0.7.1"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.clojure/spec.alpha "0.2.176"]
                 [org.postgresql/postgresql "42.2.5"]
                 [prismatic/schema "1.1.9"]
                 [ragtime "0.7.2"]
                 [raven-clj "1.5.2" :exclusions [cheshire commons-logging clj-http]]
                 [re-frame "0.10.6" :exclusions [org.clojure/tools.logging]]
                 [reagent "0.8.1"]
                 [ring "1.6.3"]
                 [ring-jetty-component "0.3.1"]
                 [secretary "1.2.3"]
                 [venantius/accountant "0.2.4"]
                 [yogthos/config "1.1.1"]

                 ;; dependency clash fixes
                  [com.google.guava/guava "22.0"]]

  :repl-options {:init-ns user}

  :plugins [[lein-ancient "0.6.15"]
            [lein-cljsbuild "1.1.7"]
            [lein-environ "1.1.0"]
            [lein-shell "0.5.0"]
            ;; dependency clash fixes
            [lein-create-template "0.2.0" :exclusions [org.clojure/clojure]]]
  :min-lein-version "2.5.3"
  :source-paths ["src/clj" "src/cljc" "src/cljs"]
  :test-paths ["test/clj" "test/cljc" "test/cljs"]
  :uberjar-name "msc.jar"
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target" "test/js"]
  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.16"]
                                  [org.clojure/test.check "0.10.0-alpha3"]
                                  [reloaded.repl "0.2.4"]
                                  [re-frisk "0.5.4"]
                                  [ring/ring-mock "0.3.2"]]
                   :source-paths ["src/clj" "src/cljc" "src/cljs" "dev/src"]
                   :resource-paths ["dev/resources"]
                   :plugins [[healthunlocked/lein-docker-compose "0.2.0"]
                             [lein-figwheel "0.5.16"]
                             [lein-doo "0.1.10"]]
                   :prep-tasks ["docker-compose"]}
             :uberjar {:aot :all
                       :source-paths ["src/clj" "src/cljc"]
                       :prep-tasks [["cljsbuild" "once" "min"]
                                    ["shell" "sass" "src/sass/application.sass" "resources/public/css/compiled/application.css"]]}
             :prod {:dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs" "src/cljc"]
     :figwheel     {:on-jsload "msc.core/mount-root"}
     :compiler     {:main                 msc.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload re-frisk.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs" "src/cljc"]
     :jar true
     :compiler     {:main            msc.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :externs         ["resources/externs/gauth.js"]
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "src/cljc" "test/cljs"]
     :compiler     {:main          msc.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}]}

  :main ^:skip-aot msc.core
  :target-path "target/%s")
