package yoyo.app.android.com.yoyoapp.Trip.details;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;

public class TripDetailsRepository{

    private MutableLiveData<Trip> tripMutableLiveData;
    private Context context;
    private ApiService apiService;
    private static TripDetailsRepository instance;

    public static TripDetailsRepository getInstance(Context context)
    {
        if (instance == null) {
            return new TripDetailsRepository(context);
        }
        else
            return instance;
    }

    public TripDetailsRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }


    public MutableLiveData<Trip> getDetails(String tripId) {
        tripMutableLiveData = new MutableLiveData<>();
        setTripDetails(tripId);
        return tripMutableLiveData;
    }

    private void setTripDetails(String tripId) {
//        apiService.getTripDetailsRequest( tripId , trip -> {
//            tripMutableLiveData.postValue(trip);
//        });
    }
}
