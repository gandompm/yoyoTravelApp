package yoyo.app.android.com.yoyoapp.DataModels;

import yoyo.app.android.com.yoyoapp.Utils;

import java.util.ArrayList;

public class TripQuery {

    private String type;
    private Long fromTime;
    private Long toTime;
    private int fromPrice;
    private int toPrice;
    private int minDuration;
    private String location;
    private ArrayList<String> categories;



    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public Long getFromTime() {
        return fromTime;
    }

    public void setFromTime(Long fromTime) {
        this.fromTime = fromTime;
    }

    public Long getToTime() {
        return toTime;
    }

    public void setToTime(Long toTime) {
        this.toTime = toTime;
    }

    public int getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(int fromPrice) {
        this.fromPrice = fromPrice;
    }

    public int getToPrice() {
        return toPrice;
    }

    public void setToPrice(int toPrice) {
        this.toPrice = toPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }
}
