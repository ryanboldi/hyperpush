(ns hyperpush.nn.substrate)

(defn make-substrate-layer
  "creates a single substrate layer of size width x height"
  [width height]
  (if (or (<= width 0) (<= height 0))
    '()
    (repeatedly width #(repeat height 0))))



