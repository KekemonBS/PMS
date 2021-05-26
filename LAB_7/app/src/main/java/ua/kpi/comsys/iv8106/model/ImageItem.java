package ua.kpi.comsys.iv8106.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class ImageItem {
    private boolean isVisible = true;

    private Bitmap bitmap = null;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private Uri     imgpath;

    private String id;
    private String pageURL;
    private String type;
    private String tags;
    private String previewURL;
    private String previewWidth;
    private String previewHeight;
    private String webformatURL;
    private String webformatWidth;
    private String webformatHeight;
    private String largeImageURL;
    private String imageWidth;
    private String imageHeight;
    private String imageSize;
    private String views;
    private String downloads;
    private String favorites;
    private String likes;
    private String comments;
    private String user_id;
    private String user;
    private String userImageURL;

    public boolean isVisible() {
        return isVisible;
    }

    public Uri getImgpath() {
        return imgpath;
    }

    public String getId() {
        return id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getPreviewWidth() {
        return previewWidth;
    }

    public String getPreviewHeight() {
        return previewHeight;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getWebformatWidth() {
        return webformatWidth;
    }

    public String getWebformatHeight() {
        return webformatHeight;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public String getImageSize() {
        return imageSize;
    }

    public String getViews() {
        return views;
    }

    public String getDownloads() {
        return downloads;
    }

    public String getFavorites() {
        return favorites;
    }

    public String getLikes() {
        return likes;
    }

    public String getComments() {
        return comments;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

}
