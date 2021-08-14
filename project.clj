(defproject hyperpush "0.1.0-SNAPSHOT"
  :description "Using PushGP CPPNs to indirectly encode Neural Networks"
  :url "https://github.com/ryanboldi/hyperpush"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [net.clojars.lspector/propeller "0.2.3"]]
  :main ^:skip-aot hyperpush.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
