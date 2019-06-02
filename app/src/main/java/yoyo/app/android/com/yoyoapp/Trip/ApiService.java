package yoyo.app.android.com.yoyoapp.Trip;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.core.util.Consumer;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.*;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import java.text.SimpleDateFormat;
import java.util.*;


public class ApiService {
    private static final String TAG = "ApiService";
    private Context context;
    private String IP = "http://192.168.1.63:9000/";
    private String JWT;
    private UserSharedManager userSharedManager;

    public ApiService(Context context) {
        this.context = context;
        userSharedManager = new UserSharedManager(context);
        JWT = userSharedManager.getToken();
    }

    public void getTripListRequest(int page, TripQuery tripQuery, Consumer<ArrayList<Trip>> tripArrayListConsumer)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, IP +"api/trips" + "?price_min="+
                tripQuery.getFromPrice()+"&price_max="+tripQuery.getToPrice()+"&start_date="+tripQuery.getFromTime()+
                "&end_date="+tripQuery.getToTime()+"&category="+tripQuery.getCategories() +"&location=" + tripQuery.getLocation()+
                "&duration_min=1&duration_max=999&offset="+ page +"&limit=10&reserve_type=FIXED",null,
                response -> {

                    ArrayList<Trip> trips = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {

                                Trip trip = new Trip();

                                JSONObject mainObject = response.getJSONObject(i);
                                trip.setTripId(mainObject.getString("trip_id"));
                                trip.setTitle(mainObject.getString("title"));
                                trip.setNightNum(mainObject.getInt("days"));
                                trip.setDayNum(mainObject.getInt("nights"));

                                // leader
                                TripLeader tripLeader = new TripLeader();
                                JSONObject leaderObject = mainObject.getJSONObject("leader");
                                tripLeader.setName(leaderObject.getString("name"));
                                JSONObject pictureObject = leaderObject.getJSONObject("picture");
                                tripLeader.setPicture(IP + pictureObject.getString("thumbnail_url"));
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
                                    galleries.add(IP + galleryObject.getString("original_url"));
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
                                    locations.add(location);
                                }
                                trip.setLocations(locations);

//                                // start and end time
//                                Calendar cal = getDateAndTime(mainObject.getLong("start_time"));
//                                String sDate = DateFormat.format("dd-MM-yyyy  hh:mm a", cal).toString();
//                                trip.setStartTime(sDate);
//                                Calendar cal2 = getDateAndTime(mainObject.getLong("end_time"));
//                                String eDate = DateFormat.format("dd-MM-yyyy  hh:mm a", cal2).toString();
//                                trip.setEndTime(eDate);
//                                trip.setStartDate(getStandardDate(cal));
//                                trip.setEndDate(getStandardDate(cal2));
//
//                                trip.setPrice(mainObject.getString("price"));
//                                trip.setImage(IP + mainObject.getString("image_url"));
//                                trip.setCategory(mainObject.getString("category"));
//                                trip.setRemainingCapacity(mainObject.getInt("remaining_capacity"));
//                                trip.setPreviousPrice("225");
//                                trip.setLanguage("English");
//                                trip.setDestination("Tehran");
//
//                                JSONArray jsonArray1 = mainObject.getJSONArray("trip_locations");
//                                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
//                                if (jsonObject1.getInt("order") == 0) {
//                                    trip.setStartPoint(jsonObject1.getString("title"));
//                                    // TODO: 5/15/2019 fix getting location
////                                    trip.setEndPoint(jsonArray1.getJSONObject(1).getString("title"));
//                                }
//                                else
//                                {
////                                  trip.setStartPoint(jsonArray1.getJSONObject(1).getString("title"));
//                                    trip.setEndPoint(jsonObject1.getString("title"));
//                                }

                                trips.add(trip);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "getTripListRequest: " + e.getMessage());
                    }

                    tripArrayListConsumer.accept(trips);

                }, error -> {
            tripArrayListConsumer.accept(null);
            error.printStackTrace();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonArrayRequest);
    }

    private Calendar getDateAndTime(Long date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(date * 1000);
        return cal;
    }

    private String getStandardDate(Calendar cal) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());
        String dayOfWeekNameFrom = dayFormat.format(cal.getTime());
        String monthNameFrom = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        return "" + dayOfWeekNameFrom + ", "
                + cal.get(Calendar.DAY_OF_MONTH)
                + " " + monthNameFrom;
    }


    public void getTripDetailsRequest(String tripId ,Consumer<Trip> tripConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/trip/" + tripId,null,
                response -> {
                    Trip trip = new Trip();

                    try {
                        JSONObject jsonObject = response.getJSONObject("trip");

                        trip.setTripId(jsonObject.getString("trip_id"));
                        trip.setTitle(jsonObject.getString("title"));
//                        trip.setTripLeaderName(jsonObject.getString("leader"));
                        // start and end time
                        Calendar cal = getDateAndTime(jsonObject.getLong("start_time"));
                        String sDate = DateFormat.format("dd-MM-yyyy  hh:mm a", cal).toString();
                        trip.setStartTime(sDate);
                        Calendar cal2 = getDateAndTime(jsonObject.getLong("end_time"));
                        String eDate = DateFormat.format("dd-MM-yyyy  hh:mm a", cal2).toString();
                        trip.setEndTime(eDate);
                        trip.setStartDate(getStandardDate(cal));
                        trip.setEndDate(getStandardDate(cal2));

                        trip.setPrice(jsonObject.getString("price"));
                        trip.setImage(jsonObject.getString("image_url"));
//                        trip.setCategory(jsonObject.getString("category"));
                        trip.setRemainingCapacity(jsonObject.getInt("remaining_capacity"));
                        trip.setPreviousPrice("225");
                        trip.setNightNum(2);
                        trip.setDayNum(3);
                        trip.setLanguage("English");
                        trip.setDestination("Tehran");

                        // get tour from trip
                        Tour tour = new Tour();
                        JSONObject jsonObject2 = jsonObject.getJSONObject("tour");
                        tour.setId(jsonObject2.getString("tour_id"));
                        tour.setName(jsonObject2.getString("name"));
                        tour.setCreatedAt(jsonObject2.getLong("created_at"));
                        tour.setPassengerCount(jsonObject2.getInt("passengers_count"));

                        trip.setTour(tour);

                        // get attraction
                        ArrayList<String> attractios = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONArray("attractions");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            attractios.add(jsonArray.getString(i));
                        }
                        trip.setAttractions(attractios);

                        // get itinerary
                        ArrayList<String> itineraries = new ArrayList<>();
                        JSONArray jsonArray2 = jsonObject.getJSONArray("itinerary");
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            itineraries.add(jsonArray2.getString(i));
                        }
                        trip.setItineraries(itineraries);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tripConsumer.accept(trip);

                }, error -> {
            tripConsumer.accept(null);
            error.printStackTrace();
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getLocationsRequest(Consumer<ArrayList<Location>> locationConsumer)
    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/location" ,null,
                response -> {

                    ArrayList<Location> locations = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {


                                Location location = new Location();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                location.setLocationId(jsonObject.getString("location_id"));
                                location.setTitle(jsonObject.getString("title"));
                                location.setCode(jsonObject.getString("code"));
                                location.setLat(jsonObject.getDouble("latitude"));
                                location.setLon(jsonObject.getDouble("longitude"));

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


    public void getCategoryRequest(Consumer<ArrayList<Category>> categoriesConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/category" ,null,
                response -> {

                    ArrayList<Category> categories = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            Category category = new Category();
                            category.setName(jsonArray.getString(i));

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
                user.setProfilePicture(response.getString("profile_picture"));
                user.setToken(response.getString("token"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            userConsumer.accept(user);

        }, error -> {
            userConsumer.accept(null);
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
                        }

                        userConsumer.accept(user);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
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
                            traveller.setDateOfBirth(jsonObject.getString("dob"));
                            traveller.setPassportNumber(jsonObject.getString("passport_number"));
                            traveller.setIranianNationalCode(jsonObject.getString("national_code"));
                            traveller.setNationality(jsonObject.getString("nationality"));
                            if (jsonObject.getString("nationality").toUpperCase().contains("IRAN")||
                                    jsonObject.getString("nationality").toUpperCase().contains("ایران"))
                                traveller.setIranian(true);
                            else
                                traveller.setIranian(false);

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
                    isDeleted.accept(false);
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
                            stringConsumer.accept(IP + response.getString("profile_picture"));
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








}

