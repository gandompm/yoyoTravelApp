package yoyo.app.android.com.yoyoapp.trip.authentication;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;

public class AuthViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    private MutableLiveData<User> signInMutableLiveData;
    private MutableLiveData<User> userMutableLiveData;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstanse(getApplication());
    }


    public void initSignIn(JSONObject jsonObject) {
        signInMutableLiveData = new MutableLiveData<>();
        signInMutableLiveData = authRepository.getSignIn(jsonObject);
    }

    public LiveData<User> getSignInResult() {
        return signInMutableLiveData;
    }

    public void initSignUp(JSONObject jsonObject) {
        userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData = authRepository.getSignUp(jsonObject);
    }

    public LiveData<User> getSignUpResult() {
        return userMutableLiveData;
    }
}
