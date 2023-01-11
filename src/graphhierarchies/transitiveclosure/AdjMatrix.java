package graphhierarchies.transitiveclosure;

import graphhierarchies.graph.DiGraph;
import graphhierarchies.graph.Vertex;

import java.util.HashMap;
import java.util.LinkedList;

import static graphhierarchies.graph.GraphUtil.DFS_lookup;

/**
 * The class computes the transitive closure and keeps the results in an 2D adjacency matrix to answer reachability queries in constant time.
 * The construction of the 2D matrix needs O(|V|*|E|) time, where V is the set of vertices and E the set of edges.
 */
public class AdjMatrix implements TransitiveClosure{
    private final boolean[][] adjmatrix;
    HashMap<Vertex,Integer> IDs;
    HashMap<Integer,Vertex> IDtoVertex;

    /**
     * Computes the transitive closure of graph g and keeps the results in an 2D adjacency matrix in O(|V|*|E|) time,
     * where V is the set of vertices and E the set of edges.
     * @param g is the input graph
     */
    public AdjMatrix(DiGraph g){
        adjmatrix = new boolean[g.verticesSize()][g.verticesSize()];
        IDs = new HashMap<>();
        IDtoVertex = new HashMap<>();
        int id = 0;
        for(Vertex v:g.getVertices()){
            IDs.put(v,id);
            IDtoVertex.put(id,v);
            ++id;
        }

        for(Vertex v:g.getVertices()){
            LinkedList<Vertex> successors = DFS_lookup(v);
            for(Vertex s:successors) {
                adjmatrix[IDs.get(v)][IDs.get(s)] = true;
            }
        }
    }

    @Override
    public boolean isReachable(Vertex source, Vertex target) {
        Integer sid = IDs.get(source);
        Integer tid = IDs.get(target);
        if(sid==null||tid==null){
            return false;
        }
        if(adjmatrix[sid][tid]){
            return true;
        }
        return false;
    }

    @Override
    public LinkedList<Vertex> getSuccessors(Vertex v) {
        Integer id = IDs.get(v);
        if(id==null){
            return null;
        }

        LinkedList<Vertex> successors = new LinkedList<>();
        for(int i=0;i<IDs.size();++i){
            if(adjmatrix[id][i]){
                successors.add( IDtoVertex.get(i) );
            }
        }

        return  successors;
    }
}
