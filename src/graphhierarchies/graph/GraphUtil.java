package graphhierarchies.graph;
import java.util.*;

public class GraphUtil {
    private GraphUtil() {}
    static void sortAdjTargets( Vertex[] topOrder) {
        LinkedList<Vertex> sortedAdjTargets[] = new LinkedList[topOrder.length];
        for(int i=0; i<topOrder.length;++i){
            sortedAdjTargets[i] = new LinkedList<Vertex>();
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
            sortedAdjSources[i] = new LinkedList<Vertex>();
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
    public static final Vertex[] setTopologicalRank(DiGraph digraph) {
        Vertex sorting[] = new Vertex[digraph.verticesSize()];
        Integer RankIndex = digraph.verticesSize()-1;
        HashMap<Vertex, Boolean> isVisited = new HashMap<Vertex, Boolean>(digraph.verticesSize());
        LinkedList<Vertex> sources = new LinkedList<Vertex>();

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
         //System.out.println("Index:"+RankIndex+" node:"+current.getID());
         sorting[RankIndex] = current;
         return (RankIndex-1);

     }
    public static boolean IsDirectedAcyclicGraph(DiGraph digraph){
        HashMap<Vertex,Boolean> isVisited = new HashMap<Vertex,Boolean>( digraph.verticesSize() );
        HashMap<Vertex,Boolean> isActive = new HashMap<Vertex,Boolean>();
        HashMap<Vertex,Iterator<Vertex>> iterators = new HashMap<Vertex,Iterator<Vertex>>( digraph.verticesSize() );
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

    public static boolean dfsFindCycle(Vertex root, HashMap<Vertex,Boolean> isVisited, HashMap<Vertex,Boolean> isActive, HashMap<Vertex,Iterator<Vertex>> iterators) {
        Stack<Vertex> stack = new Stack<Vertex>();
        stack.push(root);
        isActive.put(root,true);
        isVisited.put(root,true);
        //inStack.put(root,true);
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
                        //System.out.println("Edge: "+current.getID()+"->"+adjTarget.getID()+" form a cycle");
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
    public static LinkedList<Vertex> DFS_lookup(Vertex source){
        Stack<Vertex> stack = new Stack<>();
        LinkedList<Vertex> result = new LinkedList<>();
        HashMap<Vertex,Boolean> isVisited = new HashMap<>();
        for(Vertex adj:source.getAdjTargets()) {
            stack.push(adj);
        }
        while (!stack.isEmpty()){
            Vertex v = stack.pop();
            if( isVisited.get(v)==null ){
                result.add(v);
                isVisited.put(v,true);
                for(Vertex adj:v.getAdjTargets()){
                    if(isVisited.get(adj)==null){
                        stack.add(adj);
                    }
                }
            }
        }
        return result;
    }


}
