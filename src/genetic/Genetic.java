package genetic;

import algorithms.BFS;
import graphs.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

    public SparseGraph sg;
    public ArrayList<Chromossome> population;

    public Genetic(SparseGraph sg) {
        this.sg = sg;
        this.population = new ArrayList();
    }

    private void invalidate(String beginKey, String endKey) {
        Edge ab = null;
        for (Edge edge : sg.getAdj(beginKey)) {
            if (edge.getEndKey().equals(endKey)) {
                ab = edge;
            }
        }
        if (ab.isActive()) {
            ab.setActive(false);
            for (Edge ba : sg.getAdj(ab.getEndKey())) {
                if (ba.getEndKey().equals(ab.getBeginKey())) {
                    ba.setActive(false);
                }
            }
        }
    }
    
    public void populate(int nChromo){
        Chromossome chromo; 
        for (int i = 0; i < nChromo; i++) {
            chromo = new Chromossome(sg);
            this.population.add(chromo);
        }    
    }
    
    public ArrayList<Couple> selection(){
        Chromossome dad, mom;
        ArrayList<Couple> couples = new ArrayList();
        Collections.sort(population);
        for (int i = 0; i < (population.size() >> 1); i+=2) {
            dad = population.get(i);
            mom = population.get(i+1);
            couples.add(new Couple(dad, mom));
        }
        return couples;
    }

    public ArrayList<String> generateChromo() {
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
        ArrayList<String> chromo = randomVisit(e.getBeginKey());
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

    private ArrayList<String> randomVisit(String startKey) {
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
                invalidate(key, adj);
                key = adj;                
            } else {
                stop = true;
            }
        }      //rd.setSeed(17);
        return route;
    }
    
    public void perform(){
        this.populate(30);
        //while(not cond parada)
        this.selection();
        //this.crossover();
        //this.mutate();        
    }

    public void printOut(ArrayList<String> output) {

        for (String s : output) {
            System.out.print(s + " ");
        }
        System.out.println("");
    }

}
