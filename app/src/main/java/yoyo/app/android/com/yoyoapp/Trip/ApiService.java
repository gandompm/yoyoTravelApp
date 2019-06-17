package yoyo.app.android.com.yoyoapp.Trip;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.core.util.Consumer;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import jp.gr.java_conf.androtaku.countrylist.CountryList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.*;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import java.util.*;

public class ApiService {
    private static final String TAG = "ApiService";
    private Context context;
    private String IMAGEIP = "http://www.yoyo.travel";
    private String IP = "http://www.yoyo.travel/";
    private String JWT;
    private UserSharedManager userSharedManager;

    public ApiService(Context context) {
        this.context = context;
        userSharedManager = new UserSharedManager(context);
        JWT = userSharedManager.getToken();
    }

    public void getTripListRequest(int page, TripQuery tripQuery, Consumer<ArrayList<Trip>> tripArrayListConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/trips" + "?price_min=" +
                tripQuery.getFromPrice()+"&price_max="+tripQuery.getToPrice()+"&start_date="+tripQuery.getFromTime()+
                "&end_date="+tripQuery.getToTime()+"&category="+tripQuery.getCategories() +"&origin=" + tripQuery.getOrigin()+ "&destination=" + tripQuery.getDestination() +
                "&duration_min="+ tripQuery.getMinDuration() +"&duration_max=999&page="+ page +"&limit=10&reserve_type="+ tripQuery.getType(),null,
                response -> {

                    ArrayList<Trip> trips = new ArrayList<>();
                    try {
                    JSONArray tripJsonArray = response.getJSONArray("trips");

                        for (int i = 0; i < tripJsonArray.length(); i++) {


                            for (int k =0 ; k<3 ; k++) {
                                Trip trip = new Trip();


                                JSONObject mainObject = tripJsonArray.getJSONObject(i);
                                trip.setTripId(mainObject.getString("trip_id"));
                                trip.setTitle(mainObject.getString("title"));
                                trip.setNightNum(mainObject.getInt("days"));
                                trip.setDayNum(mainObject.getInt("nights"));
                                trip.setSummary(mainObject.getString("summary"));

                                // leader
                                TripLeader tripLeader = new TripLeader();
                                JSONObject leaderObject = mainObject.getJSONObject("leader");
                                tripLeader.setName(leaderObject.getString("name"));
                                JSONObject pictureObject = leaderObject.getJSONObject("picture");
                                tripLeader.setPicture(IMAGEIP + pictureObject.getString("original_url"));
                                tripLeader.setLanguage(leaderObject.getString("language"));
                                trip.setTripLeader(tripLeader);

                                // tour
                                Tour tour = new Tour();
                                JSONObject tourObject = mainObject.getJSONObject("tour");
                                tour.setName(tourObject.getString("name"));
                                tour.setPassengerCount(tourObject.getInt("passengers_count"));
                                trip.setTour(tour);

                                // agency
                                JSONObject agencyObject = mainObject.getJSONObject("agency");
                                trip.setAgency(agencyObject.getString("name"));

                                // itinerary
                                ArrayList<String> itineraries = new ArrayList<>();
                                JSONArray itineraryJsonArray = mainObject.getJSONArray("itinerary");
                                for (int j = 0; j < itineraryJsonArray.length(); j++) {
                                    itineraries.add(itineraryJsonArray.getString(j));
                                }
                                trip.setItineraries(itineraries);

                                // attraction
                                ArrayList<String> attractios = new ArrayList<>();
                                JSONArray jsonArray = mainObject.getJSONArray("attractions");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    attractios.add(jsonArray.getString(j));
                                }
                                trip.setAttractions(attractios);

                                // transportation
                                ArrayList<String> transportations = new ArrayList<>();
                                jsonArray = mainObject.getJSONArray("transportation");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    transportations.add(jsonArray.getString(j));
                                }
                                trip.setTransportations(transportations);

                                // meals
                                ArrayList<String> meals = new ArrayList<>();
                                jsonArray = mainObject.getJSONArray("meals");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    meals.add(jsonArray.getString(j));
                                }
                                trip.setMeals(meals);

                                // rules
                                ArrayList<String> rules = new ArrayList<>();
                                jsonArray = mainObject.getJSONArray("rules");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    rules.add(jsonArray.getString(j));
                                }
                                trip.setRules(rules);

                                // categories
                                ArrayList<String> categories = new ArrayList<>();
                                jsonArray = mainObject.getJSONArray("categories");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    categories.add(jsonArray.getString(j));
                                }
                                trip.setCategories(categories);

