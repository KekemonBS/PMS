package ua.kpi.comsys.iv8106.model;

public class MovieItem {
    private boolean isVisible = true;

    private String Title;
    private String Year;
    private String Type;
    private String Poster;
    private String imdbID;

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getType() {
        return Type;
    }

    public String getPoster() {
        return Poster;
    }

    public String getImdbId() { return imdbID; }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
