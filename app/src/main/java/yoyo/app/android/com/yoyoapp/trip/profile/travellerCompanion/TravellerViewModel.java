package yoyo.app.android.com.yoyoapp.trip.profile.travellerCompanion;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;


import java.util.List;

public class TravellerViewModel extends AndroidViewModel {

    private TravellerRepository repository;
    private MutableLiveData<List<Traveller>> travellerMutableList;
    private MutableLiveData<Boolean> isTravellerAdded;
    private MutableLiveData<Boolean> isTravellerDeleted;
    private MutableLiveData<Boolean> isTravellerEdited;

    public TravellerViewModel(@NonNull Application application) {
        super(application);
        repository = TravellerRepository.getInstanse(getApplication());
    }

    public void initGetTravellers() {
        travellerMutableList = new MutableLiveData<>();
        travellerMutableList = repository.getTravellers();
    }

    public LiveData<List<Traveller>> getTravellers() {
        return travellerMutableList;
    }

    public void initAddTraveller(JSONObject jsonObject) {
        isTravellerAdded = new MutableLiveData<>();
        isTravellerAdded = repository.isTravellerAdded(jsonObject);
    }

    public LiveData<Boolean> getIsTravellerAdded() {
        return isTravellerAdded;
    }

    public void initDeleteTraveller(String travllerId) {
        isTravellerDeleted = new MutableLiveData<>();
        isTravellerDeleted = repository.isTravellerDeleted(travllerId);
    }

    public LiveData<Boolean> getDeleteResult() {
        return isTravellerDeleted;
    }

    public void initEditTraveller(String travllerId, JSONObject jsonObject) {
        isTravellerEdited = new MutableLiveData<>();
        isTravellerEdited = repository.isTravellerEdited(travllerId,jsonObject);
    }

    public LiveData<Boolean> getEditedResult() {
        return isTravellerEdited;
    }
}
