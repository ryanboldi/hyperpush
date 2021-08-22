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
    (m/emap-indexed 
     (fn [[c r] _] 
       (c/get-output cppn 
                     [(normalize-center-biased c layer-1-height)
                      layer-1-x-normalized
                     (normalize-center-biased r layer-2-height)
                      layer-2-x-normalized])) empty)))

(defn connect-2d "returns a list of connection matrices, each representing the transition from one layer to the next"
  [substrate])


(def input-layer (vector 1 2 3))
input-layer
(def output-layer (make-1d-layer 2))
output-layer

(def weights (m/transpose (connect-1d-layers input-layer output-layer 0 1 (random-push))))
weights

(m/mmul weights input-layer)

(comment

(defn empty-3d-connection-matrix
 "returns a nested vector filled with zeros that connects two 2D layers together"
  [layer-1 layer-2]
  (let [width-1 (get-width layer-1)
        height-1 (get-height layer-1)
        width-2 (get-width layer-2)
        height-2 (get-height layer-2)]
    (apply vector (repeat width-1 (apply vector (repeat height-1 (apply vector (repeat width-2 (apply vector (repeat height-2 0))))))))))

(defn create-3d-network
  "creates a network from two substrate layers and a cppn for the weights"
  [input-layer output-layer cppn]
  (let [input-layer-width (get-width input-layer)
        input-layer-height (get-height input-layer)
        output-layer-width (get-width output-layer)
        output-layer-height (get-height output-layer)
        blank-slate (atom (empty-3d-connection-matrix input-layer output-layer))]
    (dotimes [x1 input-layer-width]
      (dotimes [y1 input-layer-height]
        (dotimes [x2 output-layer-width]
          (dotimes [y2 output-layer-height]
            (let [x1-norm (float (/ x1 input-layer-width))
                  y1-norm (float (/ y1 input-layer-height))
                  x2-norm (float (/ x2 output-layer-width))
                  y2-norm (float (/ y2 output-layer-height))]
              (swap! blank-slate assoc-in [x1 y1 x2 y2] (c/get-output cppn [x1-norm y1-norm x2-norm y2-norm])))))))
    @blank-slate))

)

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