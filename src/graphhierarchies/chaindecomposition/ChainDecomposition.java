package graphhierarchies.chaindecomposition;

import graphhierarchies.graph.Vertex;
import graphhierarchies.transitiveclosure.OnlineDFS;
import graphhierarchies.transitiveclosure.TransitiveClosure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

/**
 * This class contains static methods that perform path/chain decomposition and path/chain concatenations.
 */
public class ChainDecomposition {
    /**
     * Starts from the root vertex and searches, tracing the edges backward, for a vertex that
     * is the last vertex of a chain. If it does not find such a vertex it returns null. Otherwise,
     * it returns the path between the two vertices.
     *
     * @param root the starting vertex of the search
     * @param iterators indicate the unexplored adjacent vertices
     * @param isVisited indicates if a vertex is visited
     * @param VertexToChain indicates the chain a vertex belongs in
     * @return null if the search fails or the path between the vertices if the search succeeds
     */
    private static Stack<Vertex> reversedDFSlookup(Vertex root,ListIterator<Vertex>[] iterators/*,boolean []isDeleted*/,boolean []isVisited,Chain[] VertexToChain){//boolean []isLast){
    Stack<Vertex> stack = new Stack<Vertex>();
    // System.out.println("reversedDFSlookup starts from "+root.getID() );
    stack.push(root);
    //isVisited[root.getTopolRank()] = true;
    while (!stack.isEmpty()) {
        Vertex current = stack.peek();
        ListIterator<Vertex> it = iterators[current.getTopolRank()];
        while (true) {
            if (it.hasNext()) {
                Vertex adjSource = it.next();
                if (!isVisited[adjSource.getTopolRank()]) {
                    Vertex lastVertex = VertexToChain[adjSource.getTopolRank()].getLastVertex();
                    if(adjSource == lastVertex){
                        for(Vertex v: stack){
                            isVisited[v.getTopolRank()] = false;
                            iterators[v.getTopolRank()].previous();
                        }
                        stack.push(adjSource);
                        return stack;
                    }
                    stack.push(adjSource);
                    isVisited[adjSource.getTopolRank()] = true;
                    break;
                }
            }else{
                stack.pop();
                //isDeleted[current.getTopolRank()] = true;
                break;
            }
        }
    }
    return null;
}


    /**
     * Returns a path decomposition of the graph. It is the implementation of the Chain Order Heuristic
     * and runs in linear time.
     *
     * @param topolSorting a topological sorting of the graph
     * @return a path decomposition of the graph
     */
    public static LinkedList<Chain> COH(Vertex[] topolSorting){
        Chain[] VertexToChain = new Chain[topolSorting.length];
        LinkedList<Chain> decomposition = new LinkedList<Chain>();
        boolean[] isVisited = new boolean[topolSorting.length];

        for(int i=0;i<topolSorting.length;++i){
            Vertex v = topolSorting[i];
            if(!isVisited[v.getTopolRank()]){
                Chain C = new Chain();
                C.addTop(v);
                decomposition.add(C);
                isVisited[v.getTopolRank()] = true;
                boolean newChain = false;
                while(!newChain){
                    newChain = true;
                    for(Vertex t:v.getAdjTargets()){
                        if(!isVisited[t.getTopolRank()]){
                            C.addTop(t);
                            isVisited[t.getTopolRank()] = true;
                            v = t;
                            newChain = false;
                            break;
                        }
                    }
                }
            }
        }
        return  decomposition;
    }

    /**
     * Returns a path decomposition of the graph. It is the implementation of the Node Order Heuristic
     * and runs in linear time.
     *
     * @param topolSorting a topological sorting of the graph
     * @return a path decomposition of the graph
     */
    public static LinkedList<Chain> NOH(Vertex []topolSorting){
        Chain[] VertexToChain = new Chain[topolSorting.length];
        LinkedList<Chain> decomposition = new LinkedList<Chain>();
        for(Vertex v:topolSorting){
            boolean inserted = false;
            for(Vertex adjSource:v.getAdjSources()) {
                Chain C = VertexToChain[adjSource.getTopolRank()];
                if(C.getVertices().getLast()==adjSource) {
                    C.addTop(v);
                    VertexToChain[v.getTopolRank()] = C;
                    inserted = true;
                    break;
                }
            }
            if(!inserted){
                Chain C = new Chain();
                C.addTop(v);
                VertexToChain[v.getTopolRank()] = C;
                decomposition.add(C);
            }
        }
        return decomposition;
    }

