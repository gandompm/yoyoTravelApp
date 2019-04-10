package yoyo.app.android.com.yoyoapp.Flight.FlightDetails;

import android.content.Context;
import android.widget.LinearLayout;
import androidx.core.util.Consumer;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Flight;
import yoyo.app.android.com.yoyoapp.Flight.Utils.CheckInternetConnection;

public class FlightDetailsPresenter {

    private Context context;
    private ApiServiceFlight apiServiceFlight;
    private LinearLayout linearLayout;

    public FlightDetailsPresenter(Context context, LinearLayout linearLayout)
    {
        this.context = context;
        apiServiceFlight = new ApiServiceFlight(context);
        this.linearLayout = linearLayout;
    }

    // get flight details
    public void getFlightDetails(int flightId, Consumer<Flight> onNewResult) {

        // check internet connection first
        new CheckInternetConnection(context, linearLayout, new CheckInternetConnection.OnInternetConnected() {
            @Override
            public void onConnected(boolean result) {
                if (result)
                {
                    apiServiceFlight.sendFlightsDetailsRequest(flightId, new ApiServiceFlight.OnDetailsFlightRecieved() {
                        @Override
                        public void onRecieved(Flight flight) {
                            if (flight != null)
                            {
                                onNewResult.accept(flight);
                            }
                        }
                    });
                }
            }
        });
                  //fake data for details
//                departureDateTextview.setText("2019-01-29");
//                flightNumberTextview.setText("D34");
//                departureTimeTextview.setText("09:30");
//                departureTimeTopTextview.setText("09:30");
//                capacityTextview.setText(String.valueOf("16"));
//                airlineTextview.setText("Mahan Air");
//                aircraftTextview.setText("Boeing(740)");
//                originCityTextview.setText("Tehran");
//                destinationCityTextview.setText("Mashhad");
//                flightPathTextview.setText("Mehrabad - Hashminejad");
//                originIata.setText("TEH");
//                destinationIata.setText("MSH");
//                adultPriceTextview.setText("260$");
//                childPriceTextview.setText("260$");
//                infantPriceTextview.setText("150$");
//                flightTypeTextview.setText("Charter");
    }

    // pre reserve flight request
    public void preReserveFlight(int flightId, int adultNum, int childNum, int infantNum, Consumer<String> onNewResult)
    {
        new CheckInternetConnection(context, linearLayout, new CheckInternetConnection.OnInternetConnected() {
            @Override
            public void onConnected(boolean result) {
                if (result) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("flight", flightId);
                        jsonObject.put("adults", adultNum);
                        jsonObject.put("child", childNum);
                        jsonObject.put("infants", infantNum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    apiServiceFlight.sendPreReserve(jsonObject, new ApiServiceFlight.OnPreReserved() {
                        @Override
                        public void onReserved(String voucherNumber) {
                            if (voucherNumber != null) {
                                onNewResult.accept(voucherNumber);
                            }
                        }
                    });
                }
            }
        });
    }
}
