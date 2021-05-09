package ua.kpi.comsys.iv8106.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.model.MovieItem;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private final Fragment context;
    private final ArrayList<MovieItem> movies;
    private final ArrayList<String> maintitle;

    public MoviesAdapter(Fragment context, ArrayList<MovieItem> movies, ArrayList<String> maintitle) {
        this.context=context;
        this.movies=movies;
        this.maintitle = maintitle;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView titleText;
        private TextView yearText;
        private TextView typeText;

        public MovieViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.image    = (ImageView) view.findViewById(R.id.poster);
            this.titleText = (TextView)  view.findViewById(R.id.title);
            this.yearText  = (TextView)  view.findViewById(R.id.year);
            this.typeText  = (TextView)  view.findViewById(R.id.type);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie, parent, false);

        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MovieViewHolder holder, int position) {
        int drawableResourceId = context.getResources().getIdentifier(
                movies.get(position).getPoster().toLowerCase().replace(".jpg", ""),
                "drawable", context.getContext().getPackageName());

        holder.titleText.setText(maintitle.get(position));
        holder.yearText.setText(movies.get(position).getYear());
        holder.typeText.setText(movies.get(position).getType());

        if (drawableResourceId != 0) {
            holder.image.setImageResource(drawableResourceId);
        } else {
            holder.image.setImageResource(R.drawable.ic_action_cancel);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
