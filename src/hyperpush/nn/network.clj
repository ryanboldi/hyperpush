(ns hyperpush.nn.network
  (:require [hyperpush.nn.substrate :refer [make-substrate-layer]]))

(def input-layer (make-substrate-layer 1 5))

(def output-layer (make-substrate-layer 1 1))

(defn create-network 
  "creates a network from two substrate layers and a cppn for the weights"
  [input-layer output-layer cppn]
  )