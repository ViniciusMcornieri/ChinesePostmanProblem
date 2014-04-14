package graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SparseGraphBuilder {

    SparseGraph sg;
    Scanner graphData;
    String nextBegin, nextEnd;

    public SparseGraphBuilder(File gf) throws FileNotFoundException {
        sg = new SparseGraph();
        graphData = new Scanner(gf);
    }

    public void buildVerticies() {
        if (graphData.hasNext()) {
            nextBegin = graphData.next();
            sg.startKey = nextBegin;
            sg.addVertex(new Vertex(nextBegin));
        }
        boolean notSharp = true;
        while (graphData.hasNext() && notSharp) {
            nextBegin = graphData.next();
            notSharp = (nextBegin.charAt(0) != '#');
            if (notSharp) {
                sg.addVertex(new Vertex(nextBegin));
            }
        }

    }

    public void buildEdges() {
        while (graphData.hasNext()) {
            nextBegin = graphData.next();
            nextEnd = graphData.next();
            sg.addEdge(new Edge(nextBegin, nextEnd));
        }
    }

    public SparseGraph build() throws FileNotFoundException, IOException {
        buildVerticies();
        buildEdges();
        graphData.close();
        return sg;
    }
}
