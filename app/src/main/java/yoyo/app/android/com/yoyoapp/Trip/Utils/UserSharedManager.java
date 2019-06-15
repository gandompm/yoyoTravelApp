package yoyo.app.android.com.yoyoapp.Trip.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import yoyo.app.android.com.yoyoapp.DataModels.User;


public class UserSharedManager {
    private static final String TAG = "UserSharedManagerFlight";
    private static final String SHARED_PREF_NAME = "user_shared_pref";
    private SharedPreferences sharedPreference;
    private Context context;


    public UserSharedManager(Context context) {
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveUser(User user)
    {
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.putString("id",user.getId());
        editor.putString("firstName",user.getFirstName());
        editor.putString("lastName",user.getLastName());
        editor.putString("email", user.getEmail());
        editor.putString("username", user.getUserName());
        editor.putString("phonenumber", user.getPhoneNumber());
        if (user.getProfilePicture() != null) {
            editor.putString("profile_picture",user.getProfilePicture());
        }
        if (user.getToken() != null) {
            editor.putString("token", user.getToken());
        }
        editor.apply();
    }

    public User getUser()
    {
        User user=new User();
        user.setId(sharedPreference.getString("id",""));
        user.setFirstName(sharedPreference.getString("firstName",""));
        user.setLastName(sharedPreference.getString("lastName",""));
        user.setEmail(sharedPreference.getString("email",""));
        user.setUserName(sharedPreference.getString("username",""));
        user.setLanguage(sharedPreference.getString("language","en"));
        user.setLanguage(sharedPreference.getString("phonenumber",""));
        user.setProfilePicture(sharedPreference.getString("profile_picture",""));

        return user;
    }

    public String getToken()
    {
        return sharedPreference.getString("token","");
    }

    public String getLanguage(){return  sharedPreference.getString("language", "en");}

    public void clearSharedPref()
    {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.clear().commit();
    }

    public void saveProfilePhoto(String uri) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString("profile_picture", uri);
        editor.apply();
    }
}
