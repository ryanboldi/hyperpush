(ns hyperpush.nn.nn-test
  (:require [clojure.test :refer [deftest testing is]]
            [hyperpush.nn.substrate :refer [make-substrate-layer get-width get-height]]))

(deftest substrate-test-on-creation
  (let [substrate (make-substrate-layer 10 4)]
    (testing "width is correct"
      (is (= 10 (count substrate))))
    (testing "height is correct"
      (is (= 4 (count (first substrate))))))
  (testing "invalid substrate should return empty list"
    (is (empty? (make-substrate-layer -1 -1)))
    (is (empty? (make-substrate-layer 3 -1)))
    (is (empty? (make-substrate-layer -1 3))))
  (testing "getter functions should return correct values"
    (is (= (get-height (make-substrate-layer 4 10)) 10))
    (is (= (get-width (make-substrate-layer 4 10)) 4))))