                                // gallery
                                ArrayList<String> galleries = new ArrayList<>();
                                JSONArray galleryJsonArray = mainObject.getJSONArray("gallery");
                                for (int j = 0; j < galleryJsonArray.length(); j++) {
                                    JSONObject galleryObject = galleryJsonArray.getJSONObject(j);
                                    galleries.add(IMAGEIP + galleryObject.getString("original_url"));
                                }
                                trip.setGallery(galleries);

                                // location
                                ArrayList<Location> locations = new ArrayList<>();
                                JSONArray locationJsonArray = mainObject.getJSONArray("locations");
                                for (int j = 0; j < locationJsonArray.length(); j++) {
                                    JSONObject locationObject = locationJsonArray.getJSONObject(j);
                                    Location location = new Location();
                                    location.setTitle(locationObject.getString("title"));
                                    location.setLon(locationObject.getDouble("longitude"));
                                    location.setLat(locationObject.getDouble("latitude"));
                                    location.setOrder(locationObject.getInt("order"));
                                    if (locationObject.getInt("order") == 0)
                                        locations.add(0, location);
                                    else if (locationObject.getInt("order") == -1)
                                        locations.add(locationJsonArray.length() - 1, location);
                                    else
                                        locations.add(location);
                                }
                                trip.setLocations(locations);

                                int tripCount = response.getInt("count");
                                if (trips.size() == 0) {
                                    trip.setResultsSize(tripCount);
                                }

