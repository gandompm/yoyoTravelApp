package yoyo.app.android.com.yoyoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import yoyo.app.android.com.yoyoapp.DataModels.Client;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class UserSharedManager {
    private static final String SHARED_PREF_NAME = "user_shared_pref";
    private SharedPreferences sharedPreference;
    private Context context;


    public UserSharedManager(Context context) {
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        context = context;
    }

    public void saveClient(Client client)
    {
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.putString("firstName",client.getFirstName());
        editor.putString("lastName",client.getLastName());
        editor.putString("email", client.getEmail());
        editor.putString("country", client.getCountry());
        editor.putString("phoneNumber", client.getPhoneNumber());
        editor.putString("dob", String.valueOf(client.getDob()));
        editor.putString("picture", String.valueOf(client.getPicture()));
        editor.putString("language", String.valueOf(client.getLanguage()));
        editor.apply();
    }

    public Client getClient()
    {
        Client client=new Client();
        client.setFirstName(sharedPreference.getString("firstName",""));
        client.setLastName(sharedPreference.getString("lastName",""));
        client.setEmail(sharedPreference.getString("email",""));
        client.setCountry(sharedPreference.getString("country",""));
        client.setPhoneNumber(sharedPreference.getString("phoneNumber",""));
        client.setPicture(sharedPreference.getString("picture",""));
        client.setLanguage(sharedPreference.getString("language","en"));
        //client.setDob((Date)sharedPreference.getString("d",""));

        return client;
    }

    public void clearSharedPref()
    {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.clear().commit();
    }

    public void saveDataToSharedPrefrence(GoogleSignInAccount acct) {

        if (acct != null) {
            Client client = new Client();
            client.setFirstName(acct.getGivenName());
            client.setLastName(acct.getFamilyName());
            client.setPicture(String.valueOf(acct.getPhotoUrl()));
            client.setEmail(acct.getEmail());

            saveClient(client);
        }
    }

    public void savaLanguage(String language)
    {
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.putString("language", language);
        editor.apply();
    }



}
