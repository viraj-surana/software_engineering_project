package com.viraj;
import edu.princeton.cs.algs4.*;
import java.util.Arrays;
import java.util.Scanner;
public class kruskal {
    private static final double FLOATING_POINT_EPSILON = 1.0E-12;

    private double weight;
    private Queue<Edge> mst = new Queue<Edge>();

    public kruskal(EdgeWeightedGraph G) {

        Edge[] edges = new Edge[G.E()];
        int t = 0;
        for (Edge e : G.edges()) {
            edges[t++] = e;
        }
        Arrays.sort(edges);

        UF uf = new UF(G.V());
        for (int i = 0; i < G.E() && mst.size() < G.V() - 1; i++) {
            Edge e = edges[i];
            int v = e.either();
            int w = e.other(v);

            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
            }
        }

        assert check(G);
    }


    public Iterable<Edge> edges() {
        return mst;
    }


    public double weight() {
        return weight;
    }

    private boolean check(EdgeWeightedGraph G) {

        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
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
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

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
        float []a = new float [4] ;
        Scanner sc = new Scanner(System.in) ;
        int l = sc.nextInt();
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
                KruskalMST mst = new KruskalMST(G);
                for (Edge e : mst.edges()) {
                    StdOut.println(e);
                }
                StdOut.printf("%.5f\n", mst.weight());
                a[i+1] = (float) mst.weight();
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
                KruskalMST mst = new KruskalMST(G);
                for (Edge e : mst.edges()) {
                    StdOut.println(e);
                }
                StdOut.printf("%.5f\n\n", mst.weight());
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