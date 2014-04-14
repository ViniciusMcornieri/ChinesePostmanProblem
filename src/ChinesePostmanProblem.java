


import algorithms.Greedy;
import graphs.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ChinesePostmanProblem {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String path = args[0];
        WeightedGraphBuilder builder = new WeightedGraphBuilder(new File("instances\\basic5.txt"));
        SparseGraph sg = builder.build();
        Greedy alg = new Greedy(sg);
        alg.perform();
        alg.printOut();
    }
    
}
