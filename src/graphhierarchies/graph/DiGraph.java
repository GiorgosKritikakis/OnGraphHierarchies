package graphhierarchies.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * The class represents a directed graph (Adjacent List).
 */
public class DiGraph {
    private HashMap<Integer,Vertex> vertices;
    private int edges;

    public LinkedList<Edge> getEdgeListRepr() {
        LinkedList<Edge> edgelist = new LinkedList<>();
        for(Vertex v:getVertices()){
            for (Vertex t:v.getAdjTargets()){
                edgelist.add(new Edge(v,t));
            }
        }
        return edgelist;
    }

    DiGraph(HashSet<Vertex> vertices) {
        this.vertices = new HashMap<>();
        for(Vertex v:vertices){
            this.vertices.put(v.getID(),v);
        }
    }

    public int verticesSize(){return this.vertices.size();}
    public Collection<Vertex> getVertices() {
        return vertices.values();
    }
    public Vertex getVertex(int ID){
        return this.vertices.get(ID);
    }

}
