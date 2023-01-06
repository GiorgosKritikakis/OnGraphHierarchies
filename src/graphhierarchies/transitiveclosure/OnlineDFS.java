package graphhierarchies.transitiveclosure;

import graphhierarchies.graph.Vertex;

import java.util.LinkedList;

import static graphhierarchies.graph.GraphUtil.DFS_lookup;

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
