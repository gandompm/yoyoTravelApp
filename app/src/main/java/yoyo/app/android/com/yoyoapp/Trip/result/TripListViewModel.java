package yoyo.app.android.com.yoyoapp.Trip.result;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;

import java.util.List;

public class TripListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Trip>> tripArraylist;
    private MutableLiveData<Integer> tripCountMutableLiveData;
    private TripListRepository tripListRepository;

    public TripListViewModel(@NonNull Application application) {
        super(application);
        tripListRepository = TripListRepository.getInstance(getApplication());
    }

    public void initTripList(int page, TripQuery tripQuery)
    {
        tripArraylist = new MutableLiveData<>();
        tripCountMutableLiveData = new MutableLiveData<>();
        tripArraylist = tripListRepository.getTripList(page, tripQuery);
    }

    public LiveData<List<Trip>> getTripList()
    {
        return tripArraylist;
    }


}
