package hcg.hcggraph;

import com.jjoe64.graphview.series.DataPoint;

import java.util.TreeMap;

public class Graph {
    private DataPoint[] points;
    private int initialDose;
    private int subDose;
    private boolean custom;
    private TreeMap<Integer, Integer> customSubDose;
    private String legendTitle;

    public Graph(DataPoint[] points, int initialDose, int subDose, boolean custom, String legendTitle) {
        this.initialDose = initialDose;
        this.points = points;
        this.subDose = subDose;
        this.custom = custom;
        this.legendTitle = legendTitle;
    }

    public Graph(DataPoint[] points, int initialDose, TreeMap<Integer, Integer> customSubDose, boolean custom, String legendTitle) {
        this.initialDose = initialDose;
        this.points = points;
        this.customSubDose = customSubDose;
        this.custom = custom;
        this.legendTitle = legendTitle;
    }

    public DataPoint[] getPoints() {
        return points;
    }

    public int getInitialDose() {
        return initialDose;
    }

    public int getSubDose() {
        return subDose;
    }

    public TreeMap<Integer, Integer> getCustomSubDose() {
        return customSubDose;
    }

    public boolean getCustom() {
        return custom;
    }

    public String getLegendTitle() {
        return legendTitle;
    }
}
