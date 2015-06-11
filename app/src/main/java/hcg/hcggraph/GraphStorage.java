package hcg.hcggraph;

import java.util.ArrayList;

public class GraphStorage {
    private static ArrayList<Graph> graph = new ArrayList<>();
//    private static ArrayList<DataPoint[]> prevGraphs = new ArrayList<>();
//    private static ArrayList<Integer> initDoses = new ArrayList<>();
//    private static ArrayList<Integer> subDoses = new ArrayList<>();

//    public static ArrayList<DataPoint[]> getPrevGraphs() {
//        return prevGraphs;
//    }
//
//    public static ArrayList<Integer> getInitDoses() {
//        return initDoses;
//    }
//
//    public static ArrayList<Integer> getSubDoses() {
//        return subDoses;
//    }
//
//    public static void addPrevGraphs(DataPoint[] p, int initDose, int subDose) {
//        prevGraphs.add(p);
//        initDoses.add(initDose);
//        subDoses.add(subDose);
//    }
//
//    public static void clearPrevGraphs() {
//        prevGraphs = new ArrayList<>();
//        initDoses = new ArrayList<>();
//        subDoses = new ArrayList<>();
//    }

    public static void addGraph(Graph g) {
        graph.add(g);
    }

    public static ArrayList<Graph> getGraph() {
        return graph;
    }

    public static void clear() {
        graph = new ArrayList<>();
    }
}
