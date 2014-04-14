package graphs;

public class Edge {

    private final String beginKey;
    private final String endKey;
    private int weight;
    private boolean active;

    public Edge(String begin, String end) {
        this.beginKey = begin;
        this.endKey = end;
        this.weight = 0;
        this.active = true;
    }

    public Edge(String begin, String end, int weight) {
        this.beginKey = begin;
        this.endKey = end;
        this.weight = weight;
        this.active = true;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public String getBeginKey() {
        return beginKey;
    }

    public String getEndKey() {
        return endKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }   
    
}
