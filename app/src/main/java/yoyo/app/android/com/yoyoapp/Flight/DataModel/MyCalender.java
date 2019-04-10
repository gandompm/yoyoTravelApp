package yoyo.app.android.com.yoyoapp.Flight.DataModel;

public class MyCalender {
    private String DayOfTheWeek;
    private String day;
    private String standardDate;
    private String DayOfWeekFarsi;
    private String dayFarsi;
    private String bestPrice;
    private boolean isMin;

    public String getDayOfTheWeek() {
        return DayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.DayOfTheWeek = dayOfTheWeek;
    }

    public String getMinPrice() {
        return bestPrice;
    }

    public void setMinPrice(String minPrice) {
        this.bestPrice = minPrice;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStandardDate() {
        return standardDate;
    }

    public void setStandardDate(String standardDate) {
        this.standardDate = standardDate;
    }

    public boolean isMin() {
        return isMin;
    }

    public void setMin(boolean min) {
        isMin = min;
    }

    public String getDayOfWeekFarsi() {
        return DayOfWeekFarsi;
    }

    public void setDayOfWeekFarsi(String dayOfWeekFarsi) {
        DayOfWeekFarsi = dayOfWeekFarsi;
    }

    public String getDayFarsi() {
        return dayFarsi;
    }

    public void setDayFarsi(String dayFarsi) {
        this.dayFarsi = dayFarsi;
    }
}
