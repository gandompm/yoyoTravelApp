package yoyo.app.android.com.yoyoapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import yoyo.app.android.com.yoyoapp.BottomSheet.SignUpBottomSheet;

import org.json.JSONException;
import org.json.JSONObject;



public class ApiService {
    private static final String TAG = "ApiService";

    private Context context;
    private String url = "http://192.168.1.62:8000/rest_auth/google/";


    public ApiService(Context context) {
        this.context = context;
    }

    public void sendAndRequestResponse(final OnRequest onRequest) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_token",SignUpBottomSheet.idToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                onRequest.onSend("success");
                Log.d(TAG, "onResponse: aaaaaaaaaaaaaaaaaaaaaaaaaaa ");
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onRequest.onSend(error.toString());
                        Log.d(TAG, "onErrorResponse: bvbbbbbbbbbbbbbbbbbbbbbbbb" + error.getMessage());
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendTestRequest(String authC) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("grant_type","authorization_code");
            jsonObject.put("client_id","181467561755-7uedee591j3jcb4pj09u79t70akkkn0t.apps.googleusercontent.com");
            jsonObject.put("client_secret","xYMjVnf6Z8M-xICjHaoFYoGI");
            jsonObject.put("redirect_uri","");
            jsonObject.put("code",authC);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://www.googleapis.com/oauth2/v4/token", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.d(TAG, "onResponse: aaaaaaaaaaaaaaaaaaaaaaaaaaa " + response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "onErrorResponse: bvbbbbbbbbbbbbbbbbbbbbbbbb" + error.getMessage());
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);





    }

    public interface OnRequest
    {
        void onSend(String s);
    }

    public void getPhotoBannerSlider()
    {

    }
    public void getPhotoCityList()
    {

    }
    public void getToursListResult()
    {

    }
    public void getHotelsListResult()
    {

    }
    public void getClientInfo()
    {

    }
    public void getHotelDetails()
    {

    }
    public void getClientsOrders()
    {

    }
}

