package com.viraj;
import edu.princeton.cs.algs4.*;

import java.util.Scanner;

public class bellmanford {
    private static final double EPSILON = 1E-14;

    private double[] distTo;               // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;         // edgeTo[v] = last edge on shortest s->v path
    private boolean[] onQueue;             // onQueue[v] = is v currently on the queue?
    private Queue<Integer> queue;          // queue of vertices to relax
    private int cost;                      // number of calls to relax()
    private Iterable<DirectedEdge> cycle;  // negative cycle (or null if no such cycle)


    public bellmanford(EdgeWeightedDigraph G, int s) {
        distTo  = new double[G.V()];
        edgeTo  = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;


        queue = new Queue<Integer>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }

        assert check(G, s);
    }


    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight() + EPSILON) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (++cost % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return;  // found a negative cycle
            }
        }
    }


    public boolean hasNegativeCycle() {
        return cycle != null;
    }


    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    // by finding a cycle in predecessor graph
    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);

        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        cycle = finder.cycle();
    }


    public double distTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[v];
    }


    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }


    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }


    private boolean check(EdgeWeightedDigraph G, int s) {


        if (hasNegativeCycle()) {
            double weight = 0.0;
            for (DirectedEdge e : negativeCycle()) {
                weight += e.weight();
            }
            if (weight >= 0.0) {
                System.err.println("error: weight of negative cycle = " + weight);
                return false;
            }
        }


        else {


            if (distTo[s] != 0.0 || edgeTo[s] != null) {
                System.err.println("distanceTo[s] and edgeTo[s] inconsistent");
                return false;
            }
            for (int v = 0; v < G.V(); v++) {
                if (v == s) continue;
                if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                    System.err.println("distTo[] and edgeTo[] inconsistent");
                    return false;
                }
            }


            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    if (distTo[v] + e.weight() < distTo[w]) {
                        System.err.println("edge " + e + " not relaxed");
                        return false;
                    }
                }
            }


            for (int w = 0; w < G.V(); w++) {
                if (edgeTo[w] == null) continue;
                DirectedEdge e = edgeTo[w];
                int v = e.from();
                if (w != e.to()) return false;
                if (distTo[v] + e.weight() != distTo[w]) {
                    System.err.println("edge " + e + " on shortest path not tight");
                    return false;
                }
            }
        }

        StdOut.println("Satisfies optimality conditions");
        StdOut.println();
        return true;
    }


    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }


    public static void main(String[] args) {
        int i,s=0;
        float []a = new float [3] ;
        Scanner sc = new Scanner(System.in) ;
        int l = sc.nextInt();
        String input = null;
        if (l==1) {
            for (i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.print("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem1.txt";
                    s = 3;
                    System.out.println();
                } else if (i == 1) {
                    System.out.println("Graph2:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem2.txt";
                    s = 1;
                } else if (i == 2) {
                    System.out.println("Graph3:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem3.txt";
                    s = 1;
                }
                In in = new In(input);
                EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

                BellmanFordSP sp = new BellmanFordSP(G, s);


                if (sp.hasNegativeCycle()) {
                    for (DirectedEdge e : sp.negativeCycle())
                        StdOut.println(e);
                } else {
                    for (int v = 0; v < G.V(); v++) {
                        if (sp.hasPathTo(v)) {
                            StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                            for (DirectedEdge e : sp.pathTo(v)) {
                                StdOut.print(e + "   ");
                            }
                            StdOut.println();
                        } else {
                            StdOut.printf("%d to %d           no path\n", s, v);
                        }
                    }
                }
            }
        }
        else if (l==2)
        {
            for (i = 0; i <= 3; i++) {
                if (i == 0) {
                    System.out.println("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list.txt";
                    s = 0 ;
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
                }
                else if (i == 3) {
                    System.out.print("Graph4:");
                    System.out.println();
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\collaboration_network_list.txt";
                    s = 0;
                }
                In in = new In(input);
                EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

                BellmanFordSP sp = new BellmanFordSP(G, s);


                if (sp.hasNegativeCycle()) {
                    for (DirectedEdge e : sp.negativeCycle())
                        StdOut.println(e);
                } else {
                    for (int v = 0; v < G.V(); v++) {
                        if (sp.hasPathTo(v)) {
                            StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                            for (DirectedEdge e : sp.pathTo(v)) {
                                StdOut.print(e + "   ");
                            }
                            StdOut.println();
                        } else {
                            StdOut.printf("%d to %d           no path\n", s, v);
                        }
                    }
                }
            }
        }
    }
}