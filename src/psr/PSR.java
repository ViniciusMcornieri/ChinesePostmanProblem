package psr;

import graphs.*;

public class PSR {

    SparseGraph sg;
    SparseGraph restrictionsGraph;

    public PSR(SparseGraph sg) {
        this.sg = sg;
        this.restrictionsGraph = sg.buildRestrictionsGraph();
    }

   
}
