package yoyo.app.android.com.yoyoapp.Trip.profile.profile;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;

public class ProfileRepository {

    private ApiService apiService;
    private static ProfileRepository instance;
    private Context context;

    public static ProfileRepository getInstance(Context context)
    {
        if (instance == null) {
            instance = new ProfileRepository(context);
        }
        return instance;
    }

    private ProfileRepository(Context context)
    {
        this.context = context;
        apiService = new ApiService(context);
    }

}
