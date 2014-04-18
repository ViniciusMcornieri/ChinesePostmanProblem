package genetic;

import graphs.*;
import java.util.ArrayList;
import java.util.Collections;

public class Genetic {

    public SparseGraph sg;
    public float mutatePercent;
    public ArrayList<Chromossome> population;
    public int nPop;
    private long time;

    public Genetic(SparseGraph sg, float mutatePercent, int nPop) {
        this.sg = sg;
        this.population = new ArrayList();
        this.mutatePercent = mutatePercent;
        this.nPop = nPop;
        this.time =  System.currentTimeMillis();
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
            ArrayList<Couple> couples = this.selection();
            for (Couple couple : couples) {
                population.addAll(couple.getChildren(sg));
            }
            removeTwins();
            
        }       
    }

    public void removeTwins() {
        Collections.sort(population);
        for (int i = 0; i < population.size(); i++) {
            for (int j = i + 1; j < population.size(); j++) {
                Chromossome chromo1;
                Chromossome chromo2;
                chromo1 = population.get(i);
                chromo2 = population.get(j);
                if (compareChromos(chromo1, chromo2)) {
                    population.remove(j);
                }
            }
        }
    }

    public boolean compareChromos(Chromossome chromo1, Chromossome chromo2) {
        if (chromo1.getFitness() != chromo2.getFitness()
                || chromo1.missEdges.size() != chromo2.missEdges.size()) {
            return false;
        }
        for (int i = 0; i < chromo1.chromo.size(); i++) {
            if (!chromo1.chromo.get(i).equals(chromo2.chromo.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void printOut(ArrayList<String> output) {

        for (String s : output) {
            System.out.print(s + " ");
        }
        System.out.println("");
    }

}
