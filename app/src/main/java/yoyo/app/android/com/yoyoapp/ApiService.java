package yoyo.app.android.com.yoyoapp;

import android.content.Context;
import android.text.format.DateFormat;
import androidx.core.util.Consumer;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class ApiService {
    private static final String TAG = "ApiService";

    private Context context;
    private String IP = "http://192.168.1.65:80/";


    public ApiService(Context context) {
        this.context = context;
    }

    public void getTripListRequest(TripQuery tripQuery ,Consumer<ArrayList<Trip>> tirpArrayListConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/trip" + "?from_price="+
                tripQuery.getFromPrice()+"&to_price="+tripQuery.getToPrice()+"&from_time="+tripQuery.getFromTime()+
                "&to_time="+tripQuery.getToTime()+"&category="+tripQuery.getCategories() +"&location=" + tripQuery.getLocation(),null,

                response -> {

                    ArrayList<Trip> tirps = new ArrayList<>();
                    try {
                        JSONArray jsonArray = response.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {

                                Trip tirp = new Trip();

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                tirp.setTripId(jsonObject.getString("trip_id"));
                                tirp.setTitle(jsonObject.getString("title"));
                                tirp.setTripLeaderName(jsonObject.getString("leader"));
                                // start and end time
                                Calendar cal = getDateAndTime(jsonObject.getLong("start_time"));
                                String sDate = DateFormat.format("dd-MM-yyyy  hh:mm a", cal).toString();
                                tirp.setStartTime(sDate);
                                Calendar cal2 = getDateAndTime(jsonObject.getLong("end_time"));
                                String eDate = DateFormat.format("dd-MM-yyyy  hh:mm a", cal2).toString();
                                tirp.setEndTime(eDate);
                                tirp.setStartDate(getStandardDate(cal));
                                tirp.setEndDate(getStandardDate(cal2));

                                tirp.setPrice(jsonObject.getString("price"));
                                tirp.setImage(IP + jsonObject.getString("image_url"));
                                tirp.setCategory(jsonObject.getString("category"));
                                tirp.setRemainingCapacity(jsonObject.getInt("remaining_capacity"));
                                tirp.setPreviousPrice("225");
                                tirp.setNightNum(2);
                                tirp.setDayNum(3);
                                tirp.setLanguage("English");
                                tirp.setDestination("Tehran");

                                JSONArray jsonArray1 = jsonObject.getJSONArray("trip_locations");
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                                if (jsonObject1.getInt("order") == 0) {
                                    tirp.setStartPoint(jsonObject1.getString("title"));
                                    // TODO: 5/15/2019 fix getting location
//                                    tirp.setEndPoint(jsonArray1.getJSONObject(1).getString("title"));
                                }
                                else
                                {
//                                    tirp.setStartPoint(jsonArray1.getJSONObject(1).getString("title"));
                                    tirp.setEndPoint(jsonObject1.getString("title"));
                                }

                                tirps.add(tirp);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tirpArrayListConsumer.accept(tirps);

                }, error -> {
            tirpArrayListConsumer.accept(null);
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

    private String getStandardDate(Calendar cal) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.getDefault());
        String dayOfWeekNameFrom = dayFormat.format(cal.getTime());
        String monthNameFrom = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        return "" + dayOfWeekNameFrom + ", "
                + cal.get(Calendar.DAY_OF_MONTH)
                + " " + monthNameFrom;
    }


    public void getTripDetailsRequest(String tripId ,Consumer<Trip> tirpConsumer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, IP +"api/trip/" + tripId,null,
                response -> {
                    Trip trip = new Trip();

                    try {
                        JSONObject jsonObject = response.getJSONObject("trip");

                        trip.setTripId(jsonObject.getString("trip_id"));
                        trip.setTitle(jsonObject.getString("title"));
                        trip.setTripLeaderName(jsonObject.getString("leader"));
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
                        trip.setCategory(jsonObject.getString("category"));
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
                        trip.setItinerary(itineraries);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tirpConsumer.accept(trip);

                }, error -> {
            tirpConsumer.accept(null);
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
                                location.setLat(jsonObject.getInt("latitude"));
                                location.setLon(jsonObject.getInt("longitude"));

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




}

