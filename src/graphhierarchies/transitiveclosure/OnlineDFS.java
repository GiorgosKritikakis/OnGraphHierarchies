package graphhierarchies.transitiveclosure;

import graphhierarchies.graph.Vertex;

import java.util.LinkedList;

import static graphhierarchies.graph.GraphUtil.DFS_lookup;

/**
 * The class uses on-line Depth First Search functions to answer reachability queries. Hence, every query needs O(|V|+|E|) time, where
 * V is the set of vertices and E the set of edges.
 */
public class OnlineDFS implements TransitiveClosure{
    @Override
    public boolean isReachable(Vertex source, Vertex target) {
        return DFS_lookup(source,target);
    }

    @Override
    public LinkedList<Vertex> getSuccessors(Vertex v) {
        return DFS_lookup(v);
    }


}
