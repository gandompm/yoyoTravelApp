package yoyo.app.android.com.yoyoapp.Flight.Enum;

public enum AgeClass {


    ADULT("ADULT", 1),
    CHILD("CHILD", 2),
    INFANT("INFANT", 3);

    private String stringValue;
    private int intValue;
    AgeClass(String toString, int value) {
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
