package com.viraj;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import edu.princeton.cs.algs4.*;
import java.util.Arrays;
import java.util.Scanner;

public class prim {
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;

    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;


    public prim(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);

        assert check(G);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }


    private boolean check(EdgeWeightedGraph G) {

        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        for (Edge e : edges()) {

            uf = new UF(G.V());
            for (Edge f : edges()) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    public static void main(String[] args) {
        int i;
        Scanner sc = new Scanner(System.in);
        int l  = sc.nextInt();
        float []a = new float [4];
        String input = null;
        if(l==1) {
            for (i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.println("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem1.txt";
                }
                if (i == 1) {
                    System.out.println();
                    System.out.println("Graph2:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem2.txt";
                }
                if (i == 2) {
                    System.out.println();
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem3.txt";
                    System.out.println("Graph3:");
                }

                In in = new In(input);

                EdgeWeightedGraph G = new EdgeWeightedGraph(in);
                PrimMST mst = new PrimMST(G);
                for (Edge e : mst.edges()) {
                    StdOut.println(e);
                }
                StdOut.printf("%.5f\n", mst.weight());
                a[i] = (float) mst.weight();
            }
        }
        if(l==2) {
            for (i = 0; i <= 3; i++)
            {
                if (i == 0) {
                    System.out.println("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list.txt";
                    System.out.println();
                }
                if (i == 1) {
                    System.out.println("Graph2:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list_2.txt";
                    System.out.println();
                }
                if (i == 2) {
                    System.out.println("Graph3:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list_3.txt";
                    System.out.println();
                }
                if (i == 3) {
                    System.out.println("Graph4:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\collaboration_network_list.txt";
                }

                In in = new In(input);

                EdgeWeightedGraph G = new EdgeWeightedGraph(in);
                PrimMST mst = new PrimMST(G);
                for (Edge e : mst.edges()) {
                    StdOut.println(e);
                }
                StdOut.printf("%.5f\n", mst.weight());
                a[i] = (float) mst.weight();
            }
        }
        Arrays.sort(a);
        System.out.println();
        if(l==1) {
            System.out.print("Mean = ");
            System.out.println((a[1] + a[2] + a[3]) / 3);
            System.out.print("Median = ");
            System.out.println(a[2]);
            System.out.print("Minimum = ");
            System.out.println(a[1]);
            System.out.print("Maximum = ");
            System.out.print(a[3]);
        }
        else
        {
            System.out.print("Mean = ");
            System.out.println((a[0] + a[1] + a[2] + a[3] )/ 4);
            System.out.print("Median = ");
            System.out.println((a[1]+a[2])/2);
            System.out.print("Minimum = ");
            System.out.println(a[0]);
            System.out.print("Maximum = ");
            System.out.print(a[3]);
        }
    }
}
