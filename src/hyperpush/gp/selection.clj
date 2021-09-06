(ns hyperpush.gp.selection
  (:require [propeller.selection :refer [lexicase-selection]]
            [hyperpush.nn.network :as nn]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]))



(def xor-substrate (subs/make-2d-substrate 2 1 1))

xor-substrate

(defn random-xor-network []
  (let [push (utils/random-push)]
    (nn/connect-2d xor-substrate (utils/random-push))))

(:a (apply assoc {} (interleave (list :a :b :c) (list 1 2 3))))

(def population (repeatedly 10 #(utils/random-push)))

population

(defn network-rep [pop]
  (map #(nn/connect-2d xor-substrate %) pop))

(defn get-fitness [pop])

(network-rep population)



(nn/feed-forward-2d xor-network [0 1])

;- features needed to be stored in population: push program + nn + fitness