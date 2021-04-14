package db;

/**
 * @Author: hjg
 * @Date: Create in 2018/3/23 19:40
 * @Description:
 */
public class Edge {

    private int e1;
    private int e2;
    private double weight;

    public Edge(int e1, int e2, double weight) {
        this.e1 = e1;
        this.e2 = e2;
        this.weight = weight;
    }

    public int getE1() {
        return e1;
    }

    public int getE2() {
        return e2;
    }

    public double getWeight() {
        return weight;
    }
}
