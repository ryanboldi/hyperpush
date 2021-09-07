(ns hyperpush.gp.selection
  (:require [propeller.selection :refer [lexicase-selection]]
            [hyperpush.nn.network :as nn]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]))


(def population (repeatedly 10 #(utils/random-push)))

population

(defn network-rep [pop]
  (map #(nn/connect-2d xor-substrate %) pop))

(network-rep population)

;- features needed to be stored in population: push program + nn + fitness