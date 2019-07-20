package yoyo.app.android.com.yoyoapp.trip.profile.editProfile;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.trip.ApiService;

public class EditProfileRepository {

    private ApiService apiService;
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<User> newUserMutableLiveData;
    private MutableLiveData<String> profilePicture;
    private Context context;
    private static EditProfileRepository instanse;

    public static EditProfileRepository getInstanse(Context context) {
        if (instanse == null) {
            instanse =  new EditProfileRepository(context);
        }
        return instanse;
    }

    public EditProfileRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }


    public MutableLiveData<User> getProfile() {
        userMutableLiveData = new MutableLiveData<>();
        setProfile();
        return userMutableLiveData;
    }

    private void setProfile() {
        apiService.getProfileRequest(user ->
                userMutableLiveData.postValue(user));
    }

    public MutableLiveData<User> editProfile(JSONObject jsonObject) {
        newUserMutableLiveData = new MutableLiveData<>();
        setEditProfile(jsonObject);
        return newUserMutableLiveData;
    }

    private void setEditProfile(JSONObject jsonObject) {
        apiService.sendEditProfileRequest(jsonObject, user -> {
            newUserMutableLiveData.postValue(user);
        });
    }


    public MutableLiveData<String> sendImageProfile(JSONObject jsonObject)
    {
        profilePicture = new MutableLiveData<>();
        setImageProfile(jsonObject);
        return profilePicture;
    }

    private void setImageProfile(JSONObject jsonObject) {
        apiService.sendProfilePhotoRequest(jsonObject, url -> {
            profilePicture.postValue(url);
        });
    }
}
