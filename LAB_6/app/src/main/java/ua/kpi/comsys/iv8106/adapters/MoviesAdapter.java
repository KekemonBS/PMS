package ua.kpi.comsys.iv8106.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.model.MovieItem;
import ua.kpi.comsys.iv8106.secondary_activities.MovieDetailsActivity;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private final Fragment context;
    private final ArrayList<MovieItem> movies;
    private final ArrayList<String> maintitle;

    public MoviesAdapter(Fragment context, ArrayList<MovieItem> movies, ArrayList<String> maintitle) {
        this.context=context;
        this.movies=movies;
        this.maintitle = maintitle;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView titleText;
        private TextView yearText;
        private TextView typeText;

        private ImageView deleteButton;

        public MovieViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            this.image     = (ImageView) view.findViewById(R.id.image);
            this.titleText = (TextView)  view.findViewById(R.id.title);
            this.yearText  = (TextView)  view.findViewById(R.id.year);
            this.typeText  = (TextView)  view.findViewById(R.id.type);

            this.deleteButton = (ImageView) view.findViewById(R.id.deleteButton);

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

        if (!movies.get(position).isVisible()) {
            holder.itemView.setVisibility(View.INVISIBLE);
            holder.itemView.getLayoutParams().height = 0;
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.getLayoutParams().height = 650;
        }

        holder.titleText.setText(maintitle.get(position));
        holder.yearText.setText(movies.get(position).getYear());
        holder.typeText.setText(movies.get(position).getType());

        if (drawableResourceId != 0) {
            holder.image.setImageResource(drawableResourceId);
        } else {
            holder.image.setImageResource(R.drawable.ic_action_cancel);
        }

        holder.deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                removeItem(holder.getAdapterPosition());
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getContext(), MovieDetailsActivity.class);
                if ((movies.get(position).getImdbId().equals(null)) &&
                    (!movies.get(position).getImdbId().equals("noid"))) {
                    intent.putExtra("id", movies.get(position).getImdbId());
                    context.startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(context.getContext(), "No ID", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void removeItem(int position) {
        if (position == -1)
            return;
        this.movies.remove(position);
        this.maintitle.remove(position);
        notifyDataSetChanged();
    }
}
