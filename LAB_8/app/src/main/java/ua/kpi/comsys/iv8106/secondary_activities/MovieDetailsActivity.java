package ua.kpi.comsys.iv8106.secondary_activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Queue;

import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.model.MovieDetailsItem;
import ua.kpi.comsys.iv8106.tools.Requester;
import ua.kpi.comsys.iv8106.tools.database.Databaser;

public class MovieDetailsActivity extends AppCompatActivity {

    private static String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String id = getIntent().getStringExtra("id");

        Databaser db = new Databaser(this, "lists", null, 1);

        if (db.queryTable("movieDescList", id,  "id")  == null) {
            String formattedUrlString = "http://www.omdbapi.com/?apikey=%s&i=%s";
            String apiKey = "7e9fe69e";
            Queue<String> queue = new LinkedList<>();
            Requester req = new Requester(queue, formattedUrlString, apiKey, id);
            System.out.println("PRE_HERE1");
            Thread th1 = new Thread(req, "movies");
            th1.start();
            try {
                th1.join();
                setJSONResponse(queue.remove());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (response != null) {
                //-------------------------
                ContentValues row = new ContentValues();
                row.put("id", id);
                row.put("json", response);
                db.apppendToTable("movieDescList", id, "id", row);
                //-------------------------
            }
            updateMovieList(response);
            //Here
        } else if (db.queryTable("movieDescList", id,  "id") != null) {
            response = db.queryTable("movieDescList", id,  "id");
            updateMovieList(response);
        } else {
            Toast toast = Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT);
            toast.show();
        }

        //------------------------------------------------------------------------------------
    }

    private void updateMovieList(String response) {
        ImageView descImage = findViewById(R.id.imageView);
        TextView  descText  = findViewById(R.id.textView2);
        if (response != null) {
            Gson gson = new Gson();
            Type movieDetailsElement = new TypeToken<MovieDetailsItem>() {}.getType();
            MovieDetailsItem item = null;
            item = gson.fromJson(response, movieDetailsElement);

            if (item.getPoster().length() != 0) {
                Picasso.get().load(item.getPoster()).into(descImage);
            }

            descText.setText(item.toString());
        } else {
            descText.setText("Description was not loaded.");
        }

    }

    public static void setJSONResponse(String JSON) {
        response = JSON;
    }

}