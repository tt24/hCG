package hcg.hcggraph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DrawGraphFormActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_graph_draw);

        final EditText initialDose = (EditText) findViewById(R.id.initial_dose);
        final EditText subDose = (EditText) findViewById(R.id.sub_dose);
        final EditText periodicity = (EditText) findViewById(R.id.periodicity);
        final EditText number = (EditText) findViewById(R.id.number);

        Button drawGraph = (Button) findViewById(R.id.draw_graph);

        drawGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DrawGraphActivity.class);
                Bundle extras = new Bundle();
                if(initialDose.getText()!=null) {
                    extras.putInt("initial", Integer.parseInt(initialDose.getText().toString()));
                    extras.putInt("subsequent", Integer.parseInt(subDose.getText().toString()));
                    extras.putInt("periodicity", Integer.parseInt(periodicity.getText().toString()));
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
