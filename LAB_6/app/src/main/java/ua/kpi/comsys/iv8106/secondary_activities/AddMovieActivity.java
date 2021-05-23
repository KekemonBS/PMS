package ua.kpi.comsys.iv8106.secondary_activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ua.kpi.comsys.iv8106.R;

public class AddMovieActivity extends AppCompatActivity {
    private String Title;
    private String Year;
    private String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        TextView title = (TextView) findViewById(R.id.textInputEditText2);
        TextView year  = (TextView) findViewById(R.id.textInputEditText4);
        TextView type  = (TextView) findViewById(R.id.textInputEditText5);


        Button button = findViewById(R.id.buttonAddMovie);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newMovie = "";
                newMovie += "[" + "{\"Title\":\"" + title.getText().toString() + "\"," +
                        "\"Year\":\"" + year.getText().toString() + "\"," +
                        "\"imdbID\":\"\"," +
                        "\"Type\":\"" + type.getText().toString() + "\"," +
                        "\"Poster\":\"\"}" + "]";

                Intent resultIntent = new Intent();
                resultIntent.putExtra("movie", newMovie);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}