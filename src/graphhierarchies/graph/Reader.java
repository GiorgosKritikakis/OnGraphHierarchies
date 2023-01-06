package graphhierarchies.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Reader {
    public static DiGraph ReadEdgeList(File textfile){
        HashMap<Integer,TreeSet<Integer>[]> adjs = new HashMap<Integer,TreeSet<Integer>[]>();
        HashMap<Integer, Vertex> nodes = new HashMap<Integer, Vertex>();
        int edges = 0;
        Scanner sc = null;
        try {
            sc = new Scanner(textfile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Integer line = 1;
        try {
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                edges+=parseLine(data, adjs, nodes, line);
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

        HashSet<Vertex> graph_vertices = new HashSet<Vertex>();
        for(Vertex n:nodes.values()){
            graph_vertices.add(n);
        }

        return (new DiGraph(graph_vertices,edges));
    }

    public static DiGraph ReadEdgeList(String inputstr){
        HashMap<Integer,TreeSet<Integer>[]> adjs = new HashMap<Integer,TreeSet<Integer>[]>();
        HashMap<Integer, Vertex> nodes = new HashMap<Integer, Vertex>();
        Integer line = 1;
        int edges = 0;
        try {
            String[] data = inputstr.split("\\r?\\n|\\r");
            for(String edge:data){
                edges+=parseLine(edge, adjs, nodes, line);
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

        HashSet<Vertex> graph_vertices = new HashSet<Vertex>();
        for(Vertex n:nodes.values()){
            graph_vertices.add(n);
        }

        return (new DiGraph(graph_vertices,edges));
    }
    private static int parseLine(String data, HashMap<Integer,TreeSet<Integer>[]> adjs, HashMap<Integer, Vertex> nodes, Integer line){
        if(data==""){
            return 0;
        }
        String[] edge = data.split(",");
        int sourceid = Integer.parseInt(edge[0]);
        int targetid = Integer.parseInt(edge[1]);
        if(sourceid == targetid ){
            return 0;
        }
        nodes.put(sourceid,new Vertex(sourceid));
        nodes.put(targetid,new Vertex(targetid));

        TreeSet<Integer> []adjsTarget = adjs.get(targetid);
        TreeSet<Integer> []adjsSource = adjs.get(sourceid);

        if(adjsTarget!=null){
            TreeSet<Integer> sources = adjsTarget[0];
            sources.add(sourceid);
        }else{
            TreeSet<Integer> sources = new TreeSet<Integer>();
            TreeSet<Integer> targets = new TreeSet<Integer>();
            sources.add(sourceid);
            TreeSet<Integer> tmp[] = new TreeSet[2];
            tmp[0] = sources;
            tmp[1] = targets;
            adjs.put(targetid,tmp);
        }

        if(adjsSource!=null){
            TreeSet<Integer> targets = adjsSource[1];
            targets.add(targetid);
        }else{
            TreeSet<Integer> sources = new TreeSet<Integer>();
            TreeSet<Integer> targets = new TreeSet<Integer>();
            targets.add(targetid);
            TreeSet<Integer> tmp[] = new TreeSet[2];
            tmp[0] = sources;
            tmp[1] = targets;
            adjs.put(sourceid,tmp);
        }
        return 1;
    }
}
