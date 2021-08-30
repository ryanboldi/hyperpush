(ns hyperpush.nn.network
  (:require [clojure.core.matrix :as m]
            [hyperpush.nn.substrate :refer [make-2d-square-layer get-width get-height make-1d-layer]]
            [hyperpush.nn.utils :refer [convert-to-high-d-vector normalize-center-biased]]
            [hyperpush.cppn.utils :refer [random-push]]
            [hyperpush.cppn.core :as c]))

(defn connect-1d-layers
 "outputs a w x h matrix where w is the height of layer 1 and h is the height of layer-2"
  [layer-1 layer-2 layer-1-x-normalized layer-2-x-normalized cppn]
  (let [layer-1-height (count layer-1)
        layer-2-height (count layer-2)
        empty (m/zero-matrix layer-1-height layer-2-height)]
    (-> (m/emap-indexed 
     (fn [[c r] _] 
       (c/get-output cppn 
                     [(normalize-center-biased c layer-1-height)
                      layer-1-x-normalized
                     (normalize-center-biased r layer-2-height)
                      layer-2-x-normalized])) empty)
        (m/transpose))))

(defn feed-forward-1d 
  "feeds forward from one 1D layer to the next"
  [inputs connection-matrix]
  (->> (m/mmul connection-matrix inputs)
       (m/emap #(m/tanh %))))

(defn connect-2d "returns a list of connection matrices, each representing the transition from one layer to the next"
  [substrate])

(defn get-weight
 "returns the weight from (x1, y1) to (x2, y2) in a given connection-matrix"
  [connection-matrix x1 y1 x2 y2]
  (if (list? connection-matrix)
    (-> connection-matrix (nth x1) (nth y1) (nth x2) (nth y2))
    (get-in connection-matrix [x1 y1 x2 y2])))

(defn feed-forward
  "feeds a 2d layer of inputs through a connection matrix to create a 2d layer of outputs"
  [input connection-matrix]
  (let [input-shape (-> connection-matrix)
        input-width (count input-shape)
        input-height (count (first input-shape))]
    (assert (and
             (= input-height (get-height input))
             (= input-width (get-width input)))
            "input shape should match the connection matrix's shape")
    (let [output-shape (-> connection-matrix (nth 0) (nth 0))
          output-width (count output-shape)
          output-height (count (nth output-shape 0))]
      (->> (for [x (range output-width)]
        (for [y (range output-height)]
          (->> (for [i (range input-width)]
                 (for [j (range input-height)]
                   (* (get-weight connection-matrix i j x y) (get-in input [i j]))))
               (map #(apply + %))
               (apply +))))
      (convert-to-high-d-vector)))))