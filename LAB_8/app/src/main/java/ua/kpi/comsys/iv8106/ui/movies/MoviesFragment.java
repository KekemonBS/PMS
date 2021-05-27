package ua.kpi.comsys.iv8106.ui.movies;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.adapters.MoviesAdapter;
import ua.kpi.comsys.iv8106.model.MovieItem;
import ua.kpi.comsys.iv8106.tools.Requester;
import ua.kpi.comsys.iv8106.tools.database.Databaser;

public class MoviesFragment extends Fragment {

    private static String response;
    //    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    View root;

    Type listOfMoviesItemsType = new TypeToken<ArrayList<MovieItem>>() {}.getType();
    ArrayList<MovieItem> movie_list = new ArrayList<>();
    ArrayList<String> main_title = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.setRetainInstance(true);

        root = inflater.inflate(R.layout.fragment_movies, container, false);

//        int rCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (rCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
//
//        }

        TextView nothingFound = root.findViewById(R.id.nothingFound);
        nothingFound.setVisibility(View.VISIBLE);

        nothingFound.setVisibility(View.INVISIBLE);

        RecyclerView list = root.findViewById(R.id.noMoviesMessage);
        MoviesAdapter adapter_movie = new MoviesAdapter(this, this.movie_list, this.main_title);
        list.setAdapter(adapter_movie);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        ProgressBar spinner = (ProgressBar)root.findViewById(R.id.progressBarMov);
        spinner.setVisibility(ProgressBar.INVISIBLE);

        SearchView searchBar = (SearchView) root.findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                spinner.setVisibility(ProgressBar.VISIBLE);
                new Thread(new Runnable() {
                @Override
                public void run() {
                    Databaser db = new Databaser(getContext(), "lists", null, 1);
                    if ((query.length() >= 3) &&
                            (db.queryTable("movieList", query,  "query")  == null)) {
                        String formattedUrlString = "http://www.omdbapi.com/?apikey=%s&s=%s&page=1";
                        String apiKey = "7e9fe69e";
                        Queue<String> queue = new LinkedList<>();
                        Requester req = new Requester(queue, formattedUrlString, apiKey, query);
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
                            row.put("query", query);
                            row.put("json", response);
                            db.apppendToTable("movieList", query, "query", row);
                            //-------------------------
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                        }
                        System.out.println(response);
                    } else if (db.queryTable("movieList", query,  "query") != null) {
                        response = db.queryTable("movieList", query,  "query");
                    } else {
                        movie_list.clear();
                        main_title.clear();
                        response = null;
                    }
                    spinner.setVisibility(ProgressBar.INVISIBLE);
                    updateMovieList(response);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (query.length() < 3)
                                nothingFound.setVisibility(View.VISIBLE);
                            if (response != null && !response.contains("Error")) {
                                nothingFound.setVisibility(View.INVISIBLE);
                            } else {
                                nothingFound.setVisibility(View.VISIBLE);
                            }
                            adapter_movie.notifyDataSetChanged();
                        }
                    });
                }
            }).start();

//                System.out.println(main_title);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            private void updateMovieList(String response) {
                Gson gson = new Gson();
                if (response != null &&
                    !gson.fromJson(response, JsonObject.class).has("Error")) {
                    JsonObject gsontmp = gson.fromJson(response, JsonObject.class);
                    movie_list.clear();
                    movie_list.addAll(gson.fromJson(gsontmp.get("Search"), listOfMoviesItemsType));
                    System.out.println(movie_list.hashCode());

                    main_title.clear();
                    for (MovieItem movie : movie_list) {
                        main_title.add(movie.getTitle());
                    }
                } else {
                    movie_list.clear();
                    main_title.clear();
                }
            }
        });

//--------This Activity is still present if needed--------
//        Button addMovieButton = (Button) root.findViewById(R.id.addItem);
//        addMovieButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AddMovieActivity.class);
//                startActivityForResult(intent, 1);
//                adapter_movie.notifyDataSetChanged();
//            }
//        });

        return root;
    }

    public static void setJSONResponse(String JSON) {
        response = JSON;
    }

    private void updateJSON(String newData) {
        Gson gson = new Gson();
        Type listOfMoviesItemsType = new TypeToken<ArrayList<MovieItem>>() {}.getType();
        ArrayList<MovieItem> new_movie = gson.fromJson(newData, listOfMoviesItemsType);
        movie_list.addAll(gson.fromJson(newData, listOfMoviesItemsType));
        main_title.add(new_movie.get(0).getTitle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String returnValue = data.getStringExtra("movie");
                updateJSON(returnValue);
            }
        }
    }


//Not needed anymore
//    public String ReadTextFile(String name) throws IOException {
//        StringBuilder string = new StringBuilder();
//        String line = "";
//        InputStream is = getContext().getAssets().open(name);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        while (true) {
//            try {
//                if ((line = reader.readLine()) == null) break;
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//            string.append(line);
//        }
//        is.close();
//        return string.toString();
//
//    }
}