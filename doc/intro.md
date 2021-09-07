# Introduction to hyperpush

TODO: write [great documentation](http://jacobian.org/writing/what-to-write/)


# Source Code File Contents
## CPPN Folder
This is where files associated with the genotype (a Compositional Pattern Producing Network) reside.
### These files include:
- core.clj 
- instructions.clj - a file that contains instructions that were not included in the original propeller library
- utils.clj

## NN Folder
Neural Networks (Phenotype)
- substrate.clj - The shape of the neural networks
- network.clj - fully connected subtrates
- utils.clj

## GP Folder
Top level genetic programming modules, such as selection and reproduction can be found here.

- selction.clj - handles evaluating and selecting individuals