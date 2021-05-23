package ua.kpi.comsys.iv8106.model;

import android.net.Uri;

public class ImageItem {
    private boolean isVisible = true;

    private Uri imgpath;

    private String Title;
    private String Year;
    private String Type;
    private String id;

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getType() {
        return Type;
    }

    public Uri getImgPath() {
        return imgpath;
    }

    public String getId() { return id; }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
