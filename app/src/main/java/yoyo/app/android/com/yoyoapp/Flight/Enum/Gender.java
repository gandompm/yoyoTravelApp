package yoyo.app.android.com.yoyoapp.Flight.Enum;

public enum Gender  {
    MALE("MALE", 0),
    FEMALE("FEMALE", 1);

    private String stringValue;
    private int intValue;
    Gender(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
