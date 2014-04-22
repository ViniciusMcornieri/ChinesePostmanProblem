package psr;

import algorithms.CPP;
import graphs.*;

public class PSR extends CPP {

    SparseGraph sg;
    SparseGraph rg;
    private final boolean WHITE = true;
    private final boolean NOT_WHITE = false;
    Vertex x;

    public PSR(SparseGraph sg) {
        super(sg);
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

    public void visit(Vertex first) {
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
                first.fatherList.addFirst(father.getKey());
                loop = false;
            } else {
                next = getBestAdj(father.getKey(), NOT_WHITE);
                next.fatherList.addFirst(father.getKey());
                --father.heuristic;
                --next.heuristic;
                father = next;
            }

        } while (loop);
    }

    @Override
    public void perform() {
        init();
        x = getBest();
        visit(x);
        buildOutput();
    }

    public void buildOutput() {
        Vertex v = x;
        String f;
        String first;
        String vertex = sameVertexInThisEdges(v.getKey(), v.fatherList.getFirst());
        String str[] = v.getKey().split(" ");
        if (str[0].equals(vertex)) {
            first = str[1];
            output.add(first);
        } else {
            first = str[0];
            output.add(first);
        }
        do {

            System.out.println(v.getKey());
            f = v.fatherList.removeFirst();
            vertex = sameVertexInThisEdges(v.getKey(), f);
            str = v.getKey().split(" ");
            if (output.get(output.size() - 1).equals(vertex)) {
                if (str[0].equals(vertex)) {
                    output.add(str[1]);
                    output.add(str[0]);

                } else {
                    output.add(str[0]);
                    output.add(str[1]);
                }
            } else {
                output.add(vertex);
            }

            v = rg.getVertex(f);
        } while (!v.fatherList.isEmpty());
        output.add(first);
    }

    public String sameVertexInThisEdges(String v, String u) {
        for (String strV : v.split(" ")) {
            for (String strU : u.split(" ")) {
                if (strV.equals(strU) && !strV.equals(" ")) {
                    return strV;
                }
            }
        }
        return null;
    }

}
