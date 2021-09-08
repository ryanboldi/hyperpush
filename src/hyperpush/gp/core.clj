(ns hyperpush.gp.core
  (:require [propeller.genome :as genome]
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