package ua.kpi.comsys.iv8106.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.model.ImageItem;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final Fragment             fragment;
    private final ArrayList<ImageItem> images;

    public GalleryAdapter(Fragment fragment, ArrayList<ImageItem> images) {
        this.fragment = fragment;
        this.images = images;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView iw;
        private ProgressBar spinnerImg;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iw = (ImageView) itemView.findViewById(R.id.image);
            this.spinnerImg = (ProgressBar)itemView.findViewById(R.id.progressBarImg);

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

        holder.spinnerImg.setVisibility(ProgressBar.VISIBLE);
        if (images.get(position).getBitmap() == null) {
            Picasso.get()
                   .load(images.get(position).getWebformatURL())/*.placeholder()*/
                   .into(holder.iw, new Callback() {
                       @Override
                       public void onSuccess() {
                           holder.spinnerImg.setVisibility(ProgressBar.INVISIBLE);
                       }

                       @Override
                       public void onError(Exception e) {

                       }
                   });
        } else {
            holder.iw.setImageBitmap(images.get(position).getBitmap());
            holder.spinnerImg.setVisibility(ProgressBar.INVISIBLE);
        }

    }
//-------------------------------------------------------------------------------------------------------------
    @Override
    public int getItemCount() {
        return images.size();
    }

}
