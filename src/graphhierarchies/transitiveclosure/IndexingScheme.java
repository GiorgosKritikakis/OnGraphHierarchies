package graphhierarchies.transitiveclosure;

import graphhierarchies.chaindecomposition.Chain;
import graphhierarchies.graph.Edge;
import graphhierarchies.graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * The class holds an indexing scheme based on chain decomposition. These kinds of indexing schemes
 * were introduced in the following papers:
 * <br>- K. SIMON. An improved algorithm for transitive closure on acyclic digraphs.
 * <br>- H. V. Jagadish. A Compression Technique to Materialize Transitive Closure.
 * <br>The class implementation is explicated in the paper:
 * <br>- Giorgos Kritikakis, Ioannis G. Tollis. "Fast and Practical DAG Decomposition with Reachability Applications".
 */
public class IndexingScheme implements TransitiveClosure{
    private final int INF = Integer.MAX_VALUE;
    private Indices[] vertexIndices;
    private ArrayList<Indices> []scheme;

    @Override
    public boolean isReachable(Vertex source, Vertex target) {
        if(source==target){return true;}
        Indices t_indices = vertexIndices[target.getTopolRank()];
        int t_chain = t_indices.chain;

        Indices s_indices = vertexIndices[source.getTopolRank()];
        int s_index = s_indices.indices[t_chain];
        int t_index = t_indices.indices[t_chain];
        if(s_index<t_index){
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


    /**
     * Builds an indexing scheme based on chain decomposition following the approach that is described in the paper:
     * <br>- Giorgos Kritikakis, Ioannis G. Tollis. "Fast and Practical DAG Decomposition with Reachability Applications".
     * @param decomposition a chain decomposition of the graph
     * @param topolSorting a topological sorting of the graph
     */
    public IndexingScheme(LinkedList<Chain> decomposition, Vertex[] topolSorting){
        create_IndexingScheme(decomposition,topolSorting,null);
    }
    public IndexingScheme(LinkedList<Chain> decomposition, Vertex[] topolSorting,LinkedList<Edge> Etr){
        create_IndexingScheme(decomposition,topolSorting,Etr);
    }

    public void create_IndexingScheme(LinkedList<Chain> decomposition, Vertex[] topolSorting,LinkedList<Edge> Etr){
        //Initialization
        int kc = decomposition.size();
        scheme = new ArrayList[kc];
        vertexIndices = new Indices[topolSorting.length];


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
                }else{ //transitive edge
                    if(Etr!=null){
                        Etr.add( new Edge(v,t) );
                    }
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
                    if(index==INF){
                        System.out.print(" -");
                    }else {
                        System.out.print(" " + index);
                    }
                }
                System.out.print("] ");
            }
            System.out.println("");
            ++chain;
        }
    }
}
