package db;

/**
 * @Author: hjg
 * @Date: Create in 2018/3/23 17:04
 * @Description:
 */
public class FloydPath {


    private int via;
    private double distance;

    /**
     * @param via i->j需要经过的节点下标
     * @param distance i->j的距离
     */
    public FloydPath(int via, double distance) {
        this.via = via;
        this.distance = distance;
    }

    public int getVia() {
        return via;
    }

    public double getDistance() {
        return distance;
    }

    public void setVia(int via) {
        this.via = via;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
