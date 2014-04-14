package algorithms;

import java.util.PriorityQueue;
import graphs.Edge;
import graphs.SparseGraph;
import graphs.Vertex;

public class Dijkstra extends Relaxation {

    private final PriorityQueue<Vertex> pq;

    public Dijkstra(SparseGraph wegraph) {
        sg = wegraph;
        pq = new PriorityQueue();
    }

    void initPQ() {
        for (Vertex u : sg.getVertexSet()) {
            pq.add(u);
        }
    }

    @Override
    public void relax(Vertex u, Vertex v, Edge e) {
        int sum;
        sum = u.distance + e.getWeight();
        if (u.distance != Integer.MAX_VALUE && v.distance > sum) {
            v.distance = sum;
            v.father = u.getKey();
            pq.add(v);
        }
    }

    public void perform() {
        Vertex u, v;
        init();
        initPQ();
        while (!pq.isEmpty()) {
            u = pq.poll();
            if (u.distance == sg.getVertex(u.getKey()).distance) {
                for (Edge e : sg.getAdj(u.getKey())) {
                    v = sg.getVertex(e.getEndKey());
                    relax(u, v, e);
                }
            }
        }
    }

   
    

}
