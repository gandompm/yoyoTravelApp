package yoyo.app.android.com.yoyoapp.Flight.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import yoyo.app.android.com.yoyoapp.R;


public class CheckInternetConnection   {
    private Context context;
    private View view;
    private Snackbar snackbar;
    private OnInternetConnected onInternetConnected;


    public CheckInternetConnection(Context context, View view, OnInternetConnected onInternetConnected) {
        this.context = context;
        this.view = view;
        this.onInternetConnected = onInternetConnected;
        checkingInternetConnection();
    }

    private void checkingInternetConnection() {
        snackbar = Snackbar.make(view, context.getString(R.string.tap_to_retry), Snackbar.LENGTH_INDEFINITE);
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // user has internet connection and call onconnected method with true
            onInternetConnected.onConnected(true);
            snackbar.dismiss();
        }
        else
        {
            // user doesn't have internet connection, so show snack bar
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            TextView errorDatePicker = sbView.findViewById(R.id.snackbar_text);
            errorDatePicker.setTextColor(ContextCompat.getColor(context, R.color.red));

            snackbar.setAction("retry", onRetryClicked -> {
                // check connection again, when the user click retry
                snackbar.dismiss();
                checkingInternetConnection();
            });

            snackbar.show();
            // user does not have internet connection and call onconnected method with false
            onInternetConnected.onConnected(false);
        }
    }
    public interface OnInternetConnected
    {
        void onConnected(boolean result);
    }

}
