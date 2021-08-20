(ns hyperpush.examples
  (:require [hyperpush.nn.network :as nn]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]))


(def input-layer (subs/make-2d-square-layer 2 1))
(def output-layer (subs/make-2d-square-layer 1 1))

(def connection-matrix (nn/create-network input-layer output-layer (utils/random-push)))

connection-matrix

(nn/feed-forward [[1] [1]] connection-matrix)

(defn run-xor-problem []
  ())