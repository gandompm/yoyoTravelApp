package yoyo.app.android.com.yoyoapp.trip.profile.travellerCompanion;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.trip.ApiService;

import java.util.List;

public class TravellerRepository {

    private ApiService apiService;
    private MutableLiveData<List<Traveller>> travellerMutableLiveData;
    private MutableLiveData<Boolean> isTravellerAdded;
    private MutableLiveData<Boolean> isTravellerDeleted;
    private MutableLiveData<Boolean> isTravellerEdited;
    private Context context;
    private static TravellerRepository instanse;

    public static TravellerRepository getInstanse(Context context) {
        if (instanse == null) {
            instanse =  new TravellerRepository(context);
        }
        return instanse;
    }

    public TravellerRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }


    public MutableLiveData<List<Traveller>> getTravellers() {
        travellerMutableLiveData = new MutableLiveData<>();
        setTravellers();
        return travellerMutableLiveData;
    }

    private void setTravellers() {
        apiService.getTravellersCompanionsRequest(travellers ->
                travellerMutableLiveData.postValue(travellers));
    }

    public MutableLiveData<Boolean> isTravellerAdded(JSONObject jsonObject) {
        isTravellerAdded = new MutableLiveData<>();
        addTraveller(jsonObject);
        return isTravellerAdded;
    }

    private void addTraveller(JSONObject jsonObject) {
        apiService.sendAddTravellerCompanionRequest(jsonObject, isAdded ->
        {
            isTravellerAdded.postValue(isAdded);
        });
    }

    public MutableLiveData<Boolean> isTravellerDeleted(String travllerId) {
        isTravellerDeleted = new MutableLiveData<>();
        deleteTraveller(travllerId);
        return isTravellerDeleted;
    }

    private void deleteTraveller(String travllerId) {
        apiService.sendDeleteTravellerCompanionRequest(travllerId,isDeleted->{
           isTravellerDeleted.postValue(isDeleted);
        });
    }

    public MutableLiveData<Boolean> isTravellerEdited(String travllerId, JSONObject jsonObject) {
        isTravellerEdited = new MutableLiveData<>();
        editTraveller(travllerId,jsonObject);
        return isTravellerEdited;
    }

    private void editTraveller(String travllerId, JSONObject jsonObject) {
        apiService.sendEditTravellerCompanionRequest(travllerId, jsonObject ,result->{
            isTravellerEdited.postValue(result);
        });
    }
}
