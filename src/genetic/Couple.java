package genetic;

import graphs.Edge;
import graphs.SparseGraph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Couple {

    private final Chromossome dad;
    private final Chromossome mom;
    private ArrayList<Chromossome> children;
    private float percent;

    public Couple(Chromossome dad, Chromossome mom, float percent) {
        this.dad = dad;
        this.mom = mom;
        children = null;
        this.percent = percent;
    }

    public ArrayList<Chromossome> getChildren(SparseGraph sg) {
        if (children == null) {
            children = new ArrayList();
            crossover(sg);
        }
        return children;
    }
    

    private int getRandomIndex(String key, int limit, SparseGraph sg) {
        Random r = new Random();
        Edge edgeRandom;
        int randomIndex = -1, adjRandom;
        for (int i = 0; randomIndex == -1 && i < limit; i++) {
            adjRandom = r.nextInt(sg.getAdj(key).size());
            edgeRandom = sg.getAdj(key).get(adjRandom);
            randomIndex = mom.getIndex(edgeRandom.getEndKey());
        }
        return randomIndex;
    }

    private ArrayList subChromo(ArrayList<String> chromo, int a0, int a1) {
        ArrayList sub;
        sub = new ArrayList(chromo.subList(a0, a1));

        return sub;

    }

    private void crossover(SparseGraph sg) {
        Random r = new Random();
        String key0, key1;
        int i0, i1, k;
        ArrayList<String> subi, subj, son1, son2, rsubi, rsubj;
        int j0 = -1;
        int j1 = -1;
        //  for (int i = 0; i < (j0 == -1 || j1 == -1)  &&  dad.chromo.size()>3 && mom.chromo.size()>3 ; i++) {

        do {
            i0 = 1 + r.nextInt(dad.chromo.size() - 2);
            i1 = 1 + r.nextInt(dad.chromo.size() - 2);

            if (i0 > i1) {
                k = i0;
                i0 = i1;
                i1 = k;
            }
            key0 = dad.chromo.get(i0);
            key1 = dad.chromo.get(i1);

            j0 = getRandomIndex(key0, mom.chromo.size(), sg);
            j1 = getRandomIndex(key1, mom.chromo.size(), sg);
        } while ((i0 - i1 <= 1
                && i0 - i1 >= -1)
                || (j0 == j1
                || j0 == mom.chromo.size() - 1
                || j1 == mom.chromo.size() - 1));

//}
        if (j0 != -1 && j1 != -1) {
            subi = subChromo(dad.chromo, i0, i1 + 1);

            if (j0 < j1) {
                subj = subChromo(mom.chromo, j0, j1 + 1);

                son1 = new ArrayList(dad.chromo.subList(0, i0 + 1));
                son1.addAll(subj);
                son1.addAll(dad.chromo.subList(i1, dad.chromo.size()));

                son2 = new ArrayList(mom.chromo.subList(0, j0 + 1));
                son2.addAll(subi);
                son2.addAll(mom.chromo.subList(j1, mom.chromo.size()));

            } else {
                subj = subChromo(mom.chromo, j1, j0 + 1);

                rsubi = new ArrayList(subi);
                Collections.reverse(rsubi);
                rsubj = new ArrayList(subj);
                Collections.reverse(rsubj);

                son1 = new ArrayList(dad.chromo.subList(0, i0 + 1));
                son1.addAll(rsubj);
                son1.addAll(dad.chromo.subList(i1, dad.chromo.size()));

                son2 = new ArrayList(mom.chromo.subList(0, j1 + 1));
                son2.addAll(rsubi);
                son2.addAll(mom.chromo.subList(j0, mom.chromo.size()));

            }
            children.add(new Chromossome(son1, sg, percent));
            children.add(new Chromossome(son2, sg, percent));
        }
    }
}
