(ns hyperpush.cppn.core
  (:require [propeller.genome :as genome]
            [propeller.push.interpreter :as interpreter]
            [propeller.push.state :as state]))

(defn get-input-map
  "returns map of key to input value like {:in# %# ...}"
  [inputs]
  (->> inputs
       (map-indexed (fn [idx itm] (hash-map (keyword (str "in" (inc idx))) itm)))
       (into {})))

(get-input-map [0.1 1.3 4.1 4.0])

;(defn get-output
 ;"given a push program and a list of inputs, return the value at the top of float stack"
 ; [push & inputs]
 ; (let [output (state/peek-stack
  ;              (interpreter/interpret-program
   ;              push
   ;              (assoc state/empty-state :input )))]))