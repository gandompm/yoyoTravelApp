package yoyo.app.android.com.yoyoapp.Flight.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.User;


public class UserSharedManagerFlight {
    private static final String TAG = "UserSharedManagerFlight";
    private static final String SHARED_PREF_NAME = "user_shared_pref";
    private SharedPreferences sharedPreference;
    private Context context;


    public UserSharedManagerFlight(Context context) {
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveClient(User user)
    {
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.putString("firstName",user.getFirstName());
        editor.putString("lastName",user.getLastName());
        editor.putString("email", user.getEmail());
        editor.putString("emailOrPassword", user.getUserName());
        if (user.getToken() != null) {
            editor.putString("token", user.getToken());
            editor.putString("language", String.valueOf(user.getLanguage()));
        }
        editor.apply();
    }

    public User getClient()
    {
        User user=new User();
        user.setFirstName(sharedPreference.getString("firstName",""));
        user.setLastName(sharedPreference.getString("lastName",""));
        user.setEmail(sharedPreference.getString("email",""));
        user.setUserName(sharedPreference.getString("emailOrPassword",""));
        user.setLanguage(sharedPreference.getString("language","en"));


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

    public void saveLanguage(String language)
    {
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.putString("language", language);
        editor.apply();
    }
}
