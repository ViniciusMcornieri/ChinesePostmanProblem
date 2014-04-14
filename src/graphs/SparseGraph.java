package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SparseGraph{

    private final HashMap<String, ArrayList<Edge>> adj;
    private final HashMap<String, Vertex> vertexSet;
    public ArrayList<Edge> edgeSet;
    public String startKey;
    
    public SparseGraph() {
        adj = new HashMap();
        vertexSet = new HashMap();
        edgeSet = new ArrayList();
        startKey = null;
    }
    
    public Collection<Vertex> getVertexSet(){
        return vertexSet.values();
    }
    
    public Collection<String> getKeys(){
        return vertexSet.keySet();
    }

    public Vertex getVertex(String key){
        return vertexSet.get(key);    
    }
  
    public Vertex getStartVertex(){
        return vertexSet.get(this.startKey);
    }
    
    public ArrayList<Edge> getAdj(String key){
        return adj.get(key);
    }

    public void addVertex(Vertex v) {
        vertexSet.put(v.getKey(), v);
        adj.put(v.getKey(), new ArrayList<Edge>());
    }    

    public void addEdge(Edge e) {
        edgeSet.add(e);
        adj.get(e.getBeginKey()).add(e);
    }

    public SparseGraph transpose() {
        SparseGraph transposed = new SparseGraph();
        transposed.startKey = this.startKey;
        for (Vertex vertex : this.vertexSet.values()) {
            transposed.addVertex(new Vertex(vertex.getKey()));
        }
        for (ArrayList<Edge> adjacencyList : this.adj.values()) {
            for (Edge edge : adjacencyList) {
                transposed.addEdge(new Edge(edge.getEndKey(), edge.getBeginKey()));
            }
        }
        return transposed;
    }
}
