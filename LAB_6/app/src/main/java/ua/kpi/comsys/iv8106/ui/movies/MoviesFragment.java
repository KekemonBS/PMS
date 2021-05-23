package ua.kpi.comsys.iv8106.ui.movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.adapters.MoviesAdapter;
import ua.kpi.comsys.iv8106.model.MovieItem;
import ua.kpi.comsys.iv8106.secondary_activities.AddMovieActivity;

public class MoviesFragment extends Fragment {

//    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    View root;

    Type listOfMoviesItemsType = new TypeToken<ArrayList<MovieItem>>() {}.getType();
    ArrayList<MovieItem> movie_list = new ArrayList<>();
    ArrayList<String> main_title = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_movies, container, false);

//        int rCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (rCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
//
//        }

        String path = "MoviesList.json";
        //File down = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        TextView nothingFound = root.findViewById(R.id.nothingFound);
        nothingFound.setVisibility(View.INVISIBLE);

        Gson gson = new Gson();

//        ContextWrapper cont = new ContextWrapper(getActivity());
//        System.out.println(cont.getFilesDir());

        try {
            this.movie_list = gson.fromJson(ReadTextFile(path), listOfMoviesItemsType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (MovieItem movie: movie_list) {
            main_title.add(movie.getTitle());
        }

        RecyclerView list = root.findViewById(R.id.noMoviesMessage);
        MoviesAdapter adapter_movie = new MoviesAdapter(this, movie_list, main_title);
        list.setAdapter(adapter_movie);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchBar = (SearchView) root.findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                onQueryTextChange(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int hiddenItemsCount = 0;
                for (MovieItem m : movie_list){
                    if (newText.equals("")) {
                        m.setVisible(true);
                        nothingFound.setVisibility(View.INVISIBLE);
                        continue;
                    }

                    if (!m.getTitle().startsWith(newText)) {
                        m.setVisible(false);
                        hiddenItemsCount++;
                    } else {
                        m.setVisible(true);
                    }
                }
                if (hiddenItemsCount == movie_list.size()) {
                    nothingFound.setVisibility(View.VISIBLE);
                }
                adapter_movie.notifyDataSetChanged();
                return true;
            }
        });

        Button addMovieButton = (Button) root.findViewById(R.id.addItem);
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMovieActivity.class);
                startActivityForResult(intent, 1);
                adapter_movie.notifyDataSetChanged();
            }
        });

        return root;
    }

    private void updateJSON(String newData) {
        Gson gson = new Gson();
        Type listOfMoviesItemsType = new TypeToken<ArrayList<MovieItem>>() {}.getType();
        ArrayList<MovieItem> new_movie = gson.fromJson(newData, listOfMoviesItemsType);
        this.movie_list.addAll(gson.fromJson(newData, listOfMoviesItemsType));
        this.main_title.add(new_movie.get(0).getTitle());
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

//    private String moviesToString() {
//        StringBuilder result = new StringBuilder("[ ");
//        for (int i = 0; i < movie_list.size(); i++) {
//            if (i < movie_list.size() - 1) {
//                result.append(movie_list.get(i).toString());
//                result.append(", ");
//            }
//            else result.append(movie_list.get(i).toString());
//
//        }
//        return result.append(" ]").toString();
//    }

    public String ReadTextFile(String name) throws IOException {
        StringBuilder string = new StringBuilder();
        String line = "";
        InputStream is = getContext().getAssets().open(name);
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