package graphhierarchies.chaindecomposition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


// An implementation of Hopcroft Karp algorithm for maximum matching
public class MaximalMatching{

    static private final int NIL = 0;
    static private final int INF = Integer.MAX_VALUE;


    // A class to represent Bipartite graph
    // for Hopcroft Karp implementation
    static class BipGraph
    {
        class Link {
            final int u;
            final int v;

            /*
             * 0 < right <= m
             * 0 < left <= n
             * */
            Link(int u, int v) {
                if (u > 0 && u <= size_left && v>0 && v<=size_right ) {
                    this.u = u;
                    this.v = v;
                }else {
                    throw new RuntimeException("Probably my code has a bug");
                }
            }
        }
        // The number of vertices on left
        // and right sides of Bipartite Graph
        int size_left, size_right;

        // adj[u] stores adjacents of left side
        // vertex 'u'. The value of u ranges
        // from 1 to m. 0 is used for dummy vertex
        List<Integer>[] adj;

        int[] pairU, pairV, dist;

        int getm(){return size_left;}
        int getn(){return size_right;}
        int[] getpairU(){return pairU;}
        int[] getpairV(){return pairV;}

        LinkedList<Integer> getfreenodes(){
            LinkedList<Integer> fn = new LinkedList<Integer>();
            int[] l1 = getpairU();
            for(int i=1;i<getm()+1;++i){
                if(l1[i]==0){
                    fn.add(i);
                }
            }
            return fn;
        }

        // Returns size of maximum matching
        int hopcroftKarp()
        {

            // pairU[u] stores pair of u in matching where u
            // is a vertex on left side of Bipartite Graph.
            // If u doesn't have any pair, then pairU[u] is NIL
            pairU = new int[size_left + 1];

            // pairV[v] stores pair of v in matching. If v
            // doesn't have any pair, then pairU[v] is NIL
            pairV = new int[size_right + 1];

            // dist[u] stores distance of left side vertices
            // dist[u] is one more than dist[u'] if u is next
            // to u'in augmenting path
            dist = new int[size_left + 1];

            // Initialize NIL as pair of all vertices
//            Arrays.fill(pairU, NIL);
//            Arrays.fill(pairV, NIL);

            // Initialize result
            int result = 0;

            // Keep updating the result while
            // there is an augmenting path.
            while (bfs())
            {
				/*System.out.println("BFS: ");
				for(int i=1;i<pairU.length;++i){
					System.out.println("("+i+","+pairU[i]+")");
				}*/
                // Find a free vertex
                for(int u = 1; u <= size_left; u++)

                    // If current vertex is free and there is
                    // an augmenting path from current vertex
                    if (pairU[u] == NIL && dfs(u)){
                        result++;
						/*System.out.println("DFS "+u+": ");
						for(int i=1;i<pairU.length;++i){
							System.out.print("("+i+","+pairU[i]+")");
						}*/
                    }
            }
            return result;
        }

        // Returns true if there is an augmenting
        // path, else returns false
        boolean bfs()
        {

            // An integer queue
            Queue<Integer> Q = new LinkedList<>();

            // First layer of vertices (set distance as 0)
            for(int u = 1; u <= size_left; u++)
            {

                // If this is a free vertex,
                // add it to queue
                if (pairU[u] == NIL)
                {

                    // u is not matched
                    dist[u] = 0;
                    Q.add(u);
                }

                // Else set distance as infinite
                // so that this vertex is
                // considered next time
                else
                    dist[u] = INF;
            }

            // Initialize distance to
            // NIL as infinite
            dist[NIL] = INF;

            // Q is going to contain vertices
            // of left side only.
            while (!Q.isEmpty())
            {

                // Dequeue a vertex
                int u = Q.poll();

                // If this node is not NIL and
                // can provide a shorter path to NIL
                if (dist[u] < dist[NIL])
                {

                    // Get all adjacent vertices of
                    // the dequeued vertex u
                    for(int i : adj[u])
                    {
                        int v = i;

                        // If pair of v is not considered
                        // so far (v, pairV[V]) is not yet
                        // explored edge.
                        if (dist[pairV[v]] == INF)
                        {

                            // Consider the pair and add
                            // it to queue
                            dist[pairV[v]] = dist[u] + 1;
                            Q.add(pairV[v]);
                        }
                    }
                }
            }

            // If we could come back to NIL using
            // alternating path of distinct vertices
            // then there is an augmenting path
            return (dist[NIL] != INF);
        }

        // Returns true if there is an augmenting
        // path beginning with free vertex u
        boolean dfs(int u)
        {
            //System.out.println("");
            if (u != NIL)
            {
                for(int i : adj[u])
                {

                    // Adjacent to u
                    int v = i;

                    // Follow the distances set by BFS
                    if (dist[pairV[v]] == dist[u] + 1)
                    {
                        // System.out.print(""+v+"-");
                        // If dfs for pair of v also returns
                        // true
                        if (dfs(pairV[v]) == true)
                        {
                            //System.out.print("["+u+"-"+v+"]");
                            pairV[v] = u;
                            pairU[u] = v;
                            return true;
                        }
                    }
                }

                // If there is no augmenting path
                // beginning with u.
                dist[u] = INF;
                return false;
            }
            return true;
        }

        public BipGraph(int size_left, int size_right)
        {
            this.size_left = size_left;
            this.size_right = size_right;
            adj = new ArrayList[size_left + 1];
            for(int i = 0; i< size_left +1; ++i){
                adj[i]=new ArrayList<>();
            }
        }

        // To add edge from u to v and v to u
        private void addEdge(int u, int v)
        {

            // Add u to v’s list.
            adj[u].add(v);
        }
        void addLink(int u, int v){
            Link link = new Link(u,v);
            addEdge(link.u,link.v);
        }
    }

//    public static void main(String[] args)
//    {
//
//        BipGraph g = new BipGraph(3, 3);
//        g.addLink(1, 1);
//        g.addLink(2, 1);
//        g.addLink(2, 3);
//        g.addLink(3, 3);
//        g.addLink(3, 2);
//
//        System.out.println("Size of maximum matching is " +
//                g.hopcroftKarp());
//        for(int i: g.pairU){
//            System.out.println(i);
//        }
//    }
}