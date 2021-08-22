(ns hyperpush.nn.nn-test
  (:require [clojure.test :refer [deftest testing is]]
            [hyperpush.cppn.utils :refer [random-push]]
            [hyperpush.nn.substrate :refer [make-2d-square-layer get-width get-height make-1d-layer make-2d-substrate]]
            [hyperpush.nn.utils :refer [normalize-center-biased]]
            [hyperpush.nn.network :refer [feed-forward]]))

(deftest substrate-layer-2d-test
  (let [substrate (make-2d-square-layer 10 4)]
    (testing "width is correct"
      (is (= 10 (count substrate))))
    (testing "height is correct"
      (is (= 4 (count (first substrate))))))
  (testing "invalid substrate should return empty list"
    (is (empty? (make-2d-square-layer -1 -1)))
    (is (empty? (make-2d-square-layer 3 -1)))
    (is (empty? (make-2d-square-layer -1 3))))
  (testing "getter functions should return correct values"
    (is (= (get-height (make-2d-square-layer 4 10)) 10))
    (is (= (get-width (make-2d-square-layer 4 10)) 4)))
  (testing "vector conversion function is correct"
    (is (and (vector? (make-2d-square-layer 10 4)) (vector? (first (make-2d-square-layer 10 4)))))))

(deftest substrate-layer-1d-test
  (let [substrate (make-1d-layer 3)]
    (testing "width is correct"
      (is (= 3 (count substrate))))))

(deftest entire-substrate-2d-test
  (let [substrate (make-2d-substrate 3 1 2)]
    (testing "substrate creation works"
      (is (= substrate (list [0 0 0] [0 0] [0]))))))

(deftest normalization-test
  (testing "one node gets placed in the center"
    (is (= 0.5 (normalize-center-biased 0 1))))
  (testing "three nodes center gets place in the center"
    (is (= 0.5 (normalize-center-biased 1 3)))))