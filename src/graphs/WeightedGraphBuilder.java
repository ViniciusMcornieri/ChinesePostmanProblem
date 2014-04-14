
package graphs;

import java.io.File;
import java.io.FileNotFoundException;


public class WeightedGraphBuilder extends SparseGraphBuilder{

    int weight;
    
    public WeightedGraphBuilder(File gf) throws FileNotFoundException {
        super(gf);
    }
    
    @Override
    public void buildEdges() {
        while (graphData.hasNext()) {
            nextBegin = graphData.next();
            nextEnd = graphData.next();
            weight =  graphData.nextInt();
            sg.addEdge(new Edge(nextBegin, nextEnd, weight));
        }
    }
    
}
