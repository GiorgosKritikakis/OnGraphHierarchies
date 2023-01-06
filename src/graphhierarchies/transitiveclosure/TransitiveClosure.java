package graphhierarchies.transitiveclosure;

import graphhierarchies.graph.Vertex;

import java.util.LinkedList;

public interface TransitiveClosure {
    /**
     * Returns true if there is a path from the source vertex to the target vertex. Otherwise, it returns false.
     *
     * @param source the starting vertex
     * @param target the destination vertex
     * @return true if there is a path between vertex source and vertex target, otherwise, false
     */
    public boolean isReachable(Vertex source,Vertex target);

    /**
     * Returns all successors(not only the immediate) of a vertex.
     *
     * @param v the starting vertex
     * @return all successors of vertex v
     */
    public LinkedList<Vertex> getSuccessors(Vertex v);
}
