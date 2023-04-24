package yoyo.app.android.com.yoyoapp.DataModels;

import android.text.format.DateFormat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Locale;

public class Product {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("start_datetime")
    @Expose
    private long startDatetime;
    @SerializedName("end_datetime")
    @Expose
    private long endDatetime;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("qty")
    @Expose
    private int qty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(long startDatetime) {
        this.startDatetime = startDatetime;
    }

    public long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(long endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}