package genetic;

import algorithms.BFS;
import graphs.*;
import java.util.ArrayList;
import java.util.Random;

public final class Chromossome implements Comparable<Chromossome> {

    private int costWay;
    private int fitness;
    private ArrayList<Edge> missEdges;
    private ArrayList<String> chromo;

    public Chromossome(SparseGraph sg) {
        this.chromo = this.generateChromo(sg);
        this.costWay = 0;
        this.missEdges = new ArrayList<>();
        removeRedundancy();
        calculateCostWayMissEdges(sg);
        this.fitness = fitness();
    }

    public Chromossome(ArrayList<String> chromo, SparseGraph sg) {
        this.chromo = chromo;
        this.costWay = 0;
        this.missEdges = new ArrayList<>();
        removeRedundancy();
        calculateCostWayMissEdges(sg);
        this.fitness = fitness();
    }

    public int getFitness() {
        return fitness;
    }

    private int fitness() {
        int ft = 0;
        ft = (this.missEdges.size() + 1) * this.costWay;
        return ft;
    }

    private void calculateCostWayMissEdges(SparseGraph sg) {
        String a, b;
        Edge e;
        int way = 0;

        for (String s : sg.getKeys()) {
            for (Edge edge : sg.getAdj(s)) {
                edge.setActive(true);
            }
        }

        for (int i = 0; i < chromo.size() - 1; i++) {
            a = chromo.get(i);
            b = chromo.get(i + 1);
            e = sg.edgeAdj(a, b);
            sg.invalidate(a, b);
            way += e.getWeight();
        }
        this.costWay = way;

        for (String s : sg.getKeys()) {
            for (Edge edge : sg.getAdj(s)) {
                if (edge.isActive()) {
                    missEdges.add(edge);
                }
            }
        }
    }

    public ArrayList<String> generateChromo(SparseGraph sg) {
        boolean found;
        String father;
        BFS bfs = new BFS(sg);
        Random rd = new Random();
        //rd.setSeed(17);
        for (String key : sg.getKeys()) {
            for (Edge edge : sg.getAdj(key)) {
                edge.setActive(true);
            }
        }
        int size = sg.edgeSet.size();
        Edge e = sg.edgeSet.get(rd.nextInt(size));
        ArrayList<String> chromo = randomVisit(e.getBeginKey(), sg);
        String first = chromo.get(0);
        String last = chromo.get(chromo.size() - 1);
        if (!first.equals(last)) {
            sg.startKey = first;
            bfs.perform();
            found = false;
            father = last;
            while (!found) {
                father = sg.getVertex(father).father;
                chromo.add(father);
                if (father.equals(first)) {
                    found = true;
                }
            }
        }
        return chromo;
    }

    private ArrayList<String> randomVisit(String startKey, SparseGraph sg) {
        ArrayList<String> route = new ArrayList();
        ArrayList<String> valides;
        int id;
        String adj, key;
        key = startKey;
        Random rd = new Random();
        //rd.setSeed(17);
        boolean stop = false;
        while (!stop) {
            valides = new ArrayList();
            route.add(key);
            for (Edge edge : sg.getAdj(key)) {
                if (edge.isActive()) {
                    valides.add(edge.getEndKey());
                }
            }
            if (!valides.isEmpty()) {
                id = rd.nextInt(valides.size());
                adj = valides.get(id);
                sg.invalidate(key, adj);
                key = adj;
            } else {
                stop = true;
            }
        }      //rd.setSeed(17);
        return route;
    }

    public void removeRedundancy() {
        ArrayList<String> firstList;
        ArrayList<String> compareList;
        ArrayList<String> notRedundancy;
        int comparePoint = 0;
        for (int j = 0; j < chromo.size(); j++) {
            firstList = new ArrayList();
            compareList = new ArrayList();
            notRedundancy = new ArrayList();
            comparePoint = 0;
            notRedundancy.addAll(chromo.subList(0, j));
            for (int i = j; i < chromo.size(); i++) {
                if (firstList.size() == 0) {
                    firstList.add(chromo.get(i));
                } else if (chromo.get(i).equals(firstList.get(comparePoint))) {
                    compareList.add(chromo.get(i));
                    comparePoint++;
                    if (firstList.size() == compareList.size()) {
                        notRedundancy.addAll(firstList);
                        firstList = new ArrayList();
                        compareList = new ArrayList();
                        comparePoint = 0;
                    }
                } else if (comparePoint != 0) {
                    firstList.addAll(compareList);
                    firstList.add(chromo.get(i));
                    compareList = new ArrayList();
                    comparePoint = 0;
                } else {
                    firstList.add(chromo.get(i));
                }
            }
            firstList.addAll(compareList);
            notRedundancy.addAll(firstList);
            chromo = notRedundancy;
        }
    }

    @Override
    public int compareTo(Chromossome chromo) {
        if (this.fitness <= chromo.fitness) {
            return -1;
        }
        return 1;
    }

}
