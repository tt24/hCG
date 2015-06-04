package hcg.hcggraph;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


public class DrawGraphActivity extends ActionBarActivity {

    public DataPoint[] write(int dose, int periodicity, int subDose, int number) {
        ArrayList<Double> conPoints = new ArrayList<Double>();
        double a = 126.246953;
        double b = 0.35634767;
        double c = 7.9842106;
        double d = 44.8264683;
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
            while (hcgC > 0.01) {
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
        DataPoint[] points = new DataPoint[conPoints.size()];
        for(int i = 0; i<conPoints.size(); i++) {
            points[i] = new DataPoint(i, conPoints.get(i));
        }
        return points;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_draw);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int initial = extras.getInt("initial");
        int subsequent = extras.getInt("subsequent");
        int periodicity = extras.getInt("periodicity");
        int number = extras.getInt("number");
        DataPoint[] points = write(initial, periodicity, subsequent, number);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        graph.addSeries(series);
        Viewport view = graph.getViewport();
        view.setXAxisBoundsManual(true);
        view.setMinX(0.0);
        view.setMaxX(points.length);
        view.setScrollable(true);
        view.setScalable(true);
        GridLabelRenderer renderer = graph.getGridLabelRenderer();
        renderer.setHorizontalLabelsVisible(true);
        renderer.setNumHorizontalLabels(points.length / 168);
        renderer.setHorizontalAxisTitle("Time (weeks)");
        renderer.setVerticalAxisTitle("hCG (IU\\L)");

        Button drawNewGraph = (Button) findViewById(R.id.btnDrawNew);

        drawNewGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DrawGraphFormActivity.class);
                startActivity(i);
            }
        });
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
