(ns hyperpush.nn.substrate
  (:require [hyperpush.nn.utils :refer [convert-to-high-d-vector]]))

(defn make-2d-square-layer
  "creates a single substrate layer of size width x height"
  [width height]
  (convert-to-high-d-vector
   (if (or (<= width 0) (<= height 0))
     '()
     (repeatedly width #(repeat height 0)))))

(defn make-1d-layer
  [width]
  (vec (repeat width 0)))

(defn get-width
  "returns the width of substrate layer"
  [substrate-layer]
  (count substrate-layer))

(defn get-height
  "returns the height of a 2d substrate layer"
  [substrate-layer]
  (count (first substrate-layer)))

(defn make-2d-substrate
  "makes a list of 1d-layers, that represent an entire 2d substrate"
  [inputs & rest]
  (let [outputs (last rest)
        hiddens (butlast rest)]
  (->> hiddens
       (map #(make-1d-layer %))
       (concat (vector (make-1d-layer inputs)))
       (reverse)
       (concat (vector (make-1d-layer outputs)))
       (reverse))))