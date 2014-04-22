package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SparseGraph {

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

    public Collection<Vertex> getVertexSet() {
        return vertexSet.values();
    }

    public Collection<String> getKeys() {
        return vertexSet.keySet();
    }

    public Vertex getVertex(String key) {
        return vertexSet.get(key);
    }

    public Vertex getStartVertex() {
        return vertexSet.get(this.startKey);
    }

    public ArrayList<Edge> getAdj(String key) {
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

    public Edge edgeAdj(String a, String b) {
        for (Edge edge : this.getAdj(a)) {
            if (edge.getEndKey().equals(b)) {
                return edge;
            }
        }
        return null;
    }

    public void invalidate(String beginKey, String endKey) {
        Edge ab = null;
        for (Edge edge : this.getAdj(beginKey)) {
            if (edge.getEndKey().equals(endKey)) {
                ab = edge;
            }
        }
        if (ab.isActive()) {
            ab.setActive(false);
            for (Edge ba : this.getAdj(ab.getEndKey())) {
                if (ba.getEndKey().equals(ab.getBeginKey())) {
                    ba.setActive(false);
                }
            }
        }
    }

    public void decreaseDegree(Edge ab) {
        if (ab.isActive()) {
            Vertex v = this.getVertex(ab.getBeginKey());
            v.heuristic--;
            v = this.getVertex(ab.getEndKey());
            v.heuristic--;
        }
    }

    public void validateAllEdges() {
        for (Edge edge : this.edgeSet) {
            edge.setActive(true);
        }

    }

    public SparseGraph buildRestrictionsGraph() {
        //cria vertices
        SparseGraph restrictionsGraph = new SparseGraph();
        this.validateAllEdges();
        for (Edge edge : this.edgeSet) {
            if (edge.isActive()) {
                Vertex v = new Vertex(edge.getBeginKey() + " " + edge.getEndKey());
                restrictionsGraph.addVertex(v);
                this.invalidate(edge.getBeginKey(), edge.getEndKey());
            }
        }
        //cria arestas
        Edge edge;
        Vertex beginRestVertex;
        Vertex endRestVertex;
        for (Vertex vertex : this.getVertexSet()) {

            for (int i = 0; i < this.getAdj(vertex.getKey()).size(); i++) {

                edge = this.getAdj(vertex.getKey()).get(i);
                beginRestVertex = restrictionsGraph.getRestrictionVertex(edge);

                for (int j = i; j < this.getAdj(vertex.getKey()).size(); j++) {

                    edge = this.getAdj(vertex.getKey()).get(j);
                    endRestVertex = restrictionsGraph.getRestrictionVertex(edge);

                    Edge ab = new Edge(beginRestVertex.getKey(), endRestVertex.getKey());
                    Edge ba = new Edge(endRestVertex.getKey(), beginRestVertex.getKey());
                    if (!ab.getBeginKey().equals(ab.getEndKey())) {
                        restrictionsGraph.addEdge(ab);
                        restrictionsGraph.addEdge(ba);
                    }
                }
            }
        }
        this.validateAllEdges();
        restrictionsGraph.validateAllEdges();
        return restrictionsGraph;
    }

    public Vertex getRestrictionVertex(Edge edge) {
        Vertex v = this.getVertex(edge.getBeginKey() + " " + edge.getEndKey());
        if (v == null) {
            v = this.getVertex(edge.getEndKey() + " " + edge.getBeginKey());
        }
        return v;
    }

    public int calculateWayCost(ArrayList<String> circuit) {
        String a, b;
        Edge e;
        int way = 0;

        this.validateAllEdges();

        for (int i = 0; i < circuit.size() - 1; i++) {
            a = circuit.get(i);
            b = circuit.get(i + 1);
            e = this.edgeAdj(a, b);
            this.invalidate(a, b);
            way += e.getWeight();
        }
        return way;
    }

    public ArrayList<Edge> calculateMissEdges() {
        ArrayList<Edge> missEdges = new ArrayList();
        for (String s : this.getKeys()) {
            for (Edge edge : this.getAdj(s)) {
                if (edge.isActive()) {
                    missEdges.add(edge);
                }
            }
        }
        return missEdges;
    }

}
