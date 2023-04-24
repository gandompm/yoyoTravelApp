package yoyo.app.android.com.yoyoapp.Flight.DataModel;

public class Ticket {

    private String state;
    private String totalPrice;
    private String voucherNumber;
    private String personSum;
    private String destination;
    private String departure;
    private String date;
    private String downloadUrl;
    private int adultNum;
    private int childNum;
    private int infantNum;



    public int getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(int adultNum) {
        this.adultNum = adultNum;
    }

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    public int getInfantNum() {
        return infantNum;
    }

    public void setInfantNum(int infantNum) {
        this.infantNum = infantNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getPersonSum() {
        return personSum;
    }

    public void setPersonSum(String personSum) {
        this.personSum = personSum;
    }


    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