    /**
     * Returns a path decomposition of the graph. It is a variation of Node Order Heuristic
     * and runs in linear time.
     *
     * @param topolSorting a topological sorting of the graph
     * @return a path decomposition of the graph
     */
    public static LinkedList<Chain> NOH_variation(Vertex[] topolSorting){
        Chain[] VertexToChain = new Chain[topolSorting.length];
        LinkedList<Chain> decomposition = new LinkedList<Chain>();
        for(Vertex v:topolSorting){
            int min_outdegree = topolSorting.length;
            Vertex toAdd = null;
            boolean belongToChain = false;
            if( VertexToChain[v.getTopolRank()] != null ){
                belongToChain = true;
            }else{
                for(Vertex adjSource:v.getAdjSources()) {  //choose the immediate predecessor with the lowest outdegree
                    int adjTargetRank = adjSource.getTopolRank();
                    Chain C = VertexToChain[adjTargetRank];
                    if (C.getVertices().getLast() == adjSource) {
                        int adjOutdegree = adjSource.getAdjTargets().size();
                        if (adjOutdegree < min_outdegree) {
                            min_outdegree = adjOutdegree;
                            toAdd = adjSource;
                        }
                    }
                }
            }
            if(toAdd != null){
                Chain C = VertexToChain[toAdd.getTopolRank()];
                C.addTop(v);
                VertexToChain[v.getTopolRank()] = C;
            }else if(!belongToChain){
                Chain C = new Chain();
                C.addTop(v);
                VertexToChain[v.getTopolRank()] = C;
                decomposition.add(C);
            }

            for(Vertex t: v.getAdjTargets()){
                int t_topolRank = t.getTopolRank();
                if(t.getAdjSources().size()==1){
                    Chain C = VertexToChain[v.getTopolRank()];
                    C.addTop(t);
                    VertexToChain[t.getTopolRank()] = C;
                    break;
                }
            }

        }
        return decomposition;
    }

    /**
     * Returns a chain decomposition of the graph. It runs in O(|E|+c*l).
     *
     * @param topolSorting a topological sorting of the graph
     * @return a chain decomposition of the graph
     */
    public static LinkedList<Chain> FastChainDecomposition(Vertex[] topolSorting){
        Chain[] VertexToChain = new Chain[topolSorting.length];
        LinkedList<Chain> decomposition = new LinkedList<Chain>();

      //  boolean[] isLast = new boolean[dag.verticesSize()];
      //  boolean[] isDeleted = new boolean[topolSorting.length];
        boolean[] isVisited = new boolean[topolSorting.length];
        ListIterator<Vertex> []iterators = new ListIterator[topolSorting.length];
//        for(Vertex v:topolSorting){
//            isLast[v.getTopolRank()] = null;
//            isDeleted[v.getTopolRank()] = false;
//            isVisited[v.getTopolRank()] = false;
//            iterators[v.getTopolRank()] = v.getAdjSources().listIterator();
//        }

        for(Vertex v:topolSorting){
            iterators[v.getTopolRank()] = v.getAdjSources().listIterator();
            int min_outdegree = topolSorting.length;
            Vertex toAdd = null;
            boolean belongToChain = false;
            if( VertexToChain[v.getTopolRank()] != null ){
                belongToChain = true;
            }else{
                for(Vertex adjSource:v.getAdjSources()) {  //choose the immediate predecessor with the lowest outdegree
                    int adjTargetRank = adjSource.getTopolRank();
                    Chain C = VertexToChain[adjTargetRank];
                    if (C.getVertices().getLast() == adjSource) {
                        int adjOutdegree = adjSource.getAdjTargets().size();
                        if (adjOutdegree < min_outdegree) {
                            min_outdegree = adjOutdegree;
                            toAdd = adjSource;
                        }
                    }
                }
                if(toAdd==null){
                    Stack<Vertex> path = reversedDFSlookup(v,iterators,isVisited,VertexToChain);//isLast);
                    if(path!=null){
                        toAdd = path.peek();
                    }
                }
            }
            if(toAdd != null){
                Chain C = VertexToChain[toAdd.getTopolRank()];
                C.addTop(v);
                VertexToChain[v.getTopolRank()] = C;
//                isLast[toAdd.getTopolRank()] = false;
//                isLast[v.getTopolRank()] = true;
            }else if(!belongToChain){
                Chain C = new Chain();
                C.addTop(v);
                VertexToChain[v.getTopolRank()] = C;
                decomposition.add(C);
               // isLast[v.getTopolRank()] = true;
            }

            for(Vertex t: v.getAdjTargets()){
                int t_topolRank = t.getTopolRank();
                if(t.getAdjSources().size()==1){
                    Chain C = VertexToChain[v.getTopolRank()];
                    C.addTop(t);
                    VertexToChain[t.getTopolRank()] = C;
//                    isLast[v.getTopolRank()] = false;
//                    isLast[t.getTopolRank()] = true;
                    break;
                }
            }

        }
        return decomposition;
    }

