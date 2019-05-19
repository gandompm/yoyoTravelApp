package yoyo.app.android.com.yoyoapp.Trip.result;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.ApiService;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;

import java.util.List;

public class TripListRepository {

    private Context context;
    private ApiService apiService;
    private static TripListRepository instance;
    private MutableLiveData<List<Trip>> tirpListMutableLiveData;

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

    public MutableLiveData<List<Trip>> getTripList(TripQuery tripQuery)
    {
        tirpListMutableLiveData = new MutableLiveData<>();
        setTripList(tripQuery);
        return tirpListMutableLiveData;
    }

    private void setTripList(TripQuery tripQuery) {
        apiService.getTripListRequest(tripQuery,tirpList ->
                tirpListMutableLiveData.postValue(tirpList));
    }


}
