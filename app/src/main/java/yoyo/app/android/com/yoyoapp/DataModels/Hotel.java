package yoyo.app.android.com.yoyoapp.DataModels;

import java.util.ArrayList;

public class Hotel {
    private String hotelName;
    private String hotelAddress;
    private String hotelType;
    private String hotelOldPrice;
    private String hotelNewPrice;
    private float hotelStar;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public String getHotelOldPrice() {
        return hotelOldPrice;
    }

    public void setHotelOldPrice(String hotelOldPrice) {
        this.hotelOldPrice = hotelOldPrice;
    }

    public String getHotelNewPrice() {
        return hotelNewPrice;
    }

    public void setHotelNewPrice(String hotelNewPrice) {
        this.hotelNewPrice = hotelNewPrice;
    }

    public float getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(float hotelStar) {
        this.hotelStar = hotelStar;
    }

    public static ArrayList<Hotel> fakeHotelData()
    {
        ArrayList<Hotel> hotels = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Hotel hotel = new Hotel();
            hotel.setHotelName("Ferdosi Grand Hotel");
            hotel.setHotelType("Hotel");
            hotel.setHotelAddress("Tehran, Ferdosi Sq.");
            hotel.setHotelOldPrice("40$");
            hotel.setHotelNewPrice("38$");
            hotel.setHotelStar((float)2.5);

            hotels.add(hotel);
        }
        return hotels;
    }
}
