(ns hyperpush.nn.utils)

(defn convert-to-high-d-vector [substrate-layer]
  (mapv vec substrate-layer))

(defn normalize-center-biased 
  "normalizes the index of a node from 0 to 1, given the num of nodes in the layer, center biased"
  [index num]
  (let [gap (/ 1 (inc num))
        pos (* gap (inc index))]
    (float pos)))