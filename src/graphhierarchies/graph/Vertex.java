package graphhierarchies.graph;

import java.util.LinkedList;

public class Vertex {
    private int ID;
    private int topolRank;
    private LinkedList<Vertex> adjTargets;
    private LinkedList<Vertex> adjSources;

    public int getID() {
        return ID;
    }

    public LinkedList<Vertex> getAdjTargets() {
        return adjTargets;
    }

    public void setAdjTargets(LinkedList<Vertex> adjTargets) {
        this.adjTargets = adjTargets;
    }

    public LinkedList<Vertex> getAdjSources() {
        return adjSources;
    }

    public void setAdjSources(LinkedList<Vertex> adjSources) {
        this.adjSources = adjSources;
    }

    public int getTopolRank() {
        return topolRank;
    }

    public void setTopolRank(int topolRank) {
        this.topolRank = topolRank;
    }

    public Vertex(int ID) {
        this.ID = ID;
        this.topolRank = -1;
        this.adjSources = new LinkedList<>();
        this.adjTargets = new LinkedList<>();
    }
    public void addAdjTarget(Vertex n){
        this.adjTargets.add(n);
    }
    public void addAdjSource(Vertex n){
        this.adjSources.add(n);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vertex){
            return this.ID == ((Vertex)obj).ID;
        }
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        return this.ID;
    }
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("{ \n\tNode : "+this.ID+",\n\tsources : ");
        for(Vertex n:this.adjSources){
            str.append(n.getID()+" ");
        }
        str.append("\n\ttargets: ");
        for(Vertex n:this.adjTargets){
            str.append(n.getID()+" ");
        }
        str.append("\n}");
        return str.toString();
    }

}
