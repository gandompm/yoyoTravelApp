package yoyo.app.android.com.yoyoapp.Trip.details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;

public class TripDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Trip> tirpMutableLiveData;
    private TripDetailsRepository tirpDetailsRepository;

    public TripDetailsViewModel(@NonNull Application application) {
        super(application);
        tirpDetailsRepository = TripDetailsRepository.getInstance(getApplication());
    }

    public void initDetails(String tripId) {
        tirpMutableLiveData = new MutableLiveData<>();
        tirpMutableLiveData = tirpDetailsRepository.getDetails(tripId);
    }

    public LiveData<Trip> getDetails() {
        return tirpMutableLiveData;
    }
}
