package yoyo.app.android.com.yoyoapp.DataModels;

import java.util.ArrayList;

public class Order {
    private boolean isHotelOrder;
    private boolean isFlightOrder;
    private String name;
    private String state;
    private String type;
    private String totalPrice;
    private String orderNumber;
    private String orderTime;
    private String personSum;
    private String roomSum;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPersonSum() {
        return personSum;
    }

    public void setPersonSum(String personSum) {
        this.personSum = personSum;
    }

    public boolean isHotelOrder() {
        return isHotelOrder;
    }

    public void setHotelOrder(boolean hotelOrder) {
        isHotelOrder = hotelOrder;
    }

    public boolean isFlightOrder() {
        return isFlightOrder;
    }

    public void setFlightOrder(boolean flightOrder) {
        isFlightOrder = flightOrder;
    }

    public String getRoomSum() {
        return roomSum;
    }

    public void setRoomSum(String roomSum) {
        this.roomSum = roomSum;
    }

    public static ArrayList<Order> getFakeData()
    {
        ArrayList<Order> orders= new ArrayList<>();
        for (int i = 0; i < 8; i++) {

            Order order = new Order();
            order.setName("Isfahan Dinner Party with Locals");

            switch (i)
            {
                case 0:
                    order.setName("Hotel Javaher");
                    order.setHotelOrder(true);
                    order.setRoomSum("2 * Person");
                case 3:
                    order.setName("Hotel Javaher");
                    order.setHotelOrder(true);
                    order.setRoomSum("2 * Person");
                case 7:
                    order.setName("Hotel Javaher");
                    order.setHotelOrder(true);
                    order.setRoomSum("2 * Person");
                    default:
                    {
                        order.setOrderNumber("#1223222");
                        order.setOrderTime("2019.1.20");
                        order.setState("available");
                        order.setTotalPrice("203 $");
                        order.setPersonSum("2 * Person");
                    }
            }


            orders.add(order);
        }
        return orders;
    }
}
