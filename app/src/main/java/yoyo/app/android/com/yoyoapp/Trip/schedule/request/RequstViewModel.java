package yoyo.app.android.com.yoyoapp.Trip.schedule.request;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;

public class RequstViewModel extends AndroidViewModel {

    private RequestRepository repository;
    private MutableLiveData<Boolean> isSuccessfull;

    public RequstViewModel(@NonNull Application application) {
        super(application);
        repository = RequestRepository.getInstance(application);
    }

    public void initRequest(String tripId, JSONObject jsonObject) {
        isSuccessfull = new MutableLiveData<>();
        isSuccessfull = repository.getResult(tripId,jsonObject);
    }

    public LiveData<Boolean> getResult() {
        return isSuccessfull;
    }
}
