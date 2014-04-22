package genetic;

import algorithms.BFS;
import graphs.*;
import java.util.ArrayList;
import java.util.Random;

public final class Chromossome implements Comparable<Chromossome> {

    private int costWay;
    private final int fitness;
    public ArrayList<Edge> missEdges;
    public ArrayList<String> chromo;

    public Chromossome(SparseGraph sg) {
        this.chromo = this.generateChromo(sg);
        this.costWay = sg.calculateWayCost(chromo);
        this.missEdges = sg.calculateMissEdges();
        //removeRedundancy()
        this.fitness = fitness();
    }

    public Chromossome(ArrayList<String> chromo, SparseGraph sg, float percent) {
        this.chromo = chromo;
        this.costWay = sg.calculateWayCost(chromo);
        this.missEdges = sg.calculateMissEdges();
        removeRedundancy();
        if (percent * 100 > new Random().nextInt(100)) {
            mutate();
            this.costWay = sg.calculateWayCost(chromo);
            this.missEdges = sg.calculateMissEdges();

        }
        this.fitness = fitness();
    }

    private void mutate() {
        Random rd = new Random();
        int size = missEdges.size();
        if (size != 0) {
            Edge edge = missEdges.get(rd.nextInt(size));
            int i = 0;
            boolean stop = false;
            while (!stop) {
                if (i == chromo.size()) {
                    stop = true;
                } else if (chromo.get(i).equals(edge.getBeginKey())) {
                    stop = true;
                    chromo.add(i, edge.getEndKey());
                    chromo.add(i, edge.getBeginKey());
                } else if (chromo.get(i).equals(edge.getEndKey())) {
                    stop = true;
                    chromo.add(i, edge.getBeginKey());
                    chromo.add(i, edge.getEndKey());
                }
                i++;
            }
        }

    }

    public int getIndex(String key) {
        for (int i = 0; i < chromo.size(); i++) {
            if (chromo.get(i).equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public int getFitness() {
        return fitness;
    }

    private int fitness() {
        int ft = 0;
        ft = (this.missEdges.size() + 1) * this.costWay;
        return ft;
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
        int comparePoint;
        for (int j = 0; j < chromo.size(); j++) {
            firstList = new ArrayList();
            compareList = new ArrayList();
            notRedundancy = new ArrayList();
            comparePoint = 0;
            notRedundancy.addAll(chromo.subList(0, j));
            for (int i = j; i < chromo.size(); i++) {
                if (firstList.isEmpty()) {
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
        if (this.fitness < chromo.fitness) {
            return -1;
        } else if (this.fitness > chromo.fitness) {
            return 1;
        } else {
            return 0;

        }
    }

}
