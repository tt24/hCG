package hcg.hcggraph;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeMap;


public class DrawGraphActivity extends ActionBarActivity {
    private final double a = 126.246953;
    private final double b = 0.35634767;
    private final double c = 7.9842106;
    private final double d = 44.8264683;
    private final int numHorizontalLabels = 12;

    public DataPoint[] write(int dose, int periodicity, int subDose, int number) {
        ArrayList<Double> conPoints = new ArrayList<>();
        double volDist = 5000 / a;
        double cMax = dose / volDist;
            int hours = 0;
            double hcgC = 0;
//            fw.write("Hours, hCG Concentration \n");
        conPoints.add(hcgC);
            hours++;
            hcgC = 1;
            double cMax1 = 0;
            int count = 0;
            while (hcgC > 0.08||count<number) {
                hcgC = cMax * (c + d) * (1 - Math.exp(-(hours - b) / c)) * Math.exp(-(hours - b) / d) / (d * Math.exp(-c * (Math.log(c + d) - Math.log(c)) / d));
                if (periodicity!=-1&&hours > periodicity && hours % periodicity == 1 && count != number) {
                    count++;
                    cMax1 = subDose / volDist;
                }
                int i = 1;
                while(i<=count) {
                    int hours1 = hours - periodicity * i;
                    hcgC += cMax1 * (c + d) * (1 - Math.exp(-(hours1 - b) / c)) * Math.exp(-(hours1 - b) / d) / (d * Math.exp(-c * (Math.log(c + d) - Math.log(c)) / d));
                    i++;
                }
                conPoints.add(hcgC);
                hours++;
            }
        return createDataPoints(conPoints);
    }

    public DataPoint[] createDataPoints(ArrayList<Double> conPoints) {
        DataPoint[] points = new DataPoint[conPoints.size()];
        for (int i = 0; i < conPoints.size(); i++) {
            points[i] = new DataPoint(i, conPoints.get(i));
        }
        return points;
    }

    public DataPoint[] write(int dose, int number) {
        ArrayList<Double> conPoints = new ArrayList<>();
        double volDist = 5000 / a;
        double cMax = dose / volDist;
        int hours = 0;
        double hcgC = 0;
//            fw.write("Hours, hCG Concentration \n");
        conPoints.add(hcgC);
        hours++;
        hcgC = 1;
        double cMax1 = 0;
        int count = 0;
        TreeMap<Integer, Integer> hourAndDose = CustomDoses.getHourAndDose();
        while (hcgC > 0.1 || count < number) {
            hcgC = cMax * (c + d) * (1 - Math.exp(-(hours - b) / c)) * Math.exp(-(hours - b) / d) / (d * Math.exp(-c * (Math.log(c + d) - Math.log(c)) / d));
            if (hourAndDose.containsKey(hours) && count != number) {
                count++;
                cMax1 = hourAndDose.get(hours) / volDist;
            }
            NavigableSet<Integer> key = hourAndDose.navigableKeySet();
            Iterator<Integer> i = key.iterator();
            int sHour;
            if (cMax1 != 0) {
                while (i.hasNext() && ((sHour = i.next()) != hours)) {
                    cMax1 = hourAndDose.get(sHour) / volDist;
                    int hours1 = hours - sHour;
                    hcgC += cMax1 * (c + d) * (1 - Math.exp(-(hours1 - b) / c)) * Math.exp(-(hours1 - b) / d) / (d * Math.exp(-c * (Math.log(c + d) - Math.log(c)) / d));
                }
            }
            conPoints.add(hcgC);
            hours++;
        }
        return createDataPoints(conPoints);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_draw);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int initial = extras.getInt("initial");
        System.out.println("Initial " + initial);
        int number = extras.getInt("number");
        boolean newGraph = extras.getBoolean("new");
        boolean custom = extras.getBoolean("custom");
        DataPoint[] points;
        String legendTitle = "initial dose: " + initial;
        if (!custom) {
            int subsequent = extras.getInt("subsequent");
            int periodicity = extras.getInt("periodicity");
            points = write(initial, periodicity, subsequent, number);
            legendTitle += ", subsequent dose(s) " + subsequent;
            GraphStorage.addGraph(new Graph(points, initial, subsequent, custom, legendTitle));
//            GraphStorage.addPrevGraphs(points, initial, subsequent);
        } else {
            points = write(initial, number);
            legendTitle += CustomDoses.getLegendTitle();
            GraphStorage.addGraph(new Graph(points, initial, CustomDoses.getHourAndDose(), custom, CustomDoses.getLegendTitle()));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graph.addSeries(series);
        LegendRenderer lr = graph.getLegendRenderer();
        series.setTitle(legendTitle);
        int maxLength = points.length;
        LineGraphSeries<DataPoint> series1;
        if(!newGraph) {
            maxLength = addGraph(graph, maxLength);
        }
        lr.setVisible(true);
        Viewport view = graph.getViewport();
        view.setXAxisBoundsManual(true);
        view.setMinX(0.0);
        view.setMaxX(maxLength);
        view.setScrollable(true);
        view.setScalable(true);
//        GraphView gv = new GraphView(this);
        GridLabelRenderer renderer = graph.getGridLabelRenderer();
        renderer.setHorizontalLabelsVisible(true);
        renderer.setNumHorizontalLabels(numHorizontalLabels);
        renderer.setHorizontalAxisTitle("Time (hours)");
        renderer.setVerticalAxisTitle("hCG (IU\\L)");

        Button drawNewGraph = (Button) findViewById(R.id.btnDrawNew);

        drawNewGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                GraphStorage.clear();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("new", true);
                startActivity(i);
            }
        });

        Button addGraph = (Button) findViewById(R.id.btnAddGraph);

        addGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GraphFormActivity.class);
                i.putExtra("new", false);
                startActivity(i);

            }
        });
    }

    public int addGraph(GraphView graph, int maxLength) {
        ArrayList<Graph> prevGraphs = GraphStorage.getGraph();
        for (int j = 0; j < prevGraphs.size() - 1; j++) {
            DataPoint[] p = prevGraphs.get(j).getPoints();
            if (p.length > maxLength) {
                maxLength = p.length;
            }
            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(p);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            series1.setColor(color);
            graph.addSeries(series1);
            series1.setTitle(prevGraphs.get(j).getLegendTitle());
        }
        return maxLength;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
