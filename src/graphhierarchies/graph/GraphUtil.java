package graphhierarchies.graph;
import java.util.*;

public class GraphUtil {
    private GraphUtil() {}
    static void sortAdjTargets( Vertex[] topOrder) {
        LinkedList<Vertex> sortedAdjTargets[] = new LinkedList[topOrder.length];
        for(int i=0; i<topOrder.length;++i){
            sortedAdjTargets[i] = new LinkedList<>();
        }
        for(int i=topOrder.length-1;i>=0;--i){
            Vertex v = topOrder[i];
            for(Vertex s : v.getAdjSources()){
                sortedAdjTargets[s.getTopolRank()].addFirst(v);
            }
        }
        for(Vertex v:topOrder){
            v.setAdjTargets(sortedAdjTargets[v.getTopolRank()]);
        }
    }
    static void sortAdjSources( Vertex[] topOrder) {
        LinkedList<Vertex> sortedAdjSources[] = new LinkedList[topOrder.length];
        for(int i=0; i<topOrder.length;++i){
            sortedAdjSources[i] = new LinkedList<>();
        }
        for(int i=0;i< topOrder.length;++i){
            Vertex v = topOrder[i];
            for(Vertex s : v.getAdjTargets()){
                sortedAdjSources[s.getTopolRank()].addFirst(v);
            }
        }
        for(Vertex v:topOrder){
            v.setAdjSources(sortedAdjSources[v.getTopolRank()]);
        }
    }

    public static void sortAdjLists(Vertex[] topOrder){
        sortAdjTargets(topOrder);
        sortAdjSources(topOrder);
    }

    /**
     * The method assigns to every vertex a topological rank. Additionally, it returns an array of the vertices
     * in ascending topological order.
     * @param digraph a directed acyclic graph.
     * @return an array of the vertices in ascending topological order
     */
    public static final Vertex[] setTopologicalRank(DiGraph digraph) {
        Vertex sorting[] = new Vertex[digraph.verticesSize()];
        Integer RankIndex = digraph.verticesSize()-1;
        HashMap<Vertex, Boolean> isVisited = new HashMap<>(digraph.verticesSize());
        LinkedList<Vertex> sources = new LinkedList<>();

        for (Vertex n: digraph.getVertices()) {
            isVisited.put(n, false);
            if(n.getAdjSources().isEmpty()){
                sources.add(n);
            }
        }

        for(Vertex n:sources){
            RankIndex = TopologicalSortUtil(n, sorting, RankIndex, isVisited );
        }

        int rank = 0;
        for(Vertex n: sorting){
            n.setTopolRank(rank);
            rank++;
        }
        return sorting;
    }
    private static Integer TopologicalSortUtil(Vertex current, Vertex[] sorting, Integer RankIndex, HashMap<Vertex,Boolean> isVisited){
         isVisited.put(current,true);
         for (Vertex dest : current.getAdjTargets() ) {
             if (!isVisited.get(dest)) {
                 RankIndex = TopologicalSortUtil(dest,sorting,RankIndex, isVisited);
             }
         }
         sorting[RankIndex] = current;
         return (RankIndex-1);

     }

    /**
     * Checks if a graph is a directed acyclic graph. It returns true if it is and false if it is not.
     * @param digraph a directed graph
     * @return returns true if the digraph is acyclic, otherwise, it returns false.
     */
    public static boolean IsDirectedAcyclicGraph(DiGraph digraph){
        HashMap<Vertex,Boolean> isVisited = new HashMap<>( digraph.verticesSize() );
        HashMap<Vertex,Boolean> isActive = new HashMap<>();
        HashMap<Vertex,Iterator<Vertex>> iterators = new HashMap<>( digraph.verticesSize() );
        for(Vertex n:digraph.getVertices()){
            isVisited.put(n,false);
            iterators.put( n,n.getAdjTargets().iterator() ) ;
        }
        for (Vertex n:digraph.getVertices()){
            if(!isVisited.get(n)){
                if( dfsFindCycle(n,isVisited,isActive,iterators) ){
                    return false;
                }
            }
        }
        return true;
    }

    static boolean dfsFindCycle(Vertex root, HashMap<Vertex,Boolean> isVisited, HashMap<Vertex,Boolean> isActive, HashMap<Vertex,Iterator<Vertex>> iterators) {
        Stack<Vertex> stack = new Stack<>();
        stack.push(root);
        isActive.put(root,true);
        isVisited.put(root,true);
        while (!stack.isEmpty()) {
            Vertex current = stack.peek();
            Iterator<Vertex> it = iterators.get(current);
            while(true) {
                if (it.hasNext()) {
                    Vertex adjTarget = it.next();
                    if (!isVisited.get(adjTarget)) {
                        stack.push(adjTarget);
                        isActive.put(adjTarget,true);
                        isVisited.put(adjTarget,true);
                        break;
                    }else if( isActive.get(adjTarget) ){
                        return true;
                    }
                }else{
                    stack.pop();
                    isActive.put(current,false);
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there is a path from the source vertex to the target vertex. Otherwise, it returns false. It searches
     * using a DFS function
     * @param source The source vertex that the DFS search function starts searching.
     * @param target The target vertex.
     * @return true if we can reach vertex target from vertex source, otherwise, false.
     */
    public static boolean DFS_lookup(Vertex source,Vertex target){
        Stack<Vertex> stack = new Stack<>();
        HashMap<Vertex,Boolean> isVisited = new HashMap<>();
        stack.push(source);
        while (!stack.isEmpty()){
            Vertex v = stack.pop();
            if( isVisited.get(v)==null ){
                isVisited.put(v,true);
                if(v==target){return true;}
                for(Vertex adj:v.getAdjTargets()){
                    if(isVisited.get(adj)==null){
                        stack.add(adj);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns all successors(not only the immediate) of a vertex.
     * @param source the source vertex.
     * @return a linked list that contains all successors of the source vertex
     */
    public static LinkedList<Vertex> DFS_lookup(Vertex source){
        Stack<Vertex> stack = new Stack<>();
        LinkedList<Vertex> successors = new LinkedList<>();
        HashMap<Vertex,Boolean> isVisited = new HashMap<>();
        for(Vertex adj:source.getAdjTargets()) {
            stack.push(adj);
        }
        while (!stack.isEmpty()){
            Vertex v = stack.pop();
            if( isVisited.get(v)==null ){
                successors.add(v);
                isVisited.put(v,true);
                for(Vertex adj:v.getAdjTargets()){
                    if(isVisited.get(adj)==null){
                        stack.add(adj);
                    }
                }
            }
        }
        return successors;
    }


}
