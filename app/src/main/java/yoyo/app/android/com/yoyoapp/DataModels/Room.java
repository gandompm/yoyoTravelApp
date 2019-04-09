package yoyo.app.android.com.yoyoapp.DataModels;

import android.util.Log;

public class Room {

    private String type;
    private int num;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int increaseNum()
    {
        return ++num;
    }

    public int decreaseNum() {
        return --num;
    }
}
