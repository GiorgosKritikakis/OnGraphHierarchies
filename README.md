# On Graph Hierarchies
## Introduction
This project revolves around graph hierarchies and applications. It demystifies chain decomposition algorithms, introduces new techniques, deploys efficient sparsification and compaction, and provides transitive closure solutions, edge classification, and a hierarchical graph drawing framework to visualize graphs. In this work, you will find:
- Chain decomposition algorithms.
- Transitive closure solutions.
- A hierarchical graph drawing framework (It has not been incorporated into this repository yet.)

The document [Analysis and Visualization of Hierarchical Graphs](https://giorgoskritikakis.github.io/MSc_Thesis_uoc-submission.pdf) explicates these approaches. You can find the corresponding updated preprints on the following links:

1) [Parameterized Linear Time Transitive Closure](https://arxiv.org/pdf/2404.17954)
2) [Fast and Practical DAG Decomposition with Reachability Applications](https://arxiv.org/abs/2212.03945)
3) [Experiments and a User Study for Hierarchical Drawings of Graphs](https://arxiv.org/abs/2209.04522)


## Build
The project is a stand-alone Java program and has no external dependencies. Use Javac to build Demo classes and experiment with them.
## Description
In the folder "OnGraphHierarchies\src", you will find the project organized into four packages, the demo classes, and the "InputGraphs" folder, which contains sample input graphs. In the folder "OnGrpahHierarchies\JavaDoc" you will find the generated Javadoc.
#### The project is organized in four packages:
> +  **graphhierarchies.graph**
> Contains the directed graph representation and utility functions.
> + **graphhierarchies.chaindecomposition**
> Contains chain decomposition solutions. 
> E.g: Linear time Chain and Node Order Heuristic approaches, a fast concatenation technique, a fast and efficient chain decomposition algorithm, the Fulkerson method that calculates the width of the graph and produces a chain decomposition with minimum cardinality, and a linear time edge sparsification algorithm amongst with others.
>  + **graphhierarchies.transitiveclosure**
>  Transitive closure solutions, including an indexing scheme that enables us to answer queries in constant time, utilizing a compact data structure.
>  + **graphhierarchies.PBF**
>  (Not incorporated in this repository yet. See [PBF repo](https://github.com/GiorgosKritikakis/PathBasedFramework))
#### Demo classes
> The Demo classes provide a short comprehensive explanation of the project's functionality.
#### "OnGrpahHierarchies\src\InputGraphs" folder
> This folder contains some sample input graphs to help the user run the demos and understand the proper graph format the class Reader can read.

 
## Contact Info
Would you like to use this project or contribute to it? Do you have any suggestions or inquiries? You can contact me—I would be glad to hear from you!
[georgecretek@gmail.com](mailto:georgecretek@gmail.com)
