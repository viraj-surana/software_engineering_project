package com.viraj;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

public class ConnectedComponents {
    private int count;           // number of connected components
    private int[] id;            // id[v] = id of connected component containing v
    private boolean[] marked;    // marked[v] = has vertex v been visited?

    public ConnectedComponents(int[][] edges, int V) {
        count = 0;
        id = new int[V];
        marked = new boolean[V];

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        for (int v = 0; v < V; v++) {
            if (!marked[v]) {
                dfs(adj, v);
                count++;
            }
        }
    }

    private void dfs(List<List<Integer>> adj, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : adj.get(v)) {
            if (!marked[w]) {
                dfs(adj, w);
            }
        }
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: java ConnectedComponents <filename>");
            return;
        }

        In in = new In(args[0]);
        int V = in.readInt();
        int E = in.readInt();
        int[][] edges = new int[E][2];
        for (int i = 0; i < E; i++) {
            edges[i][0] = in.readInt();
            edges[i][1] = in.readInt();
        }

        ConnectedComponents cc = new ConnectedComponents(edges, V);

        StdOut.println("Number of connected components: " + cc.count());
    }
}
