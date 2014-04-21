package psr;

import graphs.*;

public class PSR {

    SparseGraph sg;
    SparseGraph restrictionsGraph;

    public PSR(SparseGraph sg) {
        this.sg = sg;
        this.restrictionsGraph = new SparseGraph();
        buildRestrictionsGraph();
    }

    private void buildRestrictionsGraph() {
        //cria vertices
        sg.validateAllEdges();
        for (Edge edge : sg.edgeSet) {
            if (edge.isActive()) {
                Vertex v = new Vertex(edge.getBeginKey() + " " + edge.getEndKey());
                restrictionsGraph.addVertex(v);
                sg.invalidate(edge.getBeginKey(), edge.getEndKey());
            }
        }       
       //cria arestas
        Edge edge;
        Vertex beginRestVertex;
        Vertex endRestVertex;
        for (Vertex vertex : sg.getVertexSet()) {

            for (int i = 0; i < sg.getAdj(vertex.getKey()).size(); i++) {

                edge = sg.getAdj(vertex.getKey()).get(i);
                beginRestVertex = this.getRestrictionVertex(edge);

                for (int j = i; j < sg.getAdj(vertex.getKey()).size(); j++) {

                    edge = sg.getAdj(vertex.getKey()).get(j);
                    endRestVertex = this.getRestrictionVertex(edge);

                    Edge ab = new Edge(beginRestVertex.getKey(), endRestVertex.getKey());
                    Edge ba = new Edge(endRestVertex.getKey(), beginRestVertex.getKey());
                    if (!ab.getBeginKey().equals(ab.getEndKey())) {
                        restrictionsGraph.addEdge(ab);
                        restrictionsGraph.addEdge(ba);
                    }
                }
            }
        }
        sg.validateAllEdges();
        restrictionsGraph.validateAllEdges();
    }

    public Vertex getRestrictionVertex(Edge edge) {
        Vertex v = restrictionsGraph.getVertex(edge.getBeginKey() + " " + edge.getEndKey());
        if (v == null) {
            v = restrictionsGraph.getVertex(edge.getEndKey() + " " + edge.getBeginKey());
        }
        return v;
    }

}
