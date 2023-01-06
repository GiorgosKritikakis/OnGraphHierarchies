package graphhierarchies.transitiveclosure;

import graphhierarchies.graph.DiGraph;
import graphhierarchies.graph.Vertex;

import java.util.HashMap;
import java.util.LinkedList;

import static graphhierarchies.graph.GraphUtil.DFS_lookup;

public class AdjMatrix implements TransitiveClosure{
    private boolean adjmatrix[][];
    HashMap<Vertex,Integer> IDs;
    HashMap<Integer,Vertex> IDtoVertex;
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
