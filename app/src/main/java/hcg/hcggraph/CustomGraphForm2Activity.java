package hcg.hcggraph;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomGraphForm2Activity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1_graph_custom);
        TextView tx = (TextView)findViewById(R.id.initial_dose_text);
        TextView tx3 = (TextView)findViewById(R.id.number_text);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Raleway-LightItalic.ttf");
        tx.setTypeface(custom_font);
        tx3.setTypeface(custom_font);
        final EditText initialDose = (EditText) findViewById(R.id.initial_dose);
        final EditText number = (EditText) findViewById(R.id.number);

        Bundle extras = getIntent().getExtras();
        int N = extras.getInt("number");
        boolean newGraph = extras.getBoolean("new");
        final EditText[] edits = new EditText[2*N]; // create an empty array;
        final LinearLayout layout = (LinearLayout) findViewById(R.id.custom_graph_layout);
        for (int i = 0; i < N; i++) {
            final EditText subDoseEdit1 = new EditText(this);
            subDoseEdit1.setText("Subsequent dose "+ i);
            final EditText hourEdit1 = new EditText(this);
            hourEdit1.setText("Subsequent dose "+ i);
            layout.addView(subDoseEdit1);
            layout.addView(hourEdit1);
            edits[i*2] = subDoseEdit1;
            edits[i*2+1] = hourEdit1;
        }

        Button drawGraph = (Button) findViewById(R.id.draw_graph);
        drawGraph.setTypeface(custom_font);
        drawGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DrawGraphActivity.class);
                Bundle extras = new Bundle();
                if(initialDose.getText()!=null) {
                    extras.putInt("initial", Integer.parseInt(initialDose.getText().toString()));
                    extras.putInt("number", Integer.parseInt(number.getText().toString()));
                    Intent intent = getIntent();
                    extras.putBoolean("new", intent.getExtras().getBoolean("new"));
                    i.putExtras(extras);
                    startActivity(i);
                }
                else {
                    //TODO add checks
                }
            }
        });
    }
}