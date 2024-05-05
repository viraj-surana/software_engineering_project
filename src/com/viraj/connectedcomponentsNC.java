package com.viraj;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;
import java.util.Scanner;
import edu.princeton.cs.algs4.*;

public class connectedcomponentsNC {
    private boolean[] marked; // marked[v] = has vertex v been marked?
    private int[] id;         // id[v] = id of connected component containing v
    private int count;        // number of connected components

    public connectedcomponentsNC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    public int count() {
        return count;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        String input;
        int s;
        if (l == 1) {
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.print("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem1.txt";
                    s = 3;
                    System.out.println();
                } else if (i == 1) {
                    System.out.println("Graph2:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem2.txt";
                    s = 1;
                } else {
                    System.out.println("Graph3:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem3.txt";
                    s = 1;
                }
                In in = new In(input);
                Graph G = new Graph(in);

                connectedcomponentsNC cc = new connectedcomponentsNC(G);

                StdOut.println("Number of connected components: " + cc.count());
                for (int v = 0; v < G.V(); v++) {
                    StdOut.println("Vertex " + v + " is in component " + cc.id(v));
                }
            }
        } else if (l == 2) {
            for (int i = 0; i <= 3; i++) {
                if (i == 0) {
                    System.out.println("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list.txt";
                    s = 0;
                } else if (i == 1) {
                    System.out.print("Graph2:");
                    System.out.println();
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list_2.txt";
                    s = 0;
                } else if (i == 2) {
                    System.out.print("Graph3:");
                    System.out.println();
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list_3.txt";
                    s = 0;
                } else {
                    System.out.print("Graph4:");
                    System.out.println();
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\collaboration_network_list.txt";
                    s = 2034;
                }
                In in = new In(input);
                Graph G = new Graph(in);

                connectedcomponentsNC cc = new connectedcomponentsNC(G);

                StdOut.println("Number of connected components: " + cc.count());
                for (int v = 0; v < G.V(); v++) {
                    StdOut.println("Vertex " + v + " is in component " + cc.id(v));
                }
            }
        }
    }
}
