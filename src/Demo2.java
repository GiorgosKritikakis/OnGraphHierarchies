import graphhierarchies.chaindecomposition.Chain;
import graphhierarchies.chaindecomposition.ChainDecomposition;
import graphhierarchies.graph.DiGraph;
import graphhierarchies.graph.GraphUtil;
import graphhierarchies.graph.Reader;
import graphhierarchies.graph.Vertex;
import graphhierarchies.transitiveclosure.IndexingScheme;
import graphhierarchies.transitiveclosure.TransitiveClosure;

import java.io.File;
import java.util.LinkedList;

/**
 * This class is a demo. Its purpose is to give a short comprehensive explanation of this project.
 * More precisely, this class investigates some useful classes and their behaviors.
 */
public class Demo2 {
    public static void main(String[] args) {
        File f = new File("inputGraphs/graph1.csv");
        DiGraph dag = Reader.ReadEdgeList(f); //reads the input file and creates a digraph

        //The class GraphUtil contains some helpful functionality.
        if (GraphUtil.IsDirectedAcyclicGraph(dag) ) {                 //Check if the directed graph is acyclic.
            Vertex[]TopOrder = GraphUtil.setTopologicalRank(dag);     //Calculates a topological sorting.
            GraphUtil.sortAdjLists(TopOrder);                         //Sorts adjacency lists. The lists of immediate successors in
                                                                      //ascending order and the lists of immediate predecessors in
                                                                      //descending order

            /*
             * The class ChainDecomposition provides chain decomposition solutions, heuristics, and the Fulkerson method.
             * Fulkerson's method computes a chain decomposition with the minimum cardinality, which equals the width.
             */

            //FastChainDecomposition that runs in O(|E|+c*l)
            LinkedList<Chain> decomposition_fcd = ChainDecomposition.FastChainDecomposition(TopOrder);
            /*
             * Additional chain decomposition methods:
             *  LinkedList<Chain> decomposition_no = ChainDecomposition.COH(TopOrder);  Chain Order Heuristic that runs in linear time
             *  LinkedList<Chain> decomposition_co = ChainDecomposition.NOH(TopOrder);  Node Order Heuristic that runs in linear time
             *  LinkedList<Chain> decomposition_nov = ChainDecomposition.NOH_variation(TopOrder); Node Order Heuristic variation that runs in linear time
             *  ChainDecomposition.ChainConcatenation(decomposition_no,TopOrder);  Chain concatenation method. Runs in (|E|+c*l)
             *  LinkedList<Chain> decomposition_fulkerson = ChainDecomposition.optChainDecomposition(TopOrder);  Fulkerson method
             */



            //Transitive closure solutions implement TransitiveClosure interface.
            TransitiveClosure tc = new IndexingScheme(decomposition_fcd, TopOrder);
            //TransitiveClosure tc = new OnlineDFS();
            //TransitiveClosure tc = new new AdjMatrix(dag);

            Vertex source = dag.getVertex(0);
            Vertex target = dag.getVertex(5);
            if(tc.isReachable(source,target)){
                System.out.println("Vertex "+source.getID()+" can reach vertex "+target.getID());
            }else{
                System.out.println("Vertex "+source.getID()+" cannot reach vertex "+target.getID());
            }
        }else{
            System.out.println(f.getName()+" is not a DAG.");
        }
    }
}
