


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
        WeightedGraphBuilder builder = new WeightedGraphBuilder(new File("./instances/basic5.txt"));
        SparseGraph sg = builder.build();
        /*
        Greedy alg = new Greedy(sg);        
        alg.perform();
        alg.printOut();**/
        Genetic alg = new Genetic(sg, (float) 0.25, 30);
        alg.perform();
        //alg.printOut(output);
    }
    
}
