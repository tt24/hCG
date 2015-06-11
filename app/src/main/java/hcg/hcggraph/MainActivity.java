package hcg.hcggraph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends Activity {

    private Button btnDrawGraph;
    private Button btnDrawCustomGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDrawGraph = (Button) findViewById(R.id.btnDrawGraph);
        btnDrawCustomGraph = (Button) findViewById(R.id.btnDrawCustomGraph);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-LightItalic.ttf");
        btnDrawGraph.setTypeface(custom_font);
        btnDrawCustomGraph.setTypeface(custom_font);
        btnDrawGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GraphFormActivity.class);
                Bundle extras = getIntent().getExtras();
                i.putExtra("new", extras.getBoolean("new", true));
                startActivity(i);

            }
        });

        btnDrawCustomGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CustomGraphForm1Activity.class);
                Bundle extras = getIntent().getExtras();
                i.putExtra("new", extras.getBoolean("new", true));
                startActivity(i);

            }
        });
    }
}

