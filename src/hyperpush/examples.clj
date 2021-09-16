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

xor-substrate

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

(gp/init-population 10)
(gp/create-new-population pop-map)

(def new-pop (assign-fitness-xor (gp/genotype-to-phenotype-2d (gp/create-new-population pop-map) xor-substrate)))

new-pop

(def new-pop (-> pop-map
                 gp/create-new-population
                 (gp/genotype-to-phenotype-2d xor-substrate)
                 assign-fitness-xor))

(def newer-pop (assign-fitness-xor (gp/genotype-to-phenotype-2d (gp/create-new-population new-pop) xor-substrate)))


(gp/print-stats pop-map)
(gp/print-stats newer-pop)


(defn -main []
  (println "RUNNING XOR EVOLUTION")
  (let [generations 5000
        goal-error 0.1
        popsize 10]
    (loop [population (-> popsize
                          gp/init-population
                          (gp/genotype-to-phenotype-2d xor-substrate)
                          assign-fitness-xor)
           gen-num 0]
      (if (>= gen-num generations)
        (do (gp/print-stats population) (gp/get-best-member population))
            (recur (-> population
                       gp/create-new-population
                       (gp/genotype-to-phenotype-2d xor-substrate)
                       assign-fitness-xor) (inc gen-num))))))

(-main)