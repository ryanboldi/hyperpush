(ns hyperpush.nn.network
  (:require [clojure.core.matrix :as m]
            [hyperpush.cppn.instructions]
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

(defn feed-forward-1d-layer 
  "feeds forward from one 1D layer to the next"
  [inputs connection-matrix]
  (->> (m/mmul connection-matrix inputs)
       (m/emap #(m/tanh %))))

(defn connect-2d "returns a list of connection matrices, each representing the transition from one layer to the next"
  [substrate cppn]
  (let [num-layers (count substrate)
        layer-sep (float (/ 1 num-layers))
        layer-x-values (partition 2 1 (map #(* layer-sep %) (range num-layers)))
        seperated-substrate (partition 2 1 substrate)]
    (map #(connect-1d-layers (first %1) (second %1) (first %2) (second %2) cppn) seperated-substrate layer-x-values)))

(defn feed-forward-2d
  "feeds forward through a list of connection matrices"
  [list-of-connection-matrices inputs]
  (let [layers (count list-of-connection-matrices)]
  (loop [index 0 current inputs]
    (if (>= index layers)
      current
      (recur (inc index) (map #(hyperpush.cppn.instructions/sigmoid %) (feed-forward-1d-layer current (nth list-of-connection-matrices index))))))))

(defn get-weight
 "returns the weight from (x1, y1) to (x2, y2) in a given connection-matrix"
  [connection-matrix x1 y1 x2 y2]
  (if (list? connection-matrix)
    (-> connection-matrix (nth x1) (nth y1) (nth x2) (nth y2))
    (get-in connection-matrix [x1 y1 x2 y2])))