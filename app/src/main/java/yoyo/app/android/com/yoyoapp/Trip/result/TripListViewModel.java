package yoyo.app.android.com.yoyoapp.Trip.result;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;

import java.util.List;

public class TripListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Trip>> tirpArraylist;
    private TripListRepository tirpListRepository;

    public TripListViewModel(@NonNull Application application) {
        super(application);
        tirpListRepository = TripListRepository.getInstance(getApplication());
    }

    public void initTripList(TripQuery tripQuery)
    {
        tirpArraylist = new MutableLiveData<>();
        tirpArraylist = tirpListRepository.getTripList(tripQuery);
    }

    public LiveData<List<Trip>> getTripList()
    {
        return tirpArraylist;
    }


}
