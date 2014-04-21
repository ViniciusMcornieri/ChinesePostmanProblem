


import greedy.Greedy;
import genetic.Genetic;
import graphs.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import psr.PSR;

public class ChinesePostmanProblem {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        WeightedGraphBuilder builder = new WeightedGraphBuilder(new File("./instances/basic5.txt"));
        SparseGraph sg = builder.build();
        
        PSR alg = new PSR(sg);

        //Greedy alg = new Greedy(sg);        
        //alg.perform();
        //alg.printOut();
        //Genetic alg = new Genetic(sg, (float) 0.25, 30);
        //alg.perform(120);
        //alg.printOut(output);
    }
    
}
