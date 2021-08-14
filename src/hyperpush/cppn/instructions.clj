(ns hyperpush.cppn.instructions
  (:require [propeller.push.utils.macros :refer [def-instruction]]
            [propeller.push.utils.helpers :refer [make-instruction]]
            [propeller.tools.math :as math]))

(defn gauss [x]
  (-> x
      (math/pow 2.0)
      (* -1)
      (math/exp)))

(def-instruction
  :float_gauss
  ^{:stacks #{:float}}
  (fn [state]
    (make-instruction state gauss [:float] :float)))

(defn mod-point-five [x]
  (-> x
      (mod 0.5)))

(def-instruction
  :float_mod1
  ^{:stacks #{:float}}
(fn [state]
  (make-instruction state mod-point-five [:float] :float)))

(defn sigmoid [x]
  (->> x
      (* -1)
      (math/exp)
      (+ 1)
      (/ 1)))

(def-instruction
  :float_sigmoid
  ^{:stacks #{:float}}
(fn [state]
  (make-instruction state sigmoid [:float] :float)))