package graphhierarchies.chaindecomposition;

import graphhierarchies.graph.Vertex;

import java.util.LinkedList;

public class Chain {
    private LinkedList<Vertex> chain;

    public Chain() {
        this.chain = new LinkedList<Vertex>();
    }
public void addTop(Vertex n){
        chain.addLast(n);
    }

    public LinkedList<Vertex> getVertices() {
        return chain;
    }
    public Vertex getLastVertex(){
        return chain.getLast();
    }
}
