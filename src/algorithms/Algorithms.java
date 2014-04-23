package algorithms;

import genetic.Genetic;
import graphs.SparseGraph;
import graphs.WeightedGraphBuilder;
import greedy.Greedy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import psr.PSR;

public enum Algorithms {
    
    greedy{

        @Override
        public void run(String path, float mutatePercent, int nPop, int execTime) throws FileNotFoundException, IOException{
            WeightedGraphBuilder builder;
            builder = new WeightedGraphBuilder(new File(path));
            SparseGraph sg = builder.build();
            Greedy g = new Greedy(sg);
            g.perform();
            g.printOut();
        }
        
    }, genetic{

        @Override
        public void run(String path, float mutatePercent, int nPop, int execTime) throws FileNotFoundException, IOException {
            WeightedGraphBuilder builder;
            builder = new WeightedGraphBuilder(new File(path));
            SparseGraph sg = builder.build();
            Genetic ge = new Genetic(sg, mutatePercent, nPop, execTime);
            ge.perform();
            ge.printOut();
        }
        
    }, psr{

        @Override
        public void run(String path, float mutatePercent, int nPop, int execTime) throws FileNotFoundException, IOException {
            WeightedGraphBuilder builder;
            builder = new WeightedGraphBuilder(new File(path));
            SparseGraph sg = builder.build();
            PSR psr = new PSR(sg);
            psr.perform();
            psr.printOut();

        }
    };
    
    public abstract void run(String path, float mutatePercent, int nPop, int execTime) throws FileNotFoundException, IOException;  
}
