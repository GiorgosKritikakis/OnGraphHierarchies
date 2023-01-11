import graphhierarchies.chaindecomposition.Chain;
import graphhierarchies.chaindecomposition.ChainDecomposition;
import graphhierarchies.graph.*;
import graphhierarchies.transitiveclosure.IndexingScheme;

import java.io.File;
import java.util.LinkedList;

public class Demo3 {
    public static void listFilesFromFolder(final File folder, LinkedList<File> files) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesFromFolder(fileEntry,files);
            } else {
                files.add(fileEntry);
            }
        }
    }
    public static void main(String[] args) {
        final File folder = new File("InputGraphs");
        LinkedList<File> files = new LinkedList<>();
        Main.listFilesFromFolder(folder,files);
        for(File f : files) {
            DiGraph dag = Reader.ReadEdgeList(f);
            if(!GraphUtil.IsDirectedAcyclicGraph(dag)){
                System.out.println(f.getName()+" is not acyclic");
                continue;
            }
            if (GraphUtil.IsDirectedAcyclicGraph(dag)) {

                Vertex[] TopOrder = GraphUtil.setTopologicalRank(dag);
                GraphUtil.sortAdjLists(TopOrder);

                /**Uncomment the next two lines to perform sparsification*/
                //LinkedList<Chain> decomposition_co = ChainDecomposition.COH(TopOrder);  //build a chain decomposition to utilize it for sparsification.
                //ChainDecomposition.sparsify(decomposition_co,TopOrder);                 //sparsifies the graph utilizing a chain decomposition in linear time.

                System.out.print("Graph: "+f.getName()+" nodes: "+dag.verticesSize()+" Edges:"+dag.getEdgeListRepr().size());

                LinkedList<Chain> decomposition_fcd = ChainDecomposition.FastChainDecomposition(TopOrder);
                System.out.print(" chains: "+decomposition_fcd.size());

                LinkedList<Chain> decomposition_fulk = ChainDecomposition.optChainDecomposition(TopOrder);
                System.out.print(" width: "+decomposition_fulk.size());

                LinkedList<Edge> Etr = new LinkedList<>(); //In this list we add the transitive edges.
                IndexingScheme tr = new IndexingScheme(decomposition_fcd,TopOrder,Etr); //we calculate the indexing scheme and we detect all transitive edges.
                System.out.println(" Etr: "+Etr.size());
            }
        }
    }
}
