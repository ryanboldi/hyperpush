(ns hyperpush.nn.network
  (:require [hyperpush.nn.substrate :refer [make-substrate-layer get-width get-height]]
            [hyperpush.cppn.utils :refer [random-push]]
            [hyperpush.cppn.core :as c]))

(defn empty-connection-matrix [layer-1 layer-2]
  (let [width-1 (get-width layer-1)
        height-1 (get-height layer-1)
        width-2 (get-width layer-2)
        height-2 (get-height layer-2)]
    (apply vector (repeat width-1 (apply vector (repeat height-1 (apply vector (repeat width-2 (apply vector (repeat height-2 0))))))))))

(def input-layer (make-substrate-layer 1 5))

(def output-layer (make-substrate-layer 1 1))

input-layer
output-layer

(defn create-network
  "creates a network from two substrate layers and a cppn for the weights"
  [input-layer output-layer cppn]
  (let [input-layer-width (get-width input-layer)
        input-layer-height (get-height input-layer)
        output-layer-width (get-width output-layer)
        output-layer-height (get-height output-layer)
        blank-slate (atom (empty-connection-matrix input-layer output-layer))]
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

(defn get-weight [connection-matrix x1 y1 x2 y2]
  (get-in connection-matrix [x1 y1 x2 y2]))

(defn feed-forward [inputs connection-matrix])

