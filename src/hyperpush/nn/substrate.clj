(ns hyperpush.nn.substrate)

(defn make-substrate-layer
  "creates a single substrate layer of size width x height"
  [width height]
  (repeatedly width #(repeat height 0)))

(make-substrate-layer 10 3)