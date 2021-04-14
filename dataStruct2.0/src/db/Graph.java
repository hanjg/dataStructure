package db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: hjg
 * @Date: Create in 2018/3/23 16:46
 * @Description:
 */
public class Graph<E> {

    public static final double MAX_WEIGHT = Double.MAX_VALUE;
    /**
     * 存储节点的列表
     */
    private List<E> vertice;
    /**
     * 存储弧的矩阵
     */
    private double[][] arcs;
    /**
     * 弧的数量
     */
    private int numberOfArcs;

    private double[][] shortest = null;
    private List<List<List<Integer>>> shortestPath = null;


    private double[][] floyd() {
        FloydPath[][] floydPaths = new FloydPath[vertice.size()][vertice.size()];
        for (int i = 0; i < vertice.size(); i++) {
            for (int j = 0; j < vertice.size(); j++) {
                floydPaths[i][j] = new FloydPath(j, arcs[i][j]);
            }
        }
        //寻找i->k->j的距离比当前i->paths[i][j].via->j的距离短的k
        for (int k = 0; k < vertice.size(); k++) {
            for (int i = 0; i < vertice.size(); i++) {
                for (int j = 0; j < vertice.size(); j++) {
                    if (floydPaths[i][k].getDistance() + floydPaths[k][j].getDistance() < floydPaths[i][j]
                            .getDistance()) {
                        floydPaths[i][j].setVia(k);
                        floydPaths[i][j].setDistance(floydPaths[i][k].getDistance() + floydPaths[k][j].getDistance());
                    }
                }
            }
        }

        double[][] shortestDistance = new double[vertice.size()][vertice.size()];
        for (int i = 0; i < vertice.size(); i++) {
            for (int j = 0; j < vertice.size(); j++) {
                shortestDistance[i][j] = floydPaths[i][j].getDistance();
            }
        }

        List<List<List<Integer>>> res = new ArrayList();
        for (int i = 0; i < vertice.size(); i++) {
            List<List<Integer>> temp = new ArrayList<>();
            for (int j = 0; j < vertice.size(); j++) {
                List<Integer> path = new ArrayList<>();
                if (i == j) {
                    path.add(i);
                } else {
                    getPath(i, j, floydPaths, path);
                    if (path.size() == 0 && arcs[i][j] != MAX_WEIGHT) {
                        path.add(i);
                        path.add(j);
                    } else if (path.size() != 0) {
                        path.add(0, i);
                        path.add(j);
                    }

                }
                temp.add(path);
            }
            res.add(temp);
        }
        shortestPath = res;
        return shortestDistance;
    }

    /**
     * 将i->之间的路径记录在path中（不包括i，j）
     */
    private void getPath(int i, int j, FloydPath[][] paths, List<Integer> path) {
        if (paths[i][j].getVia() == j) {
            return;
        }
        getPath(i, paths[i][j].getVia(), paths, path);
        path.add(paths[i][j].getVia());
        getPath(paths[i][j].getVia(), j, paths, path);
    }

    /**
     * prim算法构造无向连通图的最小生成树，从indexs中的第一个节点开始构造。 每次寻找离生成树最近的节点加入生成树。
     *
     * @param indexs 需要加入生成树的点
     * @return 最小生成树的边
     */
    private Path[] prim(List<Integer> indexs) {
        Path[] minTree = new Path[indexs.size() - 1];
        //closestPaths[i]:未加入生成树的节点和已加入生成树的节点（包括路径上的节点）最近的一条路径的起始点,null表示已加入生成树
        //closestPaths与indexs一一对应
        Path[] closestPaths = new Path[indexs.size()];
        for (int i = 1; i < closestPaths.length; i++) {
            int from = indexs.get(0);
            int to = indexs.get(i);
            closestPaths[i] = new Path(from, to, shortest[from][to]);
        }

        for (int i = 0; i < minTree.length; i++) {
            //寻找距离生成树最近的节点
            int minVertex = -1;
            double minWeight = MAX_WEIGHT;
            for (int j = 1; j < closestPaths.length; j++) {
                if (closestPaths[j] == null) {
                    continue;
                }
                if (closestPaths[j].getWeight() < minWeight) {
                    minVertex = j;
                    minWeight = closestPaths[minVertex].getWeight();
                }
            }

            //非连通图
            if (minWeight == MAX_WEIGHT) {
                break;
            }

            //加入生成树
            minTree[i] = new Path(closestPaths[minVertex]);
            Path tempPath = closestPaths[minVertex];
            closestPaths[minVertex] = null;

            //更新最近信息
            for (int j = 0; j < closestPaths.length; j++) {
                if (closestPaths[j] == null) {
                    continue;
                }
                for (int k = 0; k <= i; k++) {
                    Path addedPath = minTree[i];
                    List<Integer> throughList = shortestPath.get(addedPath.getFrom()).get(addedPath.getTo());
                    for (Integer through : throughList) {
                        double recent = shortest[through][closestPaths[j].getTo()];
                        if (closestPaths[j].getWeight() > recent) {
                            closestPaths[j] = new Path(through, closestPaths[j].getTo(), recent);
                        }
                    }
                }

            }

        }
        return minTree;
    }

    /**
     * floyd算法求得连通图两两节点的最短路径，包括最短路径的线路和总长度。然后在图中使用改良版的最小生成树算法，无须计算整个图的最小生成树，只需计算必要节点在连通图中的最小生成树。
     *
     * @param indexs 需要知道路径的节点下标
     */
    public void start(List<Integer> indexs) {
        shortest = floyd();
       /* System.out.println("所有路径：");
        for (int i = 0; i < shortest.length; i++) {
            for (int j = 0; j < shortest[0].length; j++) {
                System.out.println(i + "->" + j + ": " + shortest[i][j] + "\t" + shortestPath.get(i).get(j));
            }
        }*/
        System.out.println("需要的路径：");
        Path[] paths = prim(indexs);
        for (Path path : paths) {
            System.out
                    .println(path.getFrom() + "->" + path.getTo() + shortestPath.get(path.getFrom()).get(path.getTo()));
        }
    }

    /**
     * @param vertice 节点
     * @param arcs 邻接矩阵
     */
    public Graph(List<E> vertice, double[][] arcs) {
        this.vertice = vertice;
        this.arcs = arcs;
        this.numberOfArcs = arcs.length;
    }


}
