(ns hyperpush.nn.nn-test
  (:require [clojure.test :refer [deftest testing is]]
            [hyperpush.nn.substrate :refer [make-substrate-layer]]))

(deftest substrate-width-height-test-on-creation
  (let [substrate (make-substrate-layer 10 4)]
    (testing "width is correct"
      (is (= 10 (count substrate))))
    (testing "height is correct"
      (is (= 4 (count (first substrate))))))
  (testing "invalid substrate should return empty list"
    (is (empty? (make-substrate-layer -1 -1)))
    (is (empty? (make-substrate-layer 3 -1)))
    (is (empty? (make-substrate-layer -1 3)))))