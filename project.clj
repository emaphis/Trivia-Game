(defproject trivia "0.0.1-SNAPSHOT"
  :description "Trivia Game"
  :url "https://www.buildfunthings.com"

  :license {:name "GPLv3"
            :url "http://choosealicense.com/licenses/gpl-3.0/#"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [reagent "0.6.0"]
                 [re-frame "0.8.0"]]

  :plugins [[lein-cljsbuild "1.1.4"]]

  :min-lein-version "2.6.1"

  :main ^:skip-aot trivia.core

  :target-path "target/%s"

  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["test/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "test/js"]

  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[figwheel-sidecar "0.5.7"]
                                  [com.cemerick/piggieback "0.2.1"]
                                ;;  [binaryage/devtools "0.8.0"]
                                  ]
                   :plugins [[lein-figwheel "0.5.7"]
                             [lein-doo "0.1.7"]]}}
  
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "trivia.core/mount-root"}
                        :compiler {:main trivia.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}
                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :compiler {:output-to "resources/public/js/compiled/test.js"
                                   :main trivia.runner
                                   :optimizations :none}}]})
