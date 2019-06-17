package yoyo.app.android.com.yoyoapp.DataModels;
import android.text.format.DateFormat;
import android.widget.ScrollView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TourRequest {

    @SerializedName("buyer")
    @Expose
    private String buyer;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("schedule_id")
    @Expose
    private String scheduleId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("passengers")
    @Expose
    private int passengers;
    @SerializedName("status")
    @Expose
    private String status;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(createdAt * 1000);
        String eDate = DateFormat.format("dd-MM-yyyy", cal).toString();
        this.createdAt = eDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}