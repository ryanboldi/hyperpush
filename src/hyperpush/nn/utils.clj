(ns hyperpush.nn.utils)

(defn convert-to-high-d-vector [substrate-layer]
  (mapv vec substrate-layer))