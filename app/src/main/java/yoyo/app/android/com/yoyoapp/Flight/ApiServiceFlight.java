package yoyo.app.android.com.yoyoapp.Flight;

import android.content.Context;
import android.util.Log;
import androidx.core.util.Consumer;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.*;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiServiceFlight {
    private static final String TAG = "ApiServiceFlight";
    public static String IP = "yoyoapp.ir";
    private String apiKey = "4626d28e5ef2edcc21e7d4d371fb91be0620bebf";
    private String JWT;
    private UserSharedManagerFlight userSharedManager;
    private Context context;
    private String language;

    public ApiServiceFlight(Context context) {
        this.context = context;
        userSharedManager = new UserSharedManagerFlight(context);
        language = userSharedManager.getLanguage();
        JWT = userSharedManager.getToken();
    }

    public void setupSignUp(JSONObject jsonObject, final OnSignupComplete onSignupComplete) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://"+ IP +"/api/mobile/account/register/", jsonObject, response -> {
           {
                User user = new User();
                try {
                    user.setFirstName(response.getString("first_name"));
                    user.setLastName(response.getString("last_name"));
                    user.setUserName(response.getString("username"));
                    user.setEmail(response.getString("email"));
                    user.setToken(response.getString("token"));
                    user.setExpireDate(response.getString("expires"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

               onSignupComplete.onSignUp(user);
            }
        }, error -> {
            onSignupComplete.onSignUp(null);
            NetworkResponse networkResponse = error.networkResponse;

            if (networkResponse != null && networkResponse.data != null) {
                String jsonError = new String(networkResponse.data);
                Log.d(TAG, "onErrorResponse: 11111yyyyyyyyy " + jsonError);

                Toasty.error(context,jsonError).show();
                error.printStackTrace();
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("api-key", apiKey);
                headers.put("Content-Type", "application/json");
                headers.put("Accept-Language", language);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public interface OnSignupComplete {
        void onSignUp(User user);
    }

    public void setupSignIn(JSONObject jsonObject, Consumer<String> tokenConsumer){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://" + IP + "/api/mobile/account/login/", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e(TAG, "onresult poi: " + response);
                    tokenConsumer.accept(response.getString("token"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tokenConsumer.accept(null);
                NetworkResponse networkResponse = error.networkResponse;

                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);

                    Toasty.error(context,jsonError).show();
                    error.printStackTrace();
                }
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("api-key", apiKey);
                headers.put("Content-Type", "application/json");
                headers.put("Accept-Language", language);
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendSearchFlightsRequest(String queryStreem,
                                         final OnFlightsRecieved onFlightsRecieved)
    {
        Log.d(TAG, "sendSearchFlightsRequest: nnnnnnf  "+ queryStreem);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + queryStreem,null,

                response -> {

                    ArrayList<Flight> flights = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("flights");
                        Log.d(TAG, "onResponse erererer: "+ jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Flight flight = new Flight();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        flight.setFlightId(jsonObject.getInt("flight_id"));
                        flight.setFlightNumber(jsonObject.getString("flight_number"));
                        flight.setDepartureTime(jsonObject.getString("departure_time"));
                        flight.setAdultPrice(jsonObject.getString("adult_price"));
                        flight.setCapacity(jsonObject.getInt("capacity"));
                        flight.setType(jsonObject.getString("type"));
                        flight.setUpdatedAt(jsonObject.getString("updated_at"));
                        flight.setAirline(jsonObject.getString("airline"));
                        flight.setOriginIataCode(jsonObject.getString("origin_iata_code"));
                        flight.setDestinationIataCode(jsonObject.getString("destination_iata_code"));
                        JSONObject jsonObjectAircraft = jsonObject.getJSONObject("aircraft");
                        flight.setAirCraftName(jsonObjectAircraft.getString("model"));
                        flight.setAirCraftManufacturer(jsonObjectAircraft.getString("manufacturer"));
                        Log.d(TAG, "onResponse: nnnnnnnnnnnnn" + jsonObjectAircraft.getString("model") + "  " + jsonObjectAircraft.getString("manufacturer"));
                        flight.setAirlineLogo(jsonObject.getString("airline_logo"));

                        flights.add(flight);
                    }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onFlightsRecieved.onRecieved(flights);

                }, error -> {
                    Log.d(TAG, "onResponse erererer: "+ error.toString());
                    onFlightsRecieved.onRecieved(null);
                    error.printStackTrace();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Content-Type", "application/json");
                params.put("Accept-Language", "en");
                return params;
            }
        };
        

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnFlightsRecieved
    {
        void onRecieved(ArrayList<Flight> flights);
    }


    public void sendFlightsDetailsRequest(int flightId, final OnDetailsFlightRecieved onDetailsFlightRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/flights/one-way/"+flightId,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Flight flight = new Flight();
                        try {
                            flight.setFlightId(response.getInt("flight_id"));
                            flight.setDapartureDate(response.getString("departure_date"));
                            flight.setFlightNumber(response.getString("flight_number"));
                            flight.setDepartureTime(response.getString("departure_time"));
                            flight.setAdultPrice(response.getString("adult_price"));
                            flight.setChildPrice(response.getString("child_price"));
                            flight.setInfantPrice(response.getString("infant_price"));
                            flight.setCapacity(response.getInt("capacity"));
                            flight.setType(response.getString("type"));
                            flight.setAirline(response.getString("airline"));
                            JSONObject originObject = response.getJSONObject("origin");
                            JSONObject destinationObject = response.getJSONObject("destination");
                            flight.setOriginIataCode(originObject.getString("iata_code"));
                            flight.setOriginAirport(originObject.getString("name"));
                            JSONObject oriCityObject = originObject.getJSONObject("city");
                            flight.setOriginCity(oriCityObject.getString("name"));
                            flight.setDestinationIataCode(destinationObject.getString("iata_code"));
                            flight.setDestinationAirport(destinationObject.getString("name"));
                            JSONObject desCityObject = destinationObject.getJSONObject("city");
                            flight.setDestinationCity(desCityObject.getString("name"));
                            JSONObject jsonObjectAircraft = response.getJSONObject("aircraft");
                            flight.setAirCraftName(jsonObjectAircraft.getString("model"));
                            flight.setAirCraftManufacturer(jsonObjectAircraft.getString("manufacturer"));
                            flight.setAirlineLogo(response.getString("airline_logo"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        onDetailsFlightRecieved.onRecieved(flight);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                onDetailsFlightRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Content-Type", "application/json");
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnDetailsFlightRecieved
    {
        void onRecieved(Flight flight);
    }


    public void getTravellersCompanions( final OnTravellersRecieved onTravellersRecieved)
    {
         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/account/passengers/",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<Traveller> travellers = new ArrayList<>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            Log.d(TAG, "onResponse: "+ jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Traveller traveller = new Traveller();

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                traveller.setTravellerId(jsonObject.getInt("id"));
                                traveller.setFirstName(jsonObject.getString("english_first_name"));
                                traveller.setLastName(jsonObject.getString("english_last_name"));
                                traveller.setAgeClass(jsonObject.getString("age_class"));
                                traveller.setGender(jsonObject.getString("gender"));
                                traveller.setDateOfBirth(jsonObject.getString("date_of_birth"));
                                traveller.setPassportNumber(jsonObject.getString("passport_number"));
                                traveller.setIranianNationalCode(jsonObject.getString("iranian_national_code"));
                                traveller.setNationality(jsonObject.getString("nationality") + " (" + jsonObject.getString("nationality_code")+")");

                                if (jsonObject.getString("nationality").toUpperCase().contains("IR")||
                                        jsonObject.getString("nationality").toUpperCase().contains("ایران"))
                                    traveller.setIranian(true);
                                else
                                    traveller.setIranian(false);

                                travellers.add(traveller);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onTravellersRecieved.onRecieved(travellers);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                onTravellersRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", "en");
                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnTravellersRecieved
    {
        void onRecieved(ArrayList<Traveller> travellers);
    }

    public void addTravellerCompanion(final Traveller traveller, final OnTravellerAdded onTravellerAdded)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("english_first_name",traveller.getFirstName());
            jsonObject.put("english_last_name",traveller.getLastName());
            jsonObject.put("gender",traveller.getGender());
            jsonObject.put("date_of_birth",traveller.getDateOfBirth());
            jsonObject.put("nationality",traveller.getNationality());

            if (traveller.isIranian())
            {
                jsonObject.put("iranian_national_code",traveller.getIranianNationalCode());
                jsonObject.put("passport_number",JSONObject.NULL);
            }
            else
            {
                jsonObject.put("passport_number",traveller.getPassportNumber());
                jsonObject.put("iranian_national_code",JSONObject.NULL);
            }
            jsonObject.put("age_class",traveller.getAgeClass());
            Log.e(TAG, "onseqqqqqqqqqqq: "+ jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "http://" + IP + "/api/mobile/account/passengers/",jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponseqqqqqqqqqq: "+ response);
                        onTravellerAdded.onAdded(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e(TAG, "onErrorResponseqqqqqqqqqqq: "+ e.toString());
                onTravellerAdded.onAdded(false);
                e.printStackTrace();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    interface OnTravellerAdded
    {
        void onAdded(Boolean result);
    }

    public void editTravellerCompanion(Traveller traveller, final OnTravellerEdited onTravellerEdited)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("english_first_name",traveller.getFirstName());
            jsonObject.put("english_last_name",traveller.getLastName());
            jsonObject.put("gender",traveller.getGender());
            jsonObject.put("date_of_birth",traveller.getDateOfBirth());
            jsonObject.put("age_class",traveller.getAgeClass());
            jsonObject.put("nationality",traveller.getNationality());

            if (traveller.isIranian())
            {
                jsonObject.put("iranian_national_code",traveller.getIranianNationalCode());
                jsonObject.put("passport_number",JSONObject.NULL);
            }
            else
            {
                jsonObject.put("passport_number",traveller.getPassportNumber());
                jsonObject.put("iranian_national_code",JSONObject.NULL);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                "http://" + IP + "/api/mobile/account/passengers/" + traveller.getTravellerId() ,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        onTravellerEdited.onEdited(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                Log.d(TAG, "onErrorResponse: po1"+ e.toString());
                onTravellerEdited.onEdited(false);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    interface OnTravellerEdited
    {
        void onEdited(Boolean result);
    }

    public void deleteTravellerCompanion(int travellerId, final OnTravellerDeleted onTravellerDeleted)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                "http://" + IP + "/api/mobile/account/passengers/" + travellerId ,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse:   true dddddd ");
                        onTravellerDeleted.onDeleted(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                //todo Fix get it out from on error
                onTravellerDeleted.onDeleted(true);
                Log.d(TAG, "onErrorResponseddddddd: "+ e.toString());
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };



        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    interface OnTravellerDeleted
    {
        void onDeleted(Boolean result);
    }

    public void getCityList(final OnCitiesRecieved onCitiesRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/gis/cities/?limit=10000",null,
                response -> {

                    ArrayList<City> cities = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            City city = new City();

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            city.setName(jsonObject.getString("name"));
                            city.setCode(jsonObject.getString("code"));


                            cities.add(city);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    onCitiesRecieved.onRecieved(cities);
                }, error -> {


                    onCitiesRecieved.onRecieved(null);
                    error.printStackTrace();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnCitiesRecieved
    {
        void onRecieved(ArrayList<City> cities);
    }

    public void signout(final onSignOutDone onSignOutDone)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/account/logout/",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        onSignOutDone.onDone(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                onSignOutDone.onDone(false);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    interface onSignOutDone{
        void onDone(boolean result);
    }

    public void getAirlineList(final OnAirlinesRecieved onAirlinesRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/flights/one-way/airlines/",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<Airline> airlines = new ArrayList<>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Airline airline = new Airline();

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                airline.setAirline(jsonObject.getString("name"));
                                airline.setIataCode(jsonObject.getString("iata_code"));
                                airline.setAirlineLogo(jsonObject.getString("logo"));

                                airlines.add(airline);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onResponse: ccccccccc "+ airlines);
                        onAirlinesRecieved.onRecieved(airlines);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                Log.d(TAG, "onResponse: ccccccccc "+ e.toString());
                onAirlinesRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnAirlinesRecieved
    {
        void onRecieved(ArrayList<Airline> airlines);
    }

    public void getAirCraftList(final OnAircraftsRecieved onAircraftsRecieved)
    {
        Log.d(TAG, "getAirCraftList: qqqqqqq1");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/flights/one-way/manufacturers/",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<AirCraft> airCrafts = new ArrayList<>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                AirCraft airCraft = new AirCraft();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                airCraft.setManufacturer(jsonObject.getString("name"));
                                Log.d(TAG, "onResponse: nnnnnnnnnnnnn" + jsonObject.getString("name"));
                                airCraft.setId(jsonObject.getInt("id"));

                                airCrafts.add(airCraft);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onAircraftsRecieved.onRecieved(airCrafts);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(TAG, "getAirCraftList: qqqqqqq3  " + e.toString());
                onAircraftsRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnAircraftsRecieved
    {
        void onRecieved(ArrayList<AirCraft> airCrafts);
    }

    public void getBestPricesForCalender(String originCityCode, String destinationCityCode, String startDate, String endDate , final OnBestPricesRecivied onBestPricesRecivied)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/flights/one-way/best-prices/?origin_city_code="+originCityCode+"&destination_city_code="+destinationCityCode+"&start_date="+startDate+"&end_date=" + endDate,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "onResponse: qwer1");
                        ArrayList<MyCalender> myCalenders = new ArrayList<>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                MyCalender myCalender = new MyCalender();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                myCalender.setStandardDate(jsonObject.getString("departure_date"));
                                myCalender.setMinPrice(jsonObject.getString("min_price"));
                                myCalender.setMin(jsonObject.getBoolean("is_min"));

                                myCalenders.add(myCalender);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onBestPricesRecivied.onRecieved(myCalenders);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                Log.d(TAG, "onResponse: qwer2");
                onBestPricesRecivied.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Accept-Language", "en");
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }
    public interface OnBestPricesRecivied
    {
        void onRecieved(ArrayList<MyCalender> myCalenders);
    }

    public void sendPreReserve(JSONObject jsonObject, final OnPreReserved onPreReserved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "http://" + IP + "/api/mobile/order/flight/pre-reserve/",jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.d(TAG, "onResponse: "+ response.getString("voucher_number"));
                            onPreReserved.onReserved(response.getString("voucher_number"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(TAG, "onErrorResponse: " + e.toString());
                onPreReserved.onReserved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnPreReserved
    {
        void onReserved(String voucherNumber);
    }

    public void sendBookFlightRequset(JSONObject jsonObject, String voucherNumber, final OnBookFlightCallback onBookFlightCallback)
    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                "http://" + IP + "/api/mobile/order/flight/book/" + voucherNumber,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                            onBookFlightCallback.onBooked(null);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                NetworkResponse networkResponse = e.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {

                    Log.d(TAG, "onErrorResponse: yyyyyyyyy" + e.toString());
                    String jsonError = new String(networkResponse.data);

                    onBookFlightCallback.onBooked(jsonError);
                    e.printStackTrace();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnBookFlightCallback
    {
        void onBooked(String message);
    }

    public void getTickets(String urlParameter, final OnTicketsRecieved onTicketsRecieved)
    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/flights/one-way/tickets/" + urlParameter, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "onResponseqq11: ");
                        Log.d(TAG, "onResponse: ggggggg" + response);
                        ArrayList<Ticket> tickets = new ArrayList<>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            Log.d(TAG, "onResponse: ggggggg" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Ticket ticket = new Ticket();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.d(TAG, "onResponse: ggggggg" + jsonObject);

                                ticket.setTotalPrice(jsonObject.getString("total_price"));
                                ticket.setDownloadUrl(jsonObject.getString("download_url"));
                                ticket.setDeparture(jsonObject.getString("origin_city"));
                                ticket.setDestination(jsonObject.getString("destination_city"));
                                ticket.setDate(jsonObject.getString("departure_date"));
                                ticket.setVoucherNumber(jsonObject.getString("voucher_number"));
                                JSONObject jsonObject2 = jsonObject.getJSONObject("counts");
                                ticket.setAdultNum(jsonObject2.getInt("ADULT"));
                                ticket.setChildNum(jsonObject2.getInt("CHILD"));
                                ticket.setInfantNum(jsonObject2.getInt("INFANT"));

                                tickets.add(ticket);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        onTicketsRecieved.onRecieved(tickets);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(TAG, "onErrorResponse: " + e.toString());
                onTicketsRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                if (JWT != "")
                params.put("Authorization", "JWT " + JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnTicketsRecieved
    {
        void onRecieved(ArrayList<Ticket> tickets);
    }


    public void editProfile(JSONObject jsonObject, final OnEditRequest onEditRequest)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                "http://" + IP + "/api/mobile/account/" ,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        User user = new User();
                        try {
                            user.setFirstName(response.getString("first_name"));
                            user.setLastName(response.getString("last_name"));
                            user.setUserName(response.getString("user_name"));
                            user.setEmail(response.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onEditRequest.onResult(user);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(TAG, "onErrorResponse: " + e.toString());
                onEditRequest.onResult(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnEditRequest
    {
        void onResult(User user);
    }

    public void retrieveProfileData( final OnProfileDataRecieved onProfileDataRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/account/",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        User user = new User();
                        try {
                            user.setFirstName(response.getString("first_name"));
                            user.setLastName(response.getString("last_name"));
                            user.setUserName(response.getString("username"));
                            user.setEmail(response.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onProfileDataRecieved.onRecieved(user);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                onProfileDataRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnProfileDataRecieved
    {
        void onRecieved(User user);
    }

    public void getPriceDetails(String voucherNumber ,final OnPriceDetailsRecieved onPriceDetailsRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/flights/one-way/price-details/" + voucherNumber , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Payment payment = new Payment();
                        try {
                            payment.setDeadlineTime(response.getString("payment_deadline"));
                            payment.setOriginCity(response.getString("origin_city"));
                            payment.setDestinationCity(response.getString("destination_city"));
                            payment.setTotalPrice(response.getString("total_price"));

                            JSONObject jsonObject = new JSONObject();
                            jsonObject = response.getJSONObject("counts");
                            JSONObject adultJsonObject = jsonObject.getJSONObject("ADULT");
                            payment.setAdultCount((adultJsonObject.getInt("count")));
                            payment.setAdultPrice((adultJsonObject.getInt("price")));
                            JSONObject childJsonObject = jsonObject.getJSONObject("CHILD");
                            payment.setChildCount((childJsonObject.getInt("count")));
                            payment.setChildPrice((childJsonObject.getInt("price")));
                            JSONObject infantJsonObject = jsonObject.getJSONObject("INFANT");
                            payment.setInfantCount((infantJsonObject.getInt("count")));
                            payment.setInfantPrice((infantJsonObject.getInt("price")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onPriceDetailsRecieved.onRecieved(payment);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(TAG, "onErrorResponse: " + e.toString());
                onPriceDetailsRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnPriceDetailsRecieved
    {
        void onRecieved(Payment payment);
    }

    public void getPaymentUrl(String voucherNumber ,final OnPaymentUrlRecieved onPaymentUrlRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                "http://" + IP + "/api/mobile/payment/init-pay/" + voucherNumber , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            onPaymentUrlRecieved.onRecieved(response.getString("payment_url"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(TAG, "onErrorResponse: " + e.toString());
                onPaymentUrlRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnPaymentUrlRecieved
    {
        void onRecieved(String paymentUrl);
    }

    public void getCountryList(final OnCountriesRecieved onCountriesRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/gis/countries/?limit=10000",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<Country> countries = new ArrayList<>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Country country = new Country();

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                country.setName(jsonObject.getString("name"));
                                country.setCode(jsonObject.getString("code"));


                                countries.add(country);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        onCountriesRecieved.onRecieved(countries);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                onCountriesRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnCountriesRecieved
    {
        void onRecieved(ArrayList<Country> countries);
    }


    public void getPaymentResult(String paymentId,final OnPaymentResultRecieved onPaymentResultRecieved)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://" + IP + "/api/mobile/payment/pay-result/"+ paymentId,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        FinalPayment finalPayment = new FinalPayment();

                        try {
                            finalPayment.setAuthority(response.getString("authority"));
                            finalPayment.setOrder(response.getString("order"));
                            finalPayment.setReason(response.getString("reason"));
                            finalPayment.setStatus(response.getString("status"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        onPaymentResultRecieved.onRecieved(finalPayment);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

                onPaymentResultRecieved.onRecieved(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("api-key", apiKey);
                params.put("Authorization", "JWT "+ JWT);
                params.put("Accept-Language", language);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public interface OnPaymentResultRecieved
    {
        void onRecieved(FinalPayment finalPayment);
    }














}
