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

(comment

(deftest connection-matrix-tests
  (testing "network creation respects shape of input and output layer"
    (let [input-layer (make-2d-square-layer 1 5)
          output-layer (make-2d-square-layer 2 3)
          connection-matrix (create-3d-network input-layer output-layer (random-push))]
      (is (= (get-width input-layer) (count connection-matrix)))
      (is (= (get-height input-layer) (count (first connection-matrix))))
      (is (= (get-width output-layer) (count (-> connection-matrix (first) (first))))) 
      (is (= (get-height output-layer) (count (-> connection-matrix (first) (first) (first))))))))

(deftest feedforward-tests
    (let [input-layer (make-2d-square-layer 1 3)
          output-layer (make-2d-square-layer 2 2)
          connection-matrix (create-3d-network input-layer output-layer (random-push))]
      (testing "incorrect input shape throws assertion error"
        (is (thrown? AssertionError (feed-forward [[1]] connection-matrix))))
      (testing "feedforward output shape matches network output shape"
        (let [output (feed-forward input-layer connection-matrix)]
          (is (= (get-width output-layer) (get-width output)))
          (is (= (get-height output-layer) (get-height output)))))))

)