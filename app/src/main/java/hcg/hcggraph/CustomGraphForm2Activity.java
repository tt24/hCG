package hcg.hcggraph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomGraphForm2Activity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2_graph_custom);
//        TextView tx = (TextView)findViewById(R.id.initial_dose_text);
//        TextView tx3 = (TextView)findViewById(R.id.number_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Raleway-LightItalic.ttf");
//        tx.setTypeface(custom_font);
//        tx3.setTypeface(custom_font);
//        final EditText initialDose = (EditText) findViewById(R.id.initial_dose);
//        final EditText number = (EditText) findViewById(R.id.number);

        Bundle extras = getIntent().getExtras();
        final int N = extras.getInt("number");
        final EditText[] edits = new EditText[2*N]; // create an empty array;
        final LinearLayout layout = (LinearLayout) findViewById(R.id.custom_graph_layout);
        for (int i = 0; i < N; i++) {
            final TextView subDoseText1 = new TextView(this);
            subDoseText1.setTypeface(custom_font);
            subDoseText1.setText("Subsequent dose " + i);
            final EditText subDoseEdit1 = new EditText(this);
            subDoseEdit1.setInputType(InputType.TYPE_CLASS_NUMBER);
            final EditText hourEdit1 = new EditText(this);
            hourEdit1.setInputType(InputType.TYPE_CLASS_NUMBER);
            final TextView hourText1 = new TextView(this);
            hourText1.setTypeface(custom_font);
            hourText1.setText("Subsequent dose time " + i);
            layout.addView(subDoseText1);
            layout.addView(subDoseEdit1);
            layout.addView(hourText1);
            layout.addView(hourEdit1);
            edits[i*2] = subDoseEdit1;
            edits[i*2+1] = hourEdit1;
        }
        Button drawGraph = new Button(this);
        drawGraph.setText("Draw Graph");
        drawGraph.setTypeface(custom_font);
        drawGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean correct = true;
                for (int i = 0; i < edits.length; i += 2) {
                    if (edits[i].getText() == null) {
                        correct = false;
                        edits[i].setError("Please fill in this field.");
                        break;
                    }
                    CustomDoses.addHourAndDose(Integer.parseInt(edits[i].getText().toString()), Integer.parseInt(edits[i + 1].getText().toString()));
                }
                if (correct) {
                    Intent i = new Intent(getApplicationContext(), DrawGraphActivity.class);
                    Bundle extras = new Bundle();
                    Bundle extras1 = getIntent().getExtras();
                    extras.putInt("initial", extras1.getInt("initial"));
                    extras.putInt("number", N);
                    extras.putBoolean("new", extras1.getBoolean("new"));
                    extras.putBoolean("custom", true);
                    i.putExtras(extras);
                    startActivity(i);
                }
            }
        });
        layout.addView(drawGraph);
    }
}