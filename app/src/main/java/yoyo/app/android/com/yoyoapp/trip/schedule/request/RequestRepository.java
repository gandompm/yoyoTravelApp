package yoyo.app.android.com.yoyoapp.trip.schedule.request;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.trip.ApiService;

public class RequestRepository{

    private static RequestRepository instance;
    private MutableLiveData<Boolean> isSuccessfull;
    private ApiService apiService;
    private Context context;

    public RequestRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }

    public static RequestRepository getInstance(Context context) {
        if (instance == null) {
            instance = new RequestRepository(context);
        }
        return instance;
    }

    public MutableLiveData<Boolean> getResult(String tripId, JSONObject jsonObject) {
        isSuccessfull = new MutableLiveData<>();
        setResult(tripId,jsonObject);
        return isSuccessfull;
    }

    private void setResult(String tripId, JSONObject jsonObject) {
        apiService.sendTripRequest(tripId,jsonObject,result-> {
            isSuccessfull.postValue(result);
        });
    }
}
