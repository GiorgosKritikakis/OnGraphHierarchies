import graphhierarchies.graph.DiGraph;
import graphhierarchies.graph.Reader;

import java.io.File;

/**
 * This class is a demo. Its purpose is to give a short comprehensive explanation of this project.
 * More precisely, this class shows how to read an edge list from a file or string.
 */
public class Demo1 {
    public static void main(String[] args) {
        /**
         * We can create digraphs using the class Reader. The class Reader provides the method ReadEdgeList,
         * which reads from a file or a string and returns a digraph. The file or string must have the proper
         * format. Each line must have one of the following forms:
         * 1)"x,y" where x,y are decimal integers. The line represents an edge from the vertex with id x to the vertex with id y.
         *   For example, the line "5,6" represents an edge from vertex 5 to vertex 6.
         * 2)"x" where x is a decimal integer that represent a single vertex.
         * 3)"" the empty line.
         */

        //Creating a graph from a java text block.
        String edgelist = """
                0,1
                1,2
                0,2
                3,1
                2,4
                2,5
                
                6
                """;
        DiGraph dag1 = Reader.ReadEdgeList(edgelist);

        //Creating a graph from a file.
        File f = new File("inputGraphs/graph1.csv");
        DiGraph dag2 = Reader.ReadEdgeList(f);
    }
}
