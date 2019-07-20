package yoyo.app.android.com.yoyoapp.trip.Utils;

import android.content.Context;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import co.infinum.princeofversions.*;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.UpdateDialogFragment;

import java.util.Map;

public class Versioning {
    private static final String TAG = "Versioning";


    public Versioning() {
    }

    public void checkingUpdates(final Context context)
    {

        PrinceOfVersions updater = new PrinceOfVersions(context);
        Loader loader = new NetworkLoader("http://www.yoyo.travel/media/app/version.json");


        UpdaterCallback callback = new UpdaterCallback() {
            @Override
            public void onNewUpdate(String version, boolean isMandatory, Map<String, String> metadata) {
                Log.d(TAG, "onNewUpdate: yyyyyyyyyyyy metadata  " + metadata);
                Log.d(TAG, "onNewUpdate: yyyyyyyyyyyy version   " + version);
                Log.d(TAG, "onNewUpdate: yyyyyyyyyyyy ismandatory   " + isMandatory);


                UpdateDialogFragment updateDialogFragment = new UpdateDialogFragment();
                updateDialogFragment.setMessage("Please update your app to version" + " " + version);
                updateDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(),"update dialog");
            }

            @Override
            public void onNoUpdate(Map<String, String> metadata) {
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "onError: yyyyyyyyyy  throw    "  + throwable.toString());
                Log.d(TAG, "onError: yyyyyyyyyyyy error  ");
            }
        };
        PrinceOfVersionsCancelable cancelable = updater.checkForUpdates(loader, callback);
    }



}
