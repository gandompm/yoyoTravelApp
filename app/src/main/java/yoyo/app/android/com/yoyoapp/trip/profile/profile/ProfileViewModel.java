package yoyo.app.android.com.yoyoapp.trip.profile.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository profileRepository;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = ProfileRepository.getInstance(getApplication());
    }


}
