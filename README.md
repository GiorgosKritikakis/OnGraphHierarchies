# On Graph Hierarchies
## Introduction
This project revolves around graph hierarchies and applications. It demystifies chain decomposition algorithms, deploying efficient sparsification and compaction, provides transitive closure solutions, edges classification, and a hierarchical graph drawing framework to visualize graphs. In this work, you will find:
- Chain decomposition algorithms
- Transitive closure sollutions
- A hierarchical graph drawing framework (Not incorporated in this repository yet. See [PBF repo](https://github.com/GiorgosKritikakis/PathBasedFramework)).

The document [Analysis and Visualization of Hierarchical Graphs](https://giorgoskritikakis.github.io/MSc_Thesis_uoc-submission.pdf) explicates these approaches. You can find the corresponding updated preprints on the following links:

1) [Fast and Practical DAG Decomposition with Reachability Applications](https://arxiv.org/abs/2212.03945)
2) [Experiments and a User Study for Hierarchical Drawings of Graphs](https://arxiv.org/abs/2209.04522)

## Build
The project is a stand-alone java program and has not any external dependencies. Use javac to build Demo classes and experiment with them.
## Description
In the folder "OnGrpahHierarchies\src" you will find the project organized into four packages, the demo classes, and the "InputGraphs" folder, which contains sample input graphs. In the folder "OnGrpahHierarchies\JavaDoc" you will find the generated javadoc.
#### The project is organised in four packages:
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
Do you want to use this project or contribute? Do you have any suggestions or inquiries? you can contact me! I would be glad to know!
[georgecretek@gmail.com](mailto:georgecretek@gmail.com)
