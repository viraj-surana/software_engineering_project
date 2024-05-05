package com.viraj;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import edu.princeton.cs.algs4.*;
import java.util.Scanner;

public class floydwarshallSP {
    private boolean hasNegativeCycle;  // is there a negative cycle?
    private double[][] distTo;         // distTo[v][w] = length of shortest v->w path
    private DirectedEdge[][] edgeTo;   // edgeTo[v][w] = last edge on shortest v->w path

    public floydwarshallSP(EdgeWeightedDigraph G) {
        int V = G.V();
        distTo = new double[V][V];
        edgeTo = new DirectedEdge[V][V];

        // initialize distances to infinity
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // initialize distances using edge-weighted digraph's
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                distTo[e.from()][e.to()] = e.weight();
                edgeTo[e.from()][e.to()] = e;
            }
            // in case of self-loops
            if (distTo[v][v] >= 0.0) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // Floyd-Warshall updates
        for (int i = 0; i < V; i++) {
            for (int v = 0; v < V; v++) {
                for (int w = 0; w < V; w++) {
                    if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                // check for negative cycle
                if (distTo[v][v] < 0.0) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
        assert check(G);
    }

    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        for (int v = 0; v < distTo.length; v++) {
            if (distTo[v][v] < 0.0) {
                int V = edgeTo.length;
                EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
                for (int w = 0; w < V; w++)
                    if (edgeTo[v][w] != null)
                        spt.addEdge(edgeTo[v][w]);
                EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
                assert finder.hasCycle();
                return finder.cycle();
            }
        }
        return null;
    }

    public boolean hasPath(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return distTo[s][t] < Double.POSITIVE_INFINITY;
    }

    public double dist(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[s][t];
    }

    public Iterable<DirectedEdge> path(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPath(s, t)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[s][t]; e != null; e = edgeTo[s][e.from()]) {
            path.push(e);
        }
        return path;
    }

    private boolean check(EdgeWeightedDigraph G) {
        if (!hasNegativeCycle()) {
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    for (int i = 0; i < G.V(); i++) {
                        if (distTo[i][w] > distTo[i][v] + e.weight()) {
                            System.err.println("edge " + e + " is eligible");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void validateVertex(int v) {
        int V = distTo.length;
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
                EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

                floydwarshallSP sp = new floydwarshallSP(G);

                if (sp.hasNegativeCycle()) {
                    for (DirectedEdge e : sp.negativeCycle())
                        StdOut.println(e);
                } else {
                    for (int v = 0; v < G.V(); v++) {
                        if (sp.hasPath(0, v)) {
                            StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.dist(0, v));
                            for (DirectedEdge e : sp.path(0, v)) {
                                StdOut.print(e + "   ");
                            }
                            StdOut.println();
                        } else {
                            StdOut.printf("%d to %d           no path\n", s, v);
                        }
                    }
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
                EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

                floydwarshallSP sp = new floydwarshallSP(G);

                // Print shortest paths
                for (int v = 0; v < G.V(); v++) {
                    for (int w = 0; w < G.V(); w++) {
                        if (sp.hasPath(v, w)) {
                            StdOut.printf("%d to %d (%5.2f)  ", v, w, sp.dist(v, w));
                            for (DirectedEdge e : sp.path(v, w))
                                StdOut.print(e + "  ");
                            StdOut.println();
                        } else {
                            StdOut.printf("%d to %d no path\n", v, w);
                        }
                    }
                }

                // Print negative cycle
                if (sp.hasNegativeCycle()) {
                    StdOut.println("Negative cost cycle:");
                    for (DirectedEdge e : sp.negativeCycle())
                        StdOut.println(e);
                }
            }
        }
    }
}
