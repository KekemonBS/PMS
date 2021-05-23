package ua.kpi.comsys.iv8106.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.arasthel.spannedgridlayoutmanager.SpanSize;
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;

import java.io.IOException;
import java.util.ArrayList;

import kotlin.jvm.functions.Function1;
import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.adapters.GalleryAdapter;

public class GalleryFragment extends Fragment {

    private int RESULT_LOAD_IMG = 1;

    public ArrayList<Bitmap> images = new ArrayList<>();
    public GalleryAdapter adapter_gallery = new GalleryAdapter(this, images);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        RecyclerView recycle = view.findViewById(R.id.galleryRecyclerView);
        recycle.setNestedScrollingEnabled(false);

        SpannedGridLayoutManager spannedGridLayoutManager = new SpannedGridLayoutManager(
              SpannedGridLayoutManager.Orientation.VERTICAL, 3);
        spannedGridLayoutManager.setItemOrderIsStable(false);

        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>(){
            @Override public SpanSize invoke(Integer position) {
                if (position % 9 == 0) {
                    return new SpanSize(2, 2);
                } else if ((position - 7)  % 9 == 0) {
                    return new SpanSize(2, 2);
                } else {
                    return new SpanSize(1, 1);
                }
            }
        }));

        ImageButton addButton = view.findViewById(R.id.moreImageButton);

        recycle.setLayoutManager(spannedGridLayoutManager);
        recycle.setAdapter(adapter_gallery);


        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent picker) {
        super.onActivityResult(requestCode, resultCode, picker);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = picker.getData();
                Bitmap selectedImage = null;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(
                            getContext().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap scaled = selectedImage.createScaledBitmap(selectedImage,
                        (int)Math.ceil(selectedImage.getWidth()/2), (int)Math.ceil(selectedImage.getHeight()/2), false);
                images.add(scaled);
                adapter_gallery.notifyDataSetChanged();
            }
        }
    }
}