package hcg.hcggraph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomGraphForm1Activity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_graph_custom);
        TextView tx = (TextView) findViewById(R.id.initial_dose_text);
        TextView tx3 = (TextView) findViewById(R.id.number_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-LightItalic.ttf");
        tx.setTypeface(custom_font);
        tx3.setTypeface(custom_font);
        final EditText initialDose = (EditText) findViewById(R.id.initial_dose);
        final EditText number = (EditText) findViewById(R.id.number);
        Button drawGraph = (Button) findViewById(R.id.draw_graph);
        drawGraph.setTypeface(custom_font);
        drawGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i;
                Bundle extras = new Bundle();
                if (initialDose.getText() != null) {
                    extras.putInt("initial", Integer.parseInt(initialDose.getText().toString()));
                    int n = 0;
                    if (number.getText() != null) {
                        n = Integer.parseInt(number.getText().toString());
                    }
                    extras.putInt("number", n);
                    Intent intent = getIntent();
                    extras.putBoolean("new", intent.getExtras().getBoolean("new"));
                    extras.putBoolean("custom", true);
                    if (n > 0) {
                        i = new Intent(getApplicationContext(), CustomGraphForm2Activity.class);
                    } else {
                        i = new Intent(getApplicationContext(), DrawGraphActivity.class);
                    }
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    initialDose.setError("Please enter the initial dose.");
                }
            }
        });
    }
}
