(ns hyperpush.examples
  (:require [hyperpush.nn.network :as nn]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]))

;==== XOR

(defn XOR
  "goal XOR function" 
  [in1 in2] (if (= in1 in2) 0 1))

(def xor-substrate (2 subs/make-2d-substrate 1 1))

xor-substrate

(defn random-xor-network []
  (let [push (utils/random-push)]
    (nn/connect-2d xor-substrate push)))

(def candidate-1 (random-xor-network))

(:a (apply assoc {} (interleave (list :a :b :c) (list 1 2 3))))

(defn evaluate-neural-network-xor
  "evaluates a neural network based on proxmity to XOR behavior, with a given input.
   Uses squared difference from expected output.
   returns error from 0 to 1"
  [network in1 in2]
  (let [expected-output (XOR in1 in2)
        actual-output (nn/feed-forward-2d network [in1 in2])]
    (Math/pow (apply - expected-output actual-output) 2)))

(nn/feed-forward-2d candidate-1 [0 1])

(evaluate-neural-network-xor candidate-1 0 1)
