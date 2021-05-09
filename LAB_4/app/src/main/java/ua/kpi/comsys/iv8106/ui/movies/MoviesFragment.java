package ua.kpi.comsys.iv8106.ui.movies;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

public class MoviesFragment extends Fragment {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movies, container, false);

        int rCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (rCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);

        }

        String path = "MoviesList.json";
        //File down = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        Gson gson = new Gson();

        Type listOfMoviesItemsType = new TypeToken<ArrayList<MovieItem>>() {}.getType();
        ArrayList<MovieItem> movie_list = new ArrayList<>();
        ArrayList<String> main_title = new ArrayList<>();

//        ContextWrapper cont = new ContextWrapper(getActivity());
//        System.out.println(cont.getFilesDir());

        try {
            //String res = ReadTextFile(path);
            //System.out.println(res);
            movie_list = gson.fromJson(ReadTextFile(path), listOfMoviesItemsType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (MovieItem movie: movie_list) {
            main_title.add(movie.getTitle());
        }

        System.out.println(movie_list.get(1).getPoster());

        RecyclerView list = root.findViewById(R.id.movies);
        MoviesAdapter adapter_movie = new MoviesAdapter(this, movie_list, main_title);
        list.setAdapter(adapter_movie);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

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