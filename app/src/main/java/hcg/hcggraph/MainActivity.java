package hcg.hcggraph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends Activity {

    Button btnDrawGraph;
    Button btnDrawCustomGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDrawGraph = (Button) findViewById(R.id.btnDrawGraph);
        btnDrawCustomGraph = (Button) findViewById(R.id.btnDrawCustomGraph);

        btnDrawGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DrawGraphFormActivity.class);
                i.putExtra("new", true);
                startActivity(i);

            }
        });

        btnDrawCustomGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //               Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                //              startActivity(i);

            }
        });
    }
}

