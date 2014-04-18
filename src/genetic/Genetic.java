package genetic;

import algorithms.BFS;
import graphs.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

    public SparseGraph sg;
    public float mutatePercent;
    public ArrayList<Chromossome> population;
    public int nPop;

    public Genetic(SparseGraph sg, float mutatePercent, int nPop) {
        this.sg = sg;
        this.population = new ArrayList();
        this.mutatePercent = mutatePercent;
        this.nPop = nPop;
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

    public void populate(int nChromo) {
        Chromossome chromo;
        for (int i = 0; i < nChromo; i++) {
            chromo = new Chromossome(sg);
            this.population.add(chromo);
        }
    }

    public ArrayList<Couple> selection() {
        Chromossome dad, mom;
        ArrayList<Couple> couples = new ArrayList();
        Collections.sort(population);
        for (int i = 0; i < (population.size() >> 1); i += 2) {
            dad = population.get(i);
            mom = population.get(i + 1);
            couples.add(new Couple(dad, mom, mutatePercent));
        }
        return couples;
    }

    public void perform() {
        this.populate(nPop);
        while (this.population.size() != 1) {

            //while(not cond parada)
            ArrayList<Couple> couples = this.selection();

            for (Couple couple : couples) {
                population.addAll(couple.getChildren(sg));
            }
            removeTwins();
        }
        System.out.println("");
        //this.crossover();
        //this.mutate();        
    }

    public void removeTwins() {
        Collections.sort(population);
        for (int i = 0; i < population.size(); i++) {
            for (int j = i + 1; j < population.size(); j++) {
                Chromossome chromo1;
                Chromossome chromo2;
                chromo1 = population.get(i);
                chromo2 = population.get(j);
                if (compareMissEdges(chromo1, chromo2)) {
                    population.remove(j);
                }
            }
        }
    }

    public boolean compareMissEdges(Chromossome chromo1, Chromossome chromo2) {
        int quantity = 0;
        if (chromo1.getFitness() == chromo2.getFitness()
                && chromo1.missEdges.size() == chromo2.missEdges.size()) {

            for (Edge edge1 : chromo1.missEdges) {
                for (Edge edge2 : chromo2.missEdges) {
                    if (edge1.equals(edge2)) {
                        quantity++;
                    }
                }
            }
        }
        return chromo1.missEdges.size() == quantity;
    }

    public void printOut(ArrayList<String> output) {

        for (String s : output) {
            System.out.print(s + " ");
        }
        System.out.println("");
    }

}
