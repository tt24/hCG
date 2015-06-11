package hcg.hcggraph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GraphFormActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_graph);
        TextView tx = (TextView) findViewById(R.id.initial_dose_text);
        TextView tx1 = (TextView) findViewById(R.id.sub_dose_text);
        TextView tx2 = (TextView) findViewById(R.id.periodicity_text);
        TextView tx3 = (TextView) findViewById(R.id.number_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Raleway-LightItalic.ttf");
        tx.setTypeface(custom_font);
        tx1.setTypeface(custom_font);
        tx2.setTypeface(custom_font);
        tx3.setTypeface(custom_font);
        final EditText initialDose = (EditText) findViewById(R.id.initial_dose);
        final EditText subDose = (EditText) findViewById(R.id.sub_dose);
        final EditText periodicity = (EditText) findViewById(R.id.periodicity);
        final EditText number = (EditText) findViewById(R.id.number);

        Button drawGraph = (Button) findViewById(R.id.draw_graph);
        drawGraph.setTypeface(custom_font);
        drawGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DrawGraphActivity.class);
                Bundle extras = new Bundle();
                if (initialDose.getText() != null) {
                    int subs = -1;
                    int period = -1;
                    int n = -1;
                    extras.putInt("initial", Integer.parseInt(initialDose.getText().toString()));
                    if (initialDose.getText() != null) {
                        subs = Integer.parseInt(subDose.getText().toString());
                    }
                    if (periodicity.getText() != null) {
                        period = Integer.parseInt(periodicity.getText().toString());
                    }
                    if (number.getText() != null) {
                        n = Integer.parseInt(number.getText().toString());
                    }
                    extras.putInt("subsequent", subs);
                    extras.putInt("periodicity", period);
                    extras.putInt("number", n);
                    Intent intent = getIntent();
                    extras.putBoolean("new", intent.getExtras().getBoolean("new"));
                    extras.putBoolean("custom", false);
                    i.putExtras(extras);
                    startActivity(i);
                } else {
                    initialDose.setError("Please enter the initial dose.");
                }
            }
        });
    }
}
