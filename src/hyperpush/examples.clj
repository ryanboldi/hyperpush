(ns hyperpush.examples
  (:require [hyperpush.nn.network :as nn]
            [propeller.selection :as slc]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]
            [hyperpush.gp.core :as gp]))

;==== XOR

(defn XOR
  "goal XOR function" 
  [in1 in2] (if (= in1 in2) 0 1))

(def xor-substrate (subs/make-2d-substrate 2 1 1))

(defn random-xor-network []
  (let [push (utils/random-push)]
    (nn/connect-2d xor-substrate push)))

(defn evaluate-neural-network-xor
  "evaluates a neural network based on proxmity to XOR behavior, with a given input.
   Uses absolute difference from expected output.
   returns error from 0 to 1"
  [network [in1 in2]]
  (let [expected-output (XOR in1 in2)
        actual-output (nn/feed-forward-2d network [in1 in2])]
    (Math/abs (apply - expected-output actual-output))))

(defn assign-fitness-xor [population]
    (map #(assoc %1 :errors %2) population
           (map #(for [x (range 2)
                        y (range 2)]
                    (apply (fn [x y] (evaluate-neural-network-xor (:nn %) [x y])) [x y])) 
                population)))

(def pop-map (assign-fitness-xor (gp/genotype-to-phenotype-2d (gp/init-population 10) xor-substrate)))

(count pop-map)

(count (gp/create-new-population pop-map))
