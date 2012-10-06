(defproject com.fireworksproject/cloudfront "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [noir "1.3.0-beta3"]
                           [enlive "1.0.1"]
                           [cheshire "4.0.3"]
                           
                           [enfocus "1.0.0-beta1"]

                           [com.fireworksproject/cloudmill "0.1.0-SNAPSHOT"]
                           [bultitude "0.1.7"]]
            :plugins [[lein-cljsbuild "0.2.7"]]
            :cljsbuild {:builds [{:source-path "cljs"
                                  :compiler {:output-to "resources/public/js/main.js"
                                             :optimizations :whitespace
                                             :pretty-print true}}]}
            :hooks [leiningen.cljsbuild]
            :main cloudfront.server)

