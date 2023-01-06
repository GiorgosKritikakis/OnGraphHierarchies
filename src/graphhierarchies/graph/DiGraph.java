package graphhierarchies.graph;

import java.util.HashSet;

public class DiGraph {
    private HashSet<Vertex> vertices;
    private int edges;

    public int getEdges() {
        return edges;
    }

    DiGraph(HashSet<Vertex> vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("");
        for(Vertex n: vertices) {
            str.append(n.toString());
            str.append("\n");
        }
        return str.toString();
    }

    public int verticesSize(){return this.vertices.size();}
    public HashSet<Vertex> getVertices() {
        return vertices;
    }
}
