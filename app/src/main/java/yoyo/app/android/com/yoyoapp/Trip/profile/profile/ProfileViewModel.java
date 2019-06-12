package yoyo.app.android.com.yoyoapp.Trip.profile.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;


public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository profileRepository;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = ProfileRepository.getInstance(getApplication());
    }


}
