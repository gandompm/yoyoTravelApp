package yoyo.app.android.com.yoyoapp.Booking;

import android.content.Context;
import android.os.Bundle;
import androidx.core.util.Consumer;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Traveller;
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

    // get payment result
    public void setupPayment(String paymentId, Consumer<Bundle> bundleConsumer) {

            apiServiceFlight.getPaymentResult(paymentId, finalPayment -> {
                if (finalPayment!=null)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("status",finalPayment.getStatus());
                    bundle.putString("authority",finalPayment.getAuthority());
                    bundle.putString("reason",finalPayment.getReason());
                    bundle.putString("order",finalPayment.getOrder());
                    bundleConsumer.accept(bundle);
                }
            });
    }

    // checking all fields for travellers in traveller list
    public int checkingEmptyItems(String emailString, String phoneNumberString, ArrayList<Traveller> travellers) {
        int i = 0;
        if (emailString == null || emailString.equals(""))
        {
            Toasty.error(context,context.getResources().getString(R.string.email_cannot_be_empty)).show();
            return i;
        }
        if (phoneNumberString == null || phoneNumberString.equals(""))
        {
            Toasty.error(context,context.getResources().getString(R.string.phone_number_cannot_be_empty)).show();
            return i;
        }
        while (travellers.size() > i)
        {
            Traveller traveller = travellers.get(i);
            if (traveller.getFirstName() != null &&
                    traveller.getLastName() != null)
            {
                if (!traveller.getFirstName().equals("") &&
                        !traveller.getLastName().equals("") )
                {
                    if (traveller.isIranian())
                    {
                        if (traveller.getIranianNationalCode() != null) {
                            if (traveller.getIranianNationalCode().equals(""))
                                return i;
                        }
                        else
                            return i;
                    }
                    else
                    {
                        if (traveller.getPassportNumber() != null) {
                            if (traveller.getPassportNumber().equals("")) {
                                return i;
                            }
                        }
                        else
                            return i;
                    }
                    if (traveller.getAgeClass().equals("INFANT"))
                    {
                        if (traveller.getDateOfBirth() == null || traveller.getDateOfBirth().equals(""))
                        {
                            return i;
                        }
                    }
                }
                else
                {
                    return i;
                }
            }
            else
            {
                return i;
            }
            i++;
        }
        return 200;
    }

    // send payment request and get zarinpall url
    public void sendPaymentRequest(String voucherNumber, Consumer<String> urlConsumer) {

        apiServiceFlight.getPaymentUrl(voucherNumber, paymentUrl -> {
            if (paymentUrl != null)
            {
                urlConsumer.accept(paymentUrl);
            }
        });
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
                int iend = item.getNationality().indexOf("(");
                js.put("nationality",item.getNationality().substring(iend+1,item.getNationality().indexOf(")")));
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

                if (item.getAgeClass().equals("INFANT")){
                    js.put("date_of_birth", item.getDateOfBirth());
                }

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
