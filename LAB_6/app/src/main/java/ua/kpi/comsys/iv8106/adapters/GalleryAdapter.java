package ua.kpi.comsys.iv8106.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ua.kpi.comsys.iv8106.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final Fragment fragment;
    private final ArrayList<Bitmap> images;

    public GalleryAdapter(Fragment fragment, ArrayList<Bitmap> images) {
        this.fragment = fragment;
        this.images = images;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView iw;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iw = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image, parent, false);

        return new GalleryAdapter.GalleryViewHolder(view);
    }
//-------------------------------------------------------------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.GalleryViewHolder holder, int position) {
        //holder.itemView.getLayoutParams().height = 650;
        //holder.iw.setImageURI(images.get(position));

        holder.iw.setImageBitmap(images.get(position));
    }
//-------------------------------------------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        return images.size();
    }

}
