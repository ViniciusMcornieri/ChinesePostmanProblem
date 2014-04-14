package algorithms;

import graphs.Edge;
import graphs.SparseGraph;
import graphs.Vertex;

public abstract class Relaxation {
    
    public SparseGraph sg;
    
    void init(){
       for(Vertex v: sg.getVertexSet()){
           v.distance = Integer.MAX_VALUE;
           v.father = null;
       }
       sg.getStartVertex().distance = 0;
    }
    
    void relax(Vertex u, Vertex v, Edge e){
        int sum;
        sum = u.distance + e.getWeight();
        if(u.distance!=Integer.MAX_VALUE  && v.distance > sum){
            v.distance = sum;
            v.father = u.getKey();
        }
    }
    
   
    
    
    public void printOut() {
        for (Vertex v : sg.getVertexSet()) {
            if (v.distance != Integer.MAX_VALUE) {
                printOutVisit(v);
                System.out.println(v.distance);
            }
        }
    }

    void printOutVisit(Vertex v) {
        if (v.father == null) {
            System.out.print(v.getKey() + " ");
        } else {
            printOutVisit(sg.getVertex(v.father));
            System.out.print(v.getKey() + " ");
        }
    }

}
