package genetic;

import graphs.*;
import java.util.ArrayList;
import java.util.Collections;

public class Genetic {

    private final SparseGraph sg;
    private final float MUTATE_PERCENT;
    private ArrayList<Chromossome> population;
    private final int N_POP;
    private final long START_TIME;

    public Genetic(SparseGraph sg, float mutatePercent, int nPop) {
        this.sg = sg;
        this.population = new ArrayList();
        this.MUTATE_PERCENT = mutatePercent;
        this.N_POP = nPop;
        this.START_TIME = System.currentTimeMillis();
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
       try {
            Collections.sort(population);
        } catch (Exception e) {
            System.out.println("Selection Deu esse pau no role da ordenação--->" + e.getMessage());
        }
        for (int i = 0; i < (population.size() >> 1); i += 2) {
            dad = population.get(i);
            mom = population.get(i + 1);
            couples.add(new Couple(dad, mom, MUTATE_PERCENT));
        }
        return couples;
    }

    public long actualTime() {
        long actual = System.currentTimeMillis();
        return ((actual - START_TIME) / 60000);//in minutes
    }

    private boolean stopCondicion(int time) {
        try {
            Collections.sort(population);
        } catch (Exception e) {
            System.out.println("StopCondicion Deu esse pau no role da ordenação--->" + e.getMessage());
        }
        if (actualTime() > time 
                && this.population.get(0).missEdges.isEmpty()) {
            return true;
        } else {
            int media = 0;
            for (int i = 0; i < 5; i++) {
                media += population.get(i).getFitness();
            }
            media = media / 5;
            if (media == population.get(0).getFitness() 
                    && this.population.get(0).missEdges.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void perform() {
        this.populate(N_POP);
        ArrayList<Couple> couples;
        while (!stopCondicion(1)) {
            couples = this.selection();
            for (Couple couple : couples) {
                population.addAll(couple.getChildren(sg));
            }
            System.out.println("");
            removeTwins();
            Collections.sort(population);
            while (population.size() > N_POP) {
                population.remove(population.size() - 1);
            }
        }
        System.out.println(actualTime() + " mins!!");
        System.out.println("");
    }

    public void removeTwins() {
        try {
            Collections.sort(population);
        } catch (Exception e) {
            System.out.println("removeTwins Deu esse pau no role da ordenação--->" + e.getMessage());
        }
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
