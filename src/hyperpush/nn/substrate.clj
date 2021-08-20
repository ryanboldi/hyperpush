(ns hyperpush.nn.substrate)

(defn convert-to-vector [substrate-layer]
  (mapv vec substrate-layer))

(defn make-2d-square-layer
  "creates a single substrate layer of size width x height"
  [width height]
  (convert-to-vector
   (if (or (<= width 0) (<= height 0))
    '()
    (repeatedly width #(repeat height 0)))))

(defn get-width 
  "returns the width of a square substrate layer"
  [substrate-layer]
  (count substrate-layer))

(defn get-height
  "returns the height of a square substrate layer"
  [substrate-layer]
  (count (first substrate-layer)))