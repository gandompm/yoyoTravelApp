package yoyo.app.android.com.yoyoapp.DataModels;

public class ScheduleCalender {
    private int month;
    private long startDate;
    private long endDate;

    public long getStartDate() {
        return startDate;
    }

    public void setDate(long date) {
        this.startDate = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
