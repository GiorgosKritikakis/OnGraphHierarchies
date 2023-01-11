package graphhierarchies.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * The class provides static methods that read input graphs in proper format and create directed graph instances (DiGraph).
 */
public class Reader {
    /**
     * The method read the input file and create a DiGraph without parallel edges and self-loops. Each line of the input file
     * must have one of the following forms:<br>
     * 1)"x,y" where x,y are decimal integers. The line represents an edge from the vertex with id x to the vertex with id y.
     * For example, the line "5,6" represents an edge from vertex 5 to vertex 6.<br>
     * 2)"x" where x is a decimal integer that represent a single vertex.<br>
     * 3)"" the empty line.<br>
     *
     * @param textfile is the input file that represents a graph.
     * @return an instance of DiGraph according to the input file
     */
    public static DiGraph ReadEdgeList(File textfile){
        HashMap<Integer,TreeSet<Integer>[]> adjs = new HashMap<>();
        HashMap<Integer, Vertex> nodes = new HashMap<>();
        Scanner sc;
        try {
            sc = new Scanner(textfile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Integer line = 1;
        try {
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                parseLine(data, adjs, nodes, line);
                ++line;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid file format: "+textfile.getName()+" file format caused exception in line "+line);
            throw new ArrayIndexOutOfBoundsException();
        }catch (NumberFormatException e){
            System.out.println("Invalid file format: "+textfile.getName()+" file format caused exception in line "+line);
            throw new NumberFormatException();
        }

        for(Vertex n: nodes.values()){
            for( Integer i:adjs.get(n.getID())[0]  ){
                n.addAdjSource(nodes.get(i));
            }
            for( Integer i:adjs.get(n.getID())[1]  ){
                n.addAdjTarget(nodes.get(i));
            }
        }

        HashSet<Vertex> graph_vertices = new HashSet<>();
        graph_vertices.addAll(nodes.values());

        return (new DiGraph(graph_vertices));
    }

    /**
     * The method read the input string and create a DiGraph without parallel edges and self-loops. Each line of the input string
     * must have one of the following forms:<br>
     * 1)"x,y" where x,y are decimal integers. The line represents an edge from the vertex with id x to the vertex with id y.
     * For example, the line "5,6" represents an edge from vertex 5 to vertex 6.<br>
     * 2)"x" where x is a decimal integer that represent a single vertex.<br>
     * 3)"" the empty line.<br>
     *
     * @param inputstr is the input string that represents a graph.
     * @return an instance of DiGraph according to the input string
     */
    public static DiGraph ReadEdgeList(String inputstr){
        HashMap<Integer,TreeSet<Integer>[]> adjs = new HashMap<>();
        HashMap<Integer, Vertex> nodes = new HashMap<>();
        Integer line = 1;
        try {
            String[] data = inputstr.split("\\r?\\n|\\r");
            for(String edge:data){
                parseLine(edge, adjs, nodes, line);
                ++line;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid format: string format caused exception in line "+line);
            throw new ArrayIndexOutOfBoundsException();
        }catch (NumberFormatException e){
            System.out.println("Invalid format: string format caused exception in line "+line);
            throw new NumberFormatException();
        }

        for(Vertex n: nodes.values()){
            for( Integer i:adjs.get(n.getID())[0]  ){
                n.addAdjSource(nodes.get(i));
            }
            for( Integer i:adjs.get(n.getID())[1]  ){
                n.addAdjTarget(nodes.get(i));
            }
        }

        HashSet<Vertex> graph_vertices = new HashSet<>();
        graph_vertices.addAll(nodes.values());

        return (new DiGraph(graph_vertices));
    }
    private static int parseLine(String data, HashMap<Integer,TreeSet<Integer>[]> adjs, HashMap<Integer, Vertex> nodes, Integer line){
        if(data==""){
            return 0;
        }
        String[] edge = data.split(",");
        if(edge.length==1){
            int id = Integer.parseInt(edge[0]);
            nodes.put(id, new Vertex(id));

            TreeSet<Integer>[] adjs_v = adjs.get(id);
            if (adjs_v == null) {
                TreeSet<Integer> sources = new TreeSet<>();
                TreeSet<Integer> targets = new TreeSet<>();
                TreeSet<Integer> tmp[] = new TreeSet[2];
                tmp[0] = sources;
                tmp[1] = targets;
                adjs.put(id, tmp);
            }
            return 0;
        }else {
            int sourceid = Integer.parseInt(edge[0]);
            int targetid = Integer.parseInt(edge[1]);
            if (sourceid == targetid) {
                return 0;
            }
            nodes.put(sourceid, new Vertex(sourceid));
            nodes.put(targetid, new Vertex(targetid));

            TreeSet<Integer>[] adjsTarget = adjs.get(targetid);
            TreeSet<Integer>[] adjsSource = adjs.get(sourceid);

            if (adjsTarget != null) {
                TreeSet<Integer> sources = adjsTarget[0];
                sources.add(sourceid);
            } else {
                TreeSet<Integer> sources = new TreeSet<>();
                TreeSet<Integer> targets = new TreeSet<>();
                sources.add(sourceid);
                TreeSet<Integer> tmp[] = new TreeSet[2];
                tmp[0] = sources;
                tmp[1] = targets;
                adjs.put(targetid, tmp);
            }

            if (adjsSource != null) {
                TreeSet<Integer> targets = adjsSource[1];
                targets.add(targetid);
            } else {
                TreeSet<Integer> sources = new TreeSet<>();
                TreeSet<Integer> targets = new TreeSet<>();
                targets.add(targetid);
                TreeSet<Integer> tmp[] = new TreeSet[2];
                tmp[0] = sources;
                tmp[1] = targets;
                adjs.put(sourceid, tmp);
            }
            return 1;
        }
    }
}
