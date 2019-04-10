package yoyo.app.android.com.yoyoapp.Flight.Enum;

public enum FilterFlight {
    PRICE("adult_price", 0),
    PRICEDEC("-adult_price", 3),
    DEPARTURETIME("departure_time", 1),
    DEPARTURETIMEDEC("-departure_time", 4),
    CAPACITYDEC("-capacity", 2),
    CAPACITY("capacity", 5);

    private String stringValue;
    private int intValue;
    FilterFlight(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
