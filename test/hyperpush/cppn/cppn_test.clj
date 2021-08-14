(ns hyperpush.cppn.cppn-test
  (:require 
   [clojure.test :refer [deftest testing is]]
   [hyperpush.cppn.core :refer [get-input-map]]))

(deftest get-input-map-test
  (testing "input-map should be correct"
    (is (= (get-input-map [0.1 0.2 0.4]) {:in1 0.1 :in2 0.2 :in3 0.4}))))