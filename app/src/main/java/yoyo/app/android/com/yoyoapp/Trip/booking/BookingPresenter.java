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
    public boolean checkingEmptyItems(String emailString, String phoneNumberString) {
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


    // book flight request and generate a json object
    public void bookFlight(String emailString, String phoneNumberString, ArrayList<Traveller> travellers, Consumer<String> messageConsumer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",emailString);
            jsonObject.put("phone_number",phoneNumberString);
            JSONArray jsonArray = new JSONArray();
            for (Traveller item : travellers)
            {
                JSONObject js = new JSONObject();
                js.put("nationality",item.getNationality());
                if (item.isIranian())
                {
                    js.put("iranian_national_code",item.getIranianNationalCode());
                    js.put("passport_number",JSONObject.NULL);
                }
                else
                {
                    js.put("passport_number",item.getPassportNumber());
                    js.put("iranian_national_code",JSONObject.NULL);
                }

                js.put("gender",item.getGender());
                js.put("english_first_name",item.getFirstName());
                js.put("english_last_name",item.getLastName());
                js.put("age_class",item.getAgeClass());

                js.put("date_of_birth", item.getDateOfBirth());

                jsonArray.put(js);
            }
            jsonObject.put("passengers",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiServiceFlight.sendBookFlightRequset(jsonObject, BookingActivity.voucherNumber, new ApiServiceFlight.OnBookFlightCallback() {
            @Override
            public void onBooked(String message) {
                messageConsumer.accept(message);
            }
        });
    }

}
