package genetic;

import algorithms.CPP;
import graphs.*;
import java.util.ArrayList;
import java.util.Collections;

public class Genetic extends CPP {

    private final float MUTATE_PERCENT;
    private final int EXEC_TIME;
    private final int N_POP;
    private long START_TIME;
    private ArrayList<Chromossome> population;
    private int avg;
    private int iterationsSameAvg;

    public Genetic(SparseGraph sg, float mutatePercent, int nPop, int execTime) {
        super(sg);
        this.population = new ArrayList();
        this.MUTATE_PERCENT = mutatePercent;
        this.N_POP = nPop;
        this.avg = 0;
        this.iterationsSameAvg = 0;
        this.EXEC_TIME = execTime;
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
            couples.add(new Couple(dad, mom, MUTATE_PERCENT));
        }
        return couples;
    }

    public long actualTime() {
        long actual = System.currentTimeMillis();
        return ((actual - START_TIME) / 60000);//in minutes
    }

    private boolean stopCondicion() {
        if (actualTime() > EXEC_TIME
                && this.population.get(0).missEdges.isEmpty()) {
            return true;
        } else {
            int media = 0;
            for (int i = 0; i < (N_POP >> 1); i++) {
                media += population.get(i).getFitness();
            }
            media = media / (N_POP >> 1);
            if (this.avg == media) {
                this.iterationsSameAvg++;
                if (this.iterationsSameAvg >= N_POP * 10 && population.get(0).missEdges.isEmpty()) {
                    return true;
                }
            } else {
                this.avg = media;
                this.iterationsSameAvg = 0;
            }
        }
        return false;
    }

    @Override
    public void perform() {
        this.START_TIME = System.currentTimeMillis();
        this.populate(N_POP);
        ArrayList<Couple> couples;
        while (!stopCondicion()) {
            couples = this.selection();
            for (Couple couple : couples) {
                population.addAll(couple.getChildren(sg));
            }
            removeTwins();
            while (population.size() > N_POP) {
                population.remove(population.size() - 1);
            }
        }
        super.output = population.get(0).chromo;
        System.out.println(actualTime() + " mins!!");
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
        }else if(chromo1.chromo.size()!=chromo2.chromo.size()){
            return false;
        }
        for (int i = 0; i < chromo1.chromo.size(); i++) {
            if (!chromo1.chromo.get(i).equals(chromo2.chromo.get(i))) {
                return false;
            }
        }
        return true;
    }



}
