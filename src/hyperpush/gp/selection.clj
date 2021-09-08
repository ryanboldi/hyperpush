(ns hyperpush.gp.selection
  (:require [propeller.selection :refer [lexicase-selection]]
            [hyperpush.nn.network :as nn]
            [hyperpush.nn.substrate :as subs]
            [hyperpush.cppn.core :as cppn]
            [hyperpush.cppn.utils :as utils]))


;propeller pop map needs
;
; :errors
;
;
;

(def pop-member {:plushy 3 :nn '(1 2 3) :errors [0 1 2]})

(def population (repeat 10 pop-member))

(map rand-nth (vals (group-by :errors population)))

(lexicase-selection population {})