                                trips.add(trip);
                            }
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tripArrayListConsumer.accept(trips);

                }, error -> {
            tripArrayListConsumer.accept(null);
            error.printStackTrace();
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    private Calendar getDateAndTime(Long date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(date * 1000);
        return cal;
    }

    public void getCategoryRequest(Consumer<ArrayList<Category>> categoriesConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/trips/categories" ,null,
                response -> {

                    ArrayList<Category> categories = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("categories");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            Category category = new Category();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            category.setName(jsonObject.getString("name"));
                            category.setCode(jsonObject.getString("code"));
                            categories.add(category);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    categoriesConsumer.accept(categories);

                }, error -> {
            categoriesConsumer.accept(null);
            error.printStackTrace();
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendSignUpRequest(JSONObject jsonObject, Consumer<User> userConsumer){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,  IP + "api/user/register", jsonObject, response -> {
            User user = new User();
            try {
                user.setFirstName(response.getString("firstname"));
                user.setLastName(response.getString("lastname"));
                user.setUserName(response.getString("username"));
                user.setEmail(response.getString("email"));
                user.setPhoneNumber(response.getString("phone_number"));
                user.setToken(response.getString("token"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                userConsumer.accept(user);

        }, error -> {
            userConsumer.accept(null);
            Log.d(TAG, "sendSignUpRequest: aaaaaa" + error.toString());
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendSignInRequest(JSONObject jsonObject, Consumer<User> userConsumer){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,  IP + "api/user/login", jsonObject, response -> {
            User user = new User();
            try {
                user.setFirstName(response.getString("firstname"));
                user.setLastName(response.getString("lastname"));
                user.setUserName(response.getString("username"));
                user.setEmail(response.getString("email"));
                user.setPhoneNumber(response.getString("phone_number"));
                user.setProfilePicture(response.getString("profile_thumbnail_picture"));
                user.setToken(response.getString("token"));

            } catch (JSONException e) {
                e.printStackTrace();

                Log.d(TAG, "sendSignInRequest: login   " + e.toString());
            }
            userConsumer.accept(user);

        }, error -> {
            userConsumer.accept(null);

            Log.d(TAG, "sendSignInRequest: login " + error.toString());
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }



    public void getProfileRequest(Consumer<User> userConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                 IP + "api/user/profile" ,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        User user = new User();
                        try {
                            user.setFirstName(response.getString("firstname"));
                            user.setLastName(response.getString("lastname"));
                            user.setUserName(response.getString("username"));
                            user.setEmail(response.getString("email"));
                            user.setPhoneNumber(response.getString("phone_number"));
                            user.setProfilePicture(response.getString("profile_picture"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        userConsumer.accept(user);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.d(TAG, "onErrorResponse: " + e.toString());
                userConsumer.accept(null);
                e.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendEditProfileRequest(JSONObject jsonObject, Consumer<User> userConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                 IP + "api/user/profile" ,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        User user = new User();
                        try {
                            user.setFirstName(response.getString("firstname"));
                            user.setLastName(response.getString("lastname"));
                            user.setUserName(response.getString("username"));
                            user.setEmail(response.getString("email"));
                            user.setProfilePicture(response.getString("profile_picture"));
                            user.setPhoneNumber(response.getString("phone_number"));

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.d(TAG, "onResponse: edit profile " + e.toString());
                        }

                        userConsumer.accept(user);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                userConsumer.accept(null);
                e.printStackTrace();

                Log.d(TAG, "onErrorResponse: edit profile " + e.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getTravellersCompanionsRequest(Consumer<ArrayList<Traveller>> travellersConsumer)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                 IP + "api/user/profile/passengers",null,
                response -> {

                    ArrayList<Traveller> travellers = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            Traveller traveller = new Traveller();

                            JSONObject jsonObject = response.getJSONObject(i);

                            traveller.setId(jsonObject.getString("passenger_id"));
                            traveller.setFirstName(jsonObject.getString("firstname"));
                            traveller.setLastName(jsonObject.getString("lastname"));
                            traveller.setGender(jsonObject.getString("gender"));
                            traveller.setDateOfBirthTimeStamp(jsonObject.getLong("dob"));
                            Calendar cal = getDateAndTime(jsonObject.getLong("dob"));
                            String eDate = DateFormat.format("dd-MM-yyyy", cal).toString();
                            traveller.setDateOfBirth(eDate);
                            traveller.setPassportNumber(jsonObject.getString("passport_number"));
                            traveller.setIranianNationalCode(jsonObject.getString("national_code"));
                            String countryName = CountryList.convertCodeToName(context, jsonObject.getString("nationality"));
                            traveller.setNationality(countryName);
                            traveller.setIranian(jsonObject.getBoolean("is_iranian"));

                            travellers.add(traveller);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    travellersConsumer.accept(travellers);
                }, e -> {
                    travellersConsumer.accept(null);
                    e.printStackTrace();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void sendAddTravellerCompanionRequest(JSONObject jsonObject, Consumer<Boolean> isTravellerAdded)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                IP + "api/user/profile/passengers",jsonObject,
                response -> {
                    isTravellerAdded.accept(true);
                }, e -> {
            isTravellerAdded.accept(false);
                    e.printStackTrace();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendDeleteTravellerCompanionRequest(String travellerId,Consumer<Boolean> isDeleted)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE,
                 IP + "api/user/profile/passengers/"+ travellerId  ,null,
                response ->
                        isDeleted.accept(true),
                e -> {
                    // TODO: 6/17/2019 fixing delete method
                    isDeleted.accept(true);
                    e.printStackTrace();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendEditTravellerCompanionRequest(String travellerId,JSONObject jsonObject, Consumer<Boolean> isEditedConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                IP + "api/user/profile/passengers/" + travellerId ,jsonObject,
                response -> isEditedConsumer.accept(true),
                e -> {
                    isEditedConsumer.accept(false);
                    e.printStackTrace();
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendProfilePhotoRequest(JSONObject jsonObject, Consumer<String> stringConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, IP + "api/user/profile/picture", jsonObject ,
                response -> {
                    try {
                            stringConsumer.accept(IP + response.getString("profile_thumbnail_picture"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    stringConsumer.accept(null);
                })
        {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ JWT);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_MAX_RETRIES));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }



    public void getScheduleRequest(String tripId,long startDate, long endDate,Consumer<ArrayList<Schedule>> scheduleConsumer)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                IP + "api/trips/"+ tripId+ "?start_date=" + startDate + "&end_date=" + endDate ,null,
                response -> {

                    ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            Schedule schedule = new Schedule();

                            JSONObject jsonObject = response.getJSONObject(i);
                            schedule.setId(jsonObject.getString("schedule_id"));
                            schedule.setPrice(jsonObject.getInt("price"));
                            schedule.setMinCapacity(jsonObject.getInt("min_capacity"));
                            schedule.setMaxCapacity(jsonObject.getInt("max_capacity"));
                            schedule.setRemainingCapacity(jsonObject.getInt("remaining_capacity"));
                            schedule.setStartTimeStamp(jsonObject.getLong("start_timestamp"));
                            schedule.setEndTimeStamp(jsonObject.getLong("end_timestamp"));

                            scheduleArrayList.add(schedule);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    scheduleConsumer.accept(scheduleArrayList);
                }, e -> {
            scheduleConsumer.accept(null);
            e.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void sendTripRequest(String tripId,JSONObject jsonObject, Consumer<Boolean> isRequestSuccessfull)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                IP + "api/trips/request/" + tripId, jsonObject,
                response -> {
                    isRequestSuccessfull.accept(true);
                }, e -> {
            isRequestSuccessfull.accept(false);
            e.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }


    public void getDestinationsRequest(Consumer<ArrayList<Location>> locationConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/trips/destinations" ,null,
                response -> {

                    ArrayList<Location> locations = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("locations");

                        for (int i = 0; i < jsonArray.length(); i++) {


                            Location location = new Location();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            location.setCode(jsonObject.getString("code"));
                            location.setTitle(jsonObject.getString("name"));

                            locations.add(location);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    locationConsumer.accept(locations);

                }, error -> {
            locationConsumer.accept(null);
            error.printStackTrace();
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getOriginsRequest(Consumer<ArrayList<Location>> locationConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/trips/origins" ,null,
                response -> {

                    ArrayList<Location> locations = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("locations");

                        for (int i = 0; i < jsonArray.length(); i++) {


                            Location location = new Location();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            location.setCode(jsonObject.getString("code"));
                            location.setTitle(jsonObject.getString("name"));

                            locations.add(location);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    locationConsumer.accept(locations);

                }, error -> {
            locationConsumer.accept(null);
            error.printStackTrace();
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void sendBookingRequest(String scheduleId, JSONObject jsonObject , Consumer<String> bookingIdConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, IP +"api/schedules/" + scheduleId ,jsonObject,
                response -> {
                    try {
                      String payment_url = response.getString("payment_url");
                        Log.d(TAG, "sendBookingRequest: " + payment_url);
                      bookingIdConsumer.accept(payment_url);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            bookingIdConsumer.accept(null);
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getTourTicketRequest(Consumer<ArrayList<TourTicket>> ticketsConsumer)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, IP +"api/user/dashboard/orders" ,null,
                response -> {

                    ArrayList<TourTicket> orders = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject jsonObject = response.getJSONObject(i);
                            TourTicket tourTicket = new Gson().fromJson(String.valueOf(jsonObject), TourTicket.class);
                            tourTicket.setOrderDatetime(jsonObject.getLong("order_timestamp"));
                            orders.add(tourTicket);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ticketsConsumer.accept(orders);
                }, error -> {
            ticketsConsumer.accept(null);
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    public void getTourRequestRequest(Consumer<ArrayList<TourRequest>> ticketsconsumer)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, IP +"api/user/dashboard/trip-requests" ,null,
                response -> {
                    ArrayList<TourRequest> tourRequests = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject jsonObject = response.getJSONObject(i);
                            TourRequest tourRequest = new Gson().fromJson(String.valueOf(jsonObject), TourRequest.class);
                            tourRequest.setCreatedAt(jsonObject.getLong("created_at"));
                            tourRequests.add(tourRequest);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ticketsconsumer.accept(tourRequests);
                }, error -> {
            ticketsconsumer.accept(null);
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "JWT "+ JWT);
                return params;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }
}