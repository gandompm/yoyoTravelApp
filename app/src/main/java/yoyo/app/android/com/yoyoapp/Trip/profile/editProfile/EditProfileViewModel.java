package yoyo.app.android.com.yoyoapp.Trip.profile.editProfile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.Trip.authentication.AuthRepository;

public class EditProfileViewModel extends AndroidViewModel {

    private EditProfileRepository repository;
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<User> newUserMutableLiveData;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        repository = EditProfileRepository.getInstanse(getApplication());
    }


    public void initGetProfile() {
        userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData = repository.getProfile();
    }

    public LiveData<User> getProfile() {
        return userMutableLiveData;
    }

    public void initEditProfile(JSONObject jsonObject) {
        newUserMutableLiveData = new MutableLiveData<>();
        newUserMutableLiveData = repository.editProfile(jsonObject);
    }

    public LiveData<User> getEditedProfile() {
        return newUserMutableLiveData;
    }

}
