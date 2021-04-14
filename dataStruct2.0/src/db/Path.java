package db;

/**
 * @Author: hjg
 * @Date: Create in 2018/3/23 17:08
 * @Description:
 */
public class Path {

    private int from;
    private int to;
    private double weight;

    public Path(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Path(Path path) {
        this.from = path.from;
        this.to = path.to;
        this.weight = path.weight;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
