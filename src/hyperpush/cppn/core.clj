(ns hyperpush.cppn.core
  (:require 
   [hyperpush.cppn.utils :as utils]
   [propeller.genome :as genome]
    [propeller.push.interpreter :as interpreter]
    [propeller.push.state :as state]))

(defn get-input-map
  "returns map of key to input value like {:in# %# ...}"
  [inputs]
  (->> inputs
       (map-indexed (fn [idx itm] (hash-map (keyword (str "in" (inc idx))) itm)))
       (into {})))

(defn get-output
  "given a push program and a list of inputs, return the value at the top of float stack"
  [push & inputs]
  (let [output (state/peek-stack
                (interpreter/interpret-program
                 push
                 (assoc state/empty-state :input (get-input-map inputs))
                 100)
                :float)
        float-output (if (float? output) output 0)]
        float-output))

(get-output (genome/plushy->push (utils/random-plushy)) [0.1 0.3 0.4 0.5])