    /**
     *It performs path and chain concatenations of the given decomposition. It runs in O(|E|+c*l) time.
     *
     * @param decomposition a path/chain decomposition of the graph
     * @param topolSorting a topological sorting of the graph
     */
    public static void ChainConcatenation(LinkedList<Chain> decomposition,Vertex[] topolSorting){
        //boolean isDeleted[] = new boolean[topolSorting.length];
        boolean isVisited[] = new boolean[topolSorting.length];
        ListIterator<Vertex> []iterators = new ListIterator[topolSorting.length];
        Chain[] VertexToChain = new Chain[topolSorting.length];
        for(int i=0;i<topolSorting.length;++i){
            iterators[i] = topolSorting[i].getAdjSources().listIterator();
        }
        for (Chain C:decomposition){
            for(Vertex v:C.getVertices()){
                VertexToChain[v.getTopolRank()] = C;
            }
        }
        ListIterator<Chain> c_it = decomposition.listIterator();
        while (c_it.hasNext()){
            Chain C = c_it.next();
            Vertex root = C.getVertices().getFirst();
            Stack<Vertex> stack = reversedDFSlookup(root,iterators,isVisited,VertexToChain);
            if(stack!=null){
                Chain tmp = VertexToChain[stack.peek().getTopolRank()];
                tmp.getVertices().addAll(C.getVertices());
                c_it.remove();
            }
        }

    }

    /**
     * Returns a chain decomposition with minimum cardinality using Fulkerson Method.
     *
     * @param topolSorting a topological sorting of the graph
     * @param tc a transitive closure solution for the first step of fulkerson method
     * @return a chain decomposition with minimum cardinality
     */
    public LinkedList<Chain> optChainDecomposition(Vertex[] topolSorting, TransitiveClosure tc ){
        return FulkersonMethod(topolSorting,tc);
    }

    /**
     *Returns a chain decomposition with minimum cardinality using Fulkerson Method.
     *
     * @param topolSorting a topological sorting of the graph
     * @return a chain decomposition with minimum cardinality
     */
    public LinkedList<Chain> optChainDecomposition(Vertex[] topolSorting){
        OnlineDFS tc = new OnlineDFS();
        return FulkersonMethod(topolSorting,tc);
    }

    /**
     * Returns a chain decomposition with minimum cardinality using Fulkerson Method.
     *
     * @param topolSorting a topological sorting of the graph
     * @param tc a transitive closure solution for the first step of fulkerson method
     * @return a chain decomposition with minimum cardinality
     */
    public static LinkedList<Chain> FulkersonMethod(Vertex[] topolSorting, TransitiveClosure tc){
        LinkedList<Chain> decomposition = new LinkedList<>();

        MaximalMatching.BipGraph bg = new MaximalMatching.BipGraph(topolSorting.length, topolSorting.length);
        HashMap<Integer,Vertex> id_v = new HashMap<>();

        for(Vertex v:topolSorting){
            LinkedList<Vertex> successors = tc.getSuccessors(v);
            id_v.put(v.getTopolRank()+1,v);
            for(Vertex s:successors){
                bg.addLink( v.getTopolRank()+1, s.getTopolRank()+1 );
            }
        }

        int M = bg.hopcroftKarp();
        int width = topolSorting.length - M;

        //build chains
        int[] matching = bg.getpairU();
        boolean []isVisited = new boolean[topolSorting.length];
        for(int j = 1;j<matching.length;++j){
            Vertex v = id_v.get(j);
            if( !isVisited[v.getTopolRank()]) {
                Chain C = new Chain();
                C.addTop(v);
                isVisited[v.getTopolRank()] = true;

                int match_id = matching[j];
                Vertex match = id_v.get(match_id);
                while (match != null) {
                    if(!isVisited[match.getTopolRank()]) {
                        C.addTop(match);
                        isVisited[match.getTopolRank()] = true;
                        match_id = matching[match_id];
                        match = id_v.get(match_id);
                    }
                }
                decomposition.add(C);
            }
        }

        assert width!=decomposition.size() : "Bug: Fulkerson method chain decomposition size is not equal to the width";

        return decomposition;
    }

    public static void printDecomposition(LinkedList<Chain> decomposition){
        int chain_no = 0;
        for(Chain C:decomposition){
            System.out.print("Chain "+chain_no+": ");
            for (Vertex v:C.getVertices()){
                System.out.print(" "+v.getID());
            }
            System.out.println("");
            ++chain_no;
        }
    }
}
