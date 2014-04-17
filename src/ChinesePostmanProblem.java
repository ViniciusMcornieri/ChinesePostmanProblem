


import genetic.Chromossome;
import genetic.Genetic;
import greedy.Greedy;
import graphs.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ChinesePostmanProblem {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String path = args[0];
        WeightedGraphBuilder builder = new WeightedGraphBuilder(new File("instances\\basic5.txt"));
        SparseGraph sg = builder.build();
        /*
        Greedy alg = new Greedy(sg);        
        alg.perform();
        alg.printOut();**/
        Genetic alg = new Genetic(sg);
       // alg.perform();
        ArrayList<String> l = new ArrayList<>();
        l.add("a");
        l.add("b");
        l.add("g");
        l.add("b");
        l.add("g");
        l.add("f");
        l.add("d");
         l.add("g");
        l.add("f");
        l.add("d");
        l.add("a");
        Chromossome ch = new Chromossome(l, sg);
        //alg.printOut(output);
    }
    
}
