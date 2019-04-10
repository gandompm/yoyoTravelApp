package yoyo.app.android.com.yoyoapp;

import android.content.Context;

public class ApiService {
    private static final String TAG = "ApiService";

    private Context context;
    private String url = "http://192.168.1.62:8000/rest_auth/google/";


    public ApiService(Context context) {
        this.context = context;
    }

}

