package algorithms;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import graphs.Edge;
import graphs.SparseGraph;
import java.util.ArrayList;

public abstract class CPP {

    public final SparseGraph sg;
    public ArrayList<String> output;

    public CPP(SparseGraph sg) {
        this.sg = sg;
        this.output = new ArrayList();
    }

    public abstract void perform();

    public void printOut() {
        int line = 0;
        for (String s : output) {
            System.out.print(s + "->");
            line++;
            if (line == 10) {
                System.out.println("");
                line = 0;
            }
        }
        System.out.println("custo do caminho: "+sg.calculateWayCost(output));
    }

}
