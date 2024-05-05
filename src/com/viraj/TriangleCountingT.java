package com.viraj;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

public class TriangleCountingT {
    private int triangleCount;  // number of triangles
    private boolean[] marked;    // marked[v] = has vertex v been visited?
    private int[] count;         // count[v] = number of triangles containing v

    public TriangleCountingT(int[][] edges, int V) {
        triangleCount = 0;
        marked = new boolean[V];
        count = new int[V];

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
            dfs(adj, v, v, 0, new ArrayList<>());
            marked[v] = true; // Mark the current vertex as visited
        }
    }

    private void dfs(List<List<Integer>> adj, int source, int v, int depth, List<Integer> path) {
        marked[v] = true;
        path.add(v);

        if (depth == 2) {
            for (int w : adj.get(v)) {
                if (w == source) {
                    triangleCount++;
                    for (int vertex : path) {
                        count[vertex]++;
                    }
                    break;
                }
            }
        } else {
            for (int w : adj.get(v)) {
                if (!marked[w]) {
                    dfs(adj, source, w, depth + 1, path);
                }
            }
        }

        marked[v] = false;
        path.remove(path.size() - 1);
    }

    public int triangleCount() {
        return triangleCount;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: java TriangleCountingT <filename>");
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

        TriangleCountingT tc = new TriangleCountingT(edges, V);

        StdOut.println("Number of triangles: " + tc.triangleCount());
    }
}
