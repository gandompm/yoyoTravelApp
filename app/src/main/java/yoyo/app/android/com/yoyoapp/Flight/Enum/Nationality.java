package yoyo.app.android.com.yoyoapp.Flight.Enum;

public enum Nationality {


    IRANIAN("IRANIAN", 2),
    FOREIGNER("FOREIGNER", 1);

    private String stringValue;
    private int intValue;
    Nationality(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public int getIntValue()
    {
        return intValue;
    }



}
