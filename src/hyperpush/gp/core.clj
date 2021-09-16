(ns hyperpush.gp.core
  (:require [propeller.genome :as genome]
            [propeller.variation :as v]
            [propeller.selection :as slc]
            [hyperpush.cppn.utils :as utils]
            [hyperpush.nn.network :as nn]))

(defn init-population "creates popsize # of random plushies in the correct format"
  [popsize]
  (map #(assoc {} :plushy %) (repeatedly popsize #(utils/random-plushy))))

(defn genotype-to-phenotype-2d
  "takes a population of plushies,  and adds in a :nn key representing
   the nn created by the cppn over the given substrate"
  [population substrate]
  (map #(assoc %1 :nn %2) population 
       (map #(nn/connect-2d substrate (genome/plushy->push (:plushy %))) population)))

(defn assign-fitness [pop-map func])

(defn crossover [parent1 parent2]
  (v/crossover (:plushy parent1) (:plushy parent2)))

(defn mutate 
  "mutates a parent with 10% addition, deletion and replacement prob"
  [parent1 umad-rate]
  (let [mutation-type (rand)]
  (if (< mutation-type 0.3) (v/uniform-replacement (:plushy parent1) utils/instructions umad-rate)
      (if (< mutation-type 0.7) (v/uniform-addition (:plushy parent1) utils/instructions umad-rate)
         (v/uniform-deletion (:plushy parent1) umad-rate)))))

(defn create-new-population "returns a list of plushies that make it to the next generation"
  [pop-map]
  (let [selection-pressure 0.3
        crossover-rate 0.3
        umad-rate 0.1
        popsize (count pop-map)
        num-parents (int (* selection-pressure popsize))
        parents (repeatedly num-parents #(slc/lexicase-selection pop-map :null))]
    (loop [children (repeatedly (int (* crossover-rate popsize)) #(crossover (rand-nth parents) (rand-nth parents)))]
      (if (<= popsize (count children))
        (map #(assoc {} :plushy %) children)
        (recur (conj children (mutate (rand-nth parents) umad-rate)))))))

(defn print-stats "prints the pop average fitness across all errors, and best individual on each case"
  [pop]
  (let [popsize (count pop)
        casesize (count (:errors (first pop)))]
  (->> pop
           (map #(:errors %))
           (apply concat)
           (apply +)
           (* (/ 1 (* popsize casesize)))
           (float)
           (str)
           (println "Population Average Error: "))
      (->> pop
           (map #(:errors %))
           (map #(apply + %))
           (map #(/ % casesize))
           (apply min)
           (println "Population Best Average Error: "))))

(defn get-best-member [pop]
  (let [popsize (count pop)
        casesize (count (:errors (first pop)))]
    (->> pop
         (map #(:errors %))
         (map #(apply + %))
         (map #(/ % casesize))
         (map-indexed vector)
         (apply min-key second)
         (first)
         (nth pop))))