(ns hyperpush.gp.selection
  (:require [propeller.selection :refer [lexicase-selection]]
            [hyperpush.nn.network :as nn]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]))


;- features needed to be stored in population: push program + nn + fitness