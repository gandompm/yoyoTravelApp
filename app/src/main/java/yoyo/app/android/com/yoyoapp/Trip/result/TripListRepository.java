package yoyo.app.android.com.yoyoapp.Trip.result;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;

import java.util.List;

public class TripListRepository {

    private Context context;
    private ApiService apiService;
    private static TripListRepository instance;
    private MutableLiveData<List<Trip>> tripListMutableLiveData;

    public TripListRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }

    public static TripListRepository getInstance(Context context) {
        if (instance == null)
        {
            instance = new TripListRepository(context);
        }
        return instance;
    }

    public MutableLiveData<List<Trip>> getTripList(int page, TripQuery tripQuery)
    {
        tripListMutableLiveData = new MutableLiveData<>();
        setTripList(page ,tripQuery);
        return tripListMutableLiveData;
    }

    private void setTripList(int page, TripQuery tripQuery) {
        apiService.getTripListRequest(page, tripQuery,tripList ->
                tripListMutableLiveData.postValue(tripList));
    }


}
