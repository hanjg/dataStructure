package db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: hjg
 * @Date: Create in 2018/3/23 19:53
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        //所有节点
        List<Integer> vertex = Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        //所有边
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 2, 1));
        edges.add(new Edge(2, 1, 1));
        edges.add(new Edge(1, 7, 1));
        edges.add(new Edge(0, 3, 1));
        edges.add(new Edge(3, 8, 1));
        edges.add(new Edge(8, 7, 1));
        edges.add(new Edge(3, 9, 1));
        edges.add(new Edge(0, 4, 1));
        edges.add(new Edge(4, 5, 1));
        edges.add(new Edge(5, 6, 1));
        edges.add(new Edge(6, 7, 1));

        //需要知道路径的节点下标
        List<Integer> select = Arrays.asList(new Integer[]{0, 7, 9});

        //初始化邻接矩阵
        double[][] arcs = new double[vertex.size()][vertex.size()];
        for (int i = 0; i < vertex.size(); i++) {
            for (int j = 0; j < vertex.size(); j++) {
                if (i != j) {
                    arcs[i][j] = Graph.MAX_WEIGHT;
                }
            }
        }
        for (Edge edge : edges) {
            int e1 = edge.getE1();
            int e2 = edge.getE2();
            arcs[e1][e2] = edge.getWeight();
            arcs[e2][e1] = edge.getWeight();
        }

        Graph<Character> graph = new Graph(vertex, arcs);
        graph.start(select);
    }
}
