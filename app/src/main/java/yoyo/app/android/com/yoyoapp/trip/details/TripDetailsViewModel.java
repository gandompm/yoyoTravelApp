package yoyo.app.android.com.yoyoapp.trip.details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;

public class TripDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Trip> tripMutableLiveData;
    private TripDetailsRepository tripDetailsRepository;

    public TripDetailsViewModel(@NonNull Application application) {
        super(application);
        tripDetailsRepository = TripDetailsRepository.getInstance(getApplication());
    }

    public void initDetails(String tripId) {
        tripMutableLiveData = new MutableLiveData<>();
        tripMutableLiveData = tripDetailsRepository.getDetails(tripId);
    }

    public LiveData<Trip> getDetails() {
        return tripMutableLiveData;
    }
}
