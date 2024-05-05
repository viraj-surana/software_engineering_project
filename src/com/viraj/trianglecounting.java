package com.viraj;
import edu.princeton.cs.algs4.*;
import java.util.Scanner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
public class trianglecounting {
    private int count; // number of triangles

    public trianglecounting(Graph G) {
        count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                for (int x : G.adj(w)) {
                    if (x == v) {
                        count++;
                    }
                }
            }
        }
        count /= 6; // each triangle is counted 6 times
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        String input;
        if (l == 1) {
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    System.out.print("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem1.txt";
                    System.out.println();
                } else if (i == 1) {
                    System.out.println("Graph2:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem2.txt";
                } else {
                    System.out.println("Graph3:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\midsem3.txt";
                }
                In in = new In(input);
                Graph G = new Graph(in);

                trianglecounting tc = new trianglecounting(G);
                System.out.println("Number of triangles: " + tc.count());
            }
        } else if (l == 2) {
            for (int i = 0; i <= 3; i++) {
                if (i == 0) {
                    System.out.println("Graph1:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list.txt";
                } else if (i == 1) {
                    System.out.println("Graph2:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list_2.txt";
                } else if (i == 2) {
                    System.out.println("Graph3:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\social_network_list_3.txt";
                } else {
                    System.out.println("Graph4:");
                    input = "C:\\Users\\Viraj Surana\\IdeaProjects\\Graph_Project\\src\\com\\viraj\\collaboration_network_list.txt";
                }
                In in = new In(input);
                Graph G = new Graph(in);

                trianglecounting tc = new trianglecounting(G);
                System.out.println("Number of triangles: " + tc.count());
            }
        }
    }
}
