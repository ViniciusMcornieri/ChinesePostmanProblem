package psr;

import graphs.*;

public class PSR {

    SparseGraph sg;
    SparseGraph rg;
    private final boolean WHITE = true;
    private final boolean NOT_WHITE = false;
    Vertex lastFather;
    
    public PSR(SparseGraph sg) {
        this.sg = sg;
        this.rg = sg.buildRestrictionsGraph();
    }

    public void init() {
        for (Vertex v : rg.getVertexSet()) {
            v.white = true;
            v.heuristic = rg.getAdj(v.getKey()).size();
        }

    }

    public Vertex getBest() {
        Vertex best = null;
        int heu = 0;
        for (Vertex v : rg.getVertexSet()) {
            if (heu < v.heuristic) {
                best = v;
                heu = v.heuristic;
            }
        }
        return best;
    }

    public Vertex getBestAdj(String key, boolean isWhite) {
        Vertex v;
        Vertex best = null;
        int heu = 0;
        for (Edge e : rg.getAdj(key)) {
            v = rg.getVertex(e.getEndKey());
            if (v.white == isWhite && heu < v.heuristic) {
                best = v;
                heu = v.heuristic;
            }
        }
        return best;
    }

    public boolean isAdjOf(Vertex father, Vertex first) {
        Vertex v;
        for (Edge e : rg.getAdj(father.getKey())) {
            v = rg.getVertex(e.getEndKey());
            if (v.getKey().equals(first.getKey())) {
                return true;
            }
        }
        return false;

    }

    public Vertex visit(Vertex first) {
        first.white = false;
        Vertex father = first;
        int notDone = rg.getVertexSet().size() - 1;
        boolean loop = true;
        Vertex next;
        do {
            next = getBestAdj(father.getKey(), WHITE);
            if (next != null) {
                next.white = false;
                --notDone;
                next.fatherList.addFirst(father.getKey());
                --father.heuristic;
                --next.heuristic;
                father = next;
            } else if (notDone == 0 && isAdjOf(father, first)) {
                loop = false;
            } else {
                next = getBestAdj(father.getKey(), NOT_WHITE);
                next.fatherList.addFirst(father.getKey());
                father = next;
            }

        } while (loop);

        return father;
    }

    public void perform() {
        init();
        lastFather = visit(getBest());
    }
    
    public void printOut(){
        Vertex v =  lastFather;
        while(!v.fatherList.isEmpty()){
            System.out.println(v.getKey());
            v = rg.getVertex(v.fatherList.removeFirst());
        }
    }

}
