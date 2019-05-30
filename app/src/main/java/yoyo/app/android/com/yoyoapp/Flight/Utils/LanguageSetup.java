package yoyo.app.android.com.yoyoapp.Flight.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.User;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.MainActivity;

import java.util.Locale;

public class LanguageSetup {

    private Context context;

    public LanguageSetup(Context context) {
        this.context = context;
    }

    public void setLocale(String lang) {
        changeLocale(lang);
        Intent refresh = new Intent(context, MainActivity.class);
        context.startActivity(refresh);
    }

    // change the local of app
    private void changeLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
        saveLangSharedPref(lang);
    }

    private void saveLangSharedPref(String language) {
        UserSharedManagerFlight userSharedManagerFlight = new UserSharedManagerFlight(context);
        userSharedManagerFlight.saveLanguage(language);
    }

    public void loadLanguageFromSharedPref()
    {
        UserSharedManagerFlight userSharedManagerFlight = new UserSharedManagerFlight(context);
        User user = userSharedManagerFlight.getClient();
        changeLocale(user.getLanguage());
    }
}
