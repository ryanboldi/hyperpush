(ns hyperpush.nn.substrate)

(defn make-substrate-layer
  "creates a single substrate layer of size width x height"
  [width height]
  (if (or (<= width 0) (<= height 0))
    '()
    (repeatedly width #(repeat height 0))))

(defn get-width 
  "returns the width of a substrate layer"
  [substrate-layer]
  (count substrate-layer))

(defn get-height
  "returns the height of a substrate layer"
  [substrate-layer]
  (count (first substrate-layer)))

(defn empty-connection-matrix [layer-1 layer-2]
  (let [width-1 (get-width layer-1)
        height-1 (get-height layer-1)
        width-2 (get-width layer-2)
        height-2 (get-height layer-2)]
    (repeat width-1 (repeat height-1 (repeat width-2 (repeat height-2 0))))))

(empty-connection-matrix 1 1 )