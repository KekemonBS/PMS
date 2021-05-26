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
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.arasthel.spannedgridlayoutmanager.SpanSize;
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import kotlin.jvm.functions.Function1;
import ua.kpi.comsys.iv8106.R;
import ua.kpi.comsys.iv8106.adapters.GalleryAdapter;
import ua.kpi.comsys.iv8106.model.ImageItem;
import ua.kpi.comsys.iv8106.requester.Requester;

public class GalleryFragment extends Fragment {

    private int RESULT_LOAD_IMG = 1;
    private static String response;


    //public ArrayList<Bitmap> images = new ArrayList<>();
    Type listOfImagesItemsType = new TypeToken<ArrayList<ImageItem>>() {}.getType();
    ArrayList<ImageItem> images   = new ArrayList<>();


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
        ProgressBar spinner = (ProgressBar)view.findViewById(R.id.progressBar);
        spinner.setVisibility(ProgressBar.VISIBLE);

        recycle.setLayoutManager(spannedGridLayoutManager);
        recycle.setAdapter(adapter_gallery);


        //----------------------------------------------------------------------------------
        //Perform request in separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                String formattedUrlString = "https://pixabay.com/api/?key=%s&q=%s&image_type=photo&per_page=%s";
                String apiKey = "19193969-87191e5db266905fe8936d565";
                String request = "yellow+flowers";
                String count = "27";
                Queue<String> queue = new LinkedList<>();
                Requester req = new Requester(queue, formattedUrlString, apiKey, request, count);
                Thread th1 = new Thread(req, "images");
                th1.start();
                try {
                    th1.join();
                    setJSONResponse(queue.remove());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(response);

                //Update data (view was already retrieved by now for shure)
                Gson gson = new Gson();
                if (response != null &&
                        gson.fromJson(response, JsonObject.class).has("hits")) {
                    spinner.setVisibility(ProgressBar.INVISIBLE);
                    JsonObject gsontmp = gson.fromJson(response, JsonObject.class);
                    images.clear();
                    images.addAll(gson.fromJson(gsontmp.get("hits"), listOfImagesItemsType));

                } else {
                    images.clear();
                    images.clear();
                }
                //Notify that data changed
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter_gallery.notifyDataSetChanged();
                    }
                });

                spinner.setVisibility(ProgressBar.INVISIBLE);
            }
        }).start();
        //----------------------------------------------------------------------------------

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        System.out.println("HERE1");

        return view;
    }

    public static void setJSONResponse(String JSON) {
        response = JSON;
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
                ImageItem selectedimageItem = new ImageItem();
                selectedimageItem.setBitmap(scaled);
                images.add(selectedimageItem);
                adapter_gallery.notifyDataSetChanged();
            }
        }
    }
}