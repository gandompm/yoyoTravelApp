package yoyo.app.android.com.yoyoapp.Trip.booking;

import android.content.Context;
import androidx.core.util.Consumer;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;


public class BookingPresenter {

    private static final String TAG = "BookingPresenter";
    private Context context;
    private ApiServiceFlight apiServiceFlight;

    public BookingPresenter(Context context) {
        this.context = context;
        apiServiceFlight = new ApiServiceFlight(context);
    }


    // checking all fields for travellers in traveller list
    public boolean checkingEmptyItems(String fullNameString, String emailString, String phoneNumberString) {
        if (fullNameString == null || fullNameString.equals(""))
        {
            Toasty.error(context,"full name can not be empty").show();
            return false;
        }
        if (emailString == null || emailString.equals(""))
        {
            Toasty.error(context,context.getResources().getString(R.string.email_cannot_be_empty)).show();
            return false;
        }
        if (phoneNumberString == null || phoneNumberString.equals(""))
        {
            Toasty.error(context,context.getResources().getString(R.string.phone_number_cannot_be_empty)).show();
            return false;
        }
        return true;
    }

}
