package algorithms;

import graphs.SparseGraph;
import graphs.Vertex;
import graphs.Edge;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class BFS {

    private final SparseGraph digraph;
    private final LinkedList<Vertex> out;

    public BFS(SparseGraph dg) {
        digraph = dg;
        out = new LinkedList();
    }

    public void perform() {
        for (Vertex v : digraph.getVertexSet()) {
            v.white = true;
            v.distance = Integer.MAX_VALUE;
            v.father = null;
        }
        Vertex s = digraph.getStartVertex();
        s.white = false;
        s.distance = 0;
        ArrayDeque<Vertex> q = new ArrayDeque();
        q.add(s);
        while (q.peek() != null) {
            Vertex u = (Vertex) q.poll();
            for (Edge e : digraph.getAdj(u.getKey())) {
                Vertex v = digraph.getVertex(e.getEndKey());
                if (v.white) {
                    v.white = false;
                    v.distance = u.distance + 1;
                    v.father = u.getKey();
                    q.add(v);
                }
            }
            out.offer(u);
        }
    }

    public void printOut() {
        String sKey = digraph.getStartVertex().getKey();
        for (Vertex u : out) {
            System.out.println(sKey + " " + u.getKey() + " " + u.distance);
        }
    }
}
