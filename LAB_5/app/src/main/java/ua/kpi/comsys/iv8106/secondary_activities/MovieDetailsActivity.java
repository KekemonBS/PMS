package ua.kpi.comsys.iv8106.secondary_activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.model.MovieDetailsItem;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageView descImage = (ImageView)findViewById(R.id.imageView);
        TextView  descText  = (TextView) findViewById(R.id.textView2);

        String file = getIntent().getStringExtra("id");

        System.out.println(file);
        Gson gson = new Gson();

        Type movieDetailsElement = new TypeToken<MovieDetailsItem>() {}.getType();
        MovieDetailsItem item = null;
        try {
            item = gson.fromJson(ReadTextFile(file), movieDetailsElement);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int drawableResourceId = this.getResources().getIdentifier(
                item.getPoster().toLowerCase().replace(".jpg", ""),
                "drawable", this.getPackageName());

        if (drawableResourceId != 0) {
            descImage.setImageResource(drawableResourceId);
        }

        descText.setText(item.toString());
    }

    public String ReadTextFile(String name) throws IOException {
        StringBuilder string = new StringBuilder();
        String line = "";
        InputStream is = this.getAssets().open(name + ".json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            string.append(line);
        }
        is.close();
        return string.toString();

    }
}