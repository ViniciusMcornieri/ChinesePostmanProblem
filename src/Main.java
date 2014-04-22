


import greedy.Greedy;
import genetic.Genetic;
import graphs.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import psr.PSR;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        WeightedGraphBuilder builder = new WeightedGraphBuilder(new File("./instances/g100"));
        SparseGraph sg = builder.build();
        
        PSR alg = new PSR(sg);
        //Greedy alg = new Greedy(sg);        
        //Genetic alg = new Genetic(sg, (float) 0.25, 30, 1);
        alg.perform();
        alg.printOut();
    }
    
}
