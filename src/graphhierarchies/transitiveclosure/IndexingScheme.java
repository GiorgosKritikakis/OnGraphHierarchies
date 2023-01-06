package graphhierarchies.transitiveclosure;

import graphhierarchies.chaindecomposition.Chain;
import graphhierarchies.graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class IndexingScheme implements TransitiveClosure{
    private final int INF;
    private Indices[] vertexIndices;
    private ArrayList<Indices> []scheme;

    @Override
    public boolean isReachable(Vertex source, Vertex target) {
        if(source==target){return true;}
        Indices t_indices = vertexIndices[target.getTopolRank()];
        int t_chain = t_indices.chain;
        int t_pos = getPosition(t_indices);
        Indices s_indices = vertexIndices[source.getTopolRank()];
        int s_index = s_indices.indices[t_chain];
        if(s_index<=t_pos){
            return  true;
        }
        return false;
    }

    @Override
    public LinkedList<Vertex> getSuccessors(Vertex v) {
        LinkedList<Vertex> successors = new LinkedList<>();
        Indices v_indices = vertexIndices[v.getTopolRank()];
        int chain = 0;
        for(int index:v_indices.indices){
            for(int i=index;i<scheme[chain].size();++i){
                Vertex toAdd = scheme[chain].get(i).v;
                successors.add(toAdd);
            }
            ++chain;
        }
        return successors;
    }

    class Indices{
        int[] indices;
        int chain;
        Vertex v;
        Indices(int kc,Vertex v,int chain_no){
            indices = new int[kc];
            Arrays.fill(indices, INF);
            this.v = v;
            this.chain = chain_no;
        }
    }

    public IndexingScheme(LinkedList<Chain> decomposition, Vertex[] topolSorting){
        //Initialization
        int kc = decomposition.size();
        scheme = new ArrayList[kc];
        vertexIndices = new Indices[topolSorting.length];
        INF = Integer.MAX_VALUE;

        int chain_no = 0;
        for(Chain C:decomposition){
            scheme[chain_no] = new ArrayList<>(C.getVertices().size());
            for(Vertex v:C.getVertices()) {
                Indices indices = new Indices(kc,v,chain_no);
                scheme[chain_no].add(indices);
                vertexIndices[v.getTopolRank()] = indices;
            }
            scheme[chain_no].trimToSize();
            ++chain_no;
        }

        //creating indices
        for(int j = topolSorting.length-1;j>=0;--j){
            Vertex v = topolSorting[j];
            for(Vertex t:v.getAdjTargets()){
                if( !isReachable(v,t) ){
                    update_indices(v,t);
                }
            }
        }
    }
    private void update_indices(Vertex source,Vertex target){
        int[] s_indices_array = vertexIndices[source.getTopolRank()].indices;
        Indices t_indices = vertexIndices[target.getTopolRank()];
        int[] t_indices_array = t_indices.indices;
        for(int i=0;i<s_indices_array.length;++i){
            if(s_indices_array[i]>t_indices_array[i]){
                s_indices_array[i] = t_indices_array[i];
            }
        }

        int t_chain = vertexIndices[target.getTopolRank()].chain;
        int t_index = getPosition(t_indices);
        if(s_indices_array[t_chain]>t_index){
            s_indices_array[t_chain]=t_index;
        }
    }
    private int getPosition(Indices indices){
        int c = indices.chain;
        int index = indices.indices[c];

        if(index==INF){
            return scheme[c].size()-1;
        }else{
            return index-1;
        }
    }

    public void printScheme(){
        System.out.println("\nSCHEME:");
        int chain = 0;
        for( ArrayList<Indices> C:scheme){
            System.out.println("Chain "+chain);
            for(Indices i:C){
                System.out.print(" [V"+i.v.getID()+":");
                for(int index:i.indices){
                    System.out.print(" "+index);
                }
                System.out.print("] ");
            }
            System.out.println("");
            ++chain;
        }
    }
}
