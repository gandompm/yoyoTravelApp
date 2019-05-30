package yoyo.app.android.com.yoyoapp.Trip.authentication;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;

public class AuthRepository {

    private ApiService apiService;
    private MutableLiveData<User> signInMutableLiveData;
    private MutableLiveData<User> userMutableLiveData;
    private Context context;
    private static AuthRepository instanse;

    public static AuthRepository getInstanse(Context context) {
        if (instanse == null) {
            instanse =  new AuthRepository(context);
        }
        return instanse;
    }

    public AuthRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }


    public MutableLiveData<User> getSignIn(JSONObject jsonObject) {
        signInMutableLiveData = new MutableLiveData<>();
        setSignIn(jsonObject);
        return signInMutableLiveData;
    }

    private void setSignIn(JSONObject jsonObject) {
        apiService.sendSignInRequest(jsonObject, signIn -> {
            signInMutableLiveData.postValue(signIn);
        });
    }

    public MutableLiveData<User> getSignUp(JSONObject jsonObject) {
        userMutableLiveData = new MutableLiveData<>();
        setSignUp(jsonObject);
        return userMutableLiveData;
    }

    private void setSignUp(JSONObject jsonObject) {
        apiService.sendSignUpRequest(jsonObject, user ->
                userMutableLiveData.postValue(user));
    }
}
