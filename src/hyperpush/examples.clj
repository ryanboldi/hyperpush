(ns hyperpush.examples
  (:require [hyperpush.nn.network :as nn]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]))

;==== XOR

(def xor-substrate (subs/make-2d-substrate 2 1 3 3 3 3 3 3 3 3 3 3 3 3))

xor-substrate

(def xor-network (nn/connect-2d xor-substrate (utils/random-push)))

xor-network
(def xor-good)

(nn/feed-forward-2d xor-network [0 1])