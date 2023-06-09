package yoyo.app.android.com.yoyoapp.DataModels;
import android.text.format.DateFormat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TourTicket {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_amount")
    @Expose
    private Float totalAmount;
    @SerializedName("order_timestamp")
    @Expose
    private String orderDatetime;
    @SerializedName("is_paid")
    @Expose
    private boolean isPaid;
    @SerializedName("reservatore_name")
    @Expose
    private String reservatoreName;
    @SerializedName("reservatore_email")
    @Expose
    private String reservatoreEmail;
    @SerializedName("reservatore_phone")
    @Expose
    private String reservatorePhone;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(long orderDatetime) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(orderDatetime * 1000);
        String eDate = DateFormat.format("dd-MM-yyyy", cal).toString();
        this.orderDatetime = eDate;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getReservatoreName() {
        return reservatoreName;
    }

    public void setReservatoreName(String reservatoreName) {
        this.reservatoreName = reservatoreName;
    }

    public String getReservatoreEmail() {
        return reservatoreEmail;
    }

    public void setReservatoreEmail(String reservatoreEmail) {
        this.reservatoreEmail = reservatoreEmail;
    }

    public String getReservatorePhone() {
        return reservatorePhone;
    }

    public void setReservatorePhone(String reservatorePhone) {
        this.reservatorePhone = reservatorePhone;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


}