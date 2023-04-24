package yoyo.app.android.com.yoyoapp.Flight.FlightResult;

import android.content.Context;
import android.text.format.DateFormat;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;
import ir.mirrajabi.persiancalendar.core.models.CivilDate;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import ir.mirrajabi.persiancalendar.helpers.DateConverter;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Flight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.MyCalender;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.Flight.Utils.CheckInternetConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class FlightResultPresenter {

    private static final String TAG = "FlightResultPresenter";
    private Context context;
    private ApiServiceFlight apiServiceFlight;
    private RecyclerView recyclerView;
    private String queryStreems;
    private DateFormat standardFormatDate;
    private SimpleDateFormat dayFormat;
    private boolean internetAccess = true;
    private boolean selectedDayIsToday = false;
    private CheckInternetConnection checkInternetConnection;


    // get recycler view in constructor for showing no internet connection in snackbar when the user
    // does not have internet connection
    public FlightResultPresenter(Context context, RecyclerView recyclerView)
    {
        this.context = context;
        this.recyclerView = recyclerView;
        // initializing instance variables
        apiServiceFlight = new ApiServiceFlight(context);
        standardFormatDate = new DateFormat();
        dayFormat = new SimpleDateFormat("E", Locale.getDefault());
    }

    // send flight request
    public void sendFlightRequest(String originCityCode, String destinationCityCode, String departureDate, int adultCount, int childCount,
                                  int infantCount, String minprice, String maxprice, String filterFlight,
                                  Consumer<ArrayList<Flight>> onNewResult)
    {
        configurQueryStreem(originCityCode, destinationCityCode, departureDate, adultCount, childCount, infantCount, minprice, maxprice, filterFlight);
        // before sending request, checking internet connection
        new CheckInternetConnection(context, recyclerView, result -> {

                apiServiceFlight.sendSearchFlightsRequest(queryStreems , flights ->
                {
                    if (flights!=null)
                    {
                        onNewResult.accept(flights);
                    }
                    else
                        onNewResult.accept(null);
                });
        });
    }


    // convert the parameter to url
    private void configurQueryStreem(String originCityCode, String destinationCityCode, String departureDate, int adultCount, int childCount, int infantCount, String minprice, String maxprice, String filterFlight) {
        queryStreems =  "/api/mobile/flights/one-way/?origin_city_code="+originCityCode+"&destination_city_code="+destinationCityCode+"&departure_date="+departureDate+"&adult_count="
                +adultCount+"&child_count="+childCount+"&infant_count="+infantCount+"&min_price="+minprice+"&max_price="+maxprice+"&ordering=" + filterFlight;
        if (getConvertedAirlines().length() > 0)
        {
            queryStreems += "&airlines="+ getConvertedAirlines();
        }
        if (getConvertedAirCrafts().length()>0)
        {
            queryStreems += "&manufacturers=" + getConvertedAirCrafts();
        }
        if (getConvertedDayTimes().length()>0)
        {
            queryStreems += "&departure_time=" + getConvertedDayTimes();
        }
    }

    // convert airline array to one string with comma between each airline
    private String getConvertedAirlines() {
        StringBuilder nameBuilder = new StringBuilder();
//        if (((MainFlightActivity)context).iataCodeAirlines.size()>0)
//        {
//            for (String n : ((MainFlightActivity)context).iataCodeAirlines) {
//                nameBuilder.append(n).append(",");
//            }
//            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
//        }
        return String.valueOf(nameBuilder);
    }

    // convert aircraft array to one string with comma between each aircraft
    private String getConvertedAirCrafts() {
        StringBuilder nameBuilder = new StringBuilder();
//        if (((MainFlightActivity)context).idAircrafts.size() > 0)
//        {
//            for (String n : ((MainFlightActivity)context).idAircrafts) {
//                nameBuilder.append(n).append(",");
//            }
//            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
//        }
        return String.valueOf(nameBuilder);
    }

    // convert day times array to one string with comma between each day time
    private String getConvertedDayTimes() {
        StringBuilder nameBuilder = new StringBuilder();
//        if (((MainFlightActivity)context).dayTimes.size() > 0)
//        {
//            for (String n : ((MainFlightActivity)context).dayTimes) {
//                nameBuilder.append(n).append(",");
//            }
//            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
//        }
        return String.valueOf(nameBuilder);
    }

    // send best prices for the calender with best price
    public void getBestPrices(String originCityCode, String destinationCityCode, String startDate, String endDate, Consumer<ArrayList<MyCalender>> onNewResult) {
        new CheckInternetConnection(context, recyclerView, result -> {
            if (result)
            {
                apiServiceFlight.getBestPricesForCalender(originCityCode, destinationCityCode, startDate, endDate, myCalenderArrayList -> {
                    if (myCalenderArrayList!= null) {
                        onNewResult.accept(myCalenderArrayList);
                    }
                });
            }
        });
    }

    // calculate 20 days in period of the user's selected day
    // ( 10 days after selected day and 10 days before selected day )
    public ArrayList<MyCalender> getCalenderData()
    {
        ArrayList<MyCalender> myCalenders = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        Calendar selectedDay = Calendar.getInstance();
//        selectedDay.setTime((((MainFlightActivity)context).standardDate).getTime());

        float diffDays = daysBetween(today,selectedDay);

        // when the difference between selected day and today is less than
        // 10 days, then we have more than 10 days after selected day
        if (diffDays < 10) {
            if (!selectedDayIsToday)
            {
                for (int i = 0; i <= diffDays; i++) {
                    myCalenders.add(setupCalender(today));
                    today.add(Calendar.DAY_OF_YEAR, +1);
                }
            }
            for (int i = 0; i < 20 - diffDays; i++) {
                myCalenders.add(setupCalender(selectedDay));
                selectedDay.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        // we have exactly 10 days before and after the selected day
        else
        {
            selectedDay.add(Calendar.DAY_OF_YEAR, -10);
            for (int i = 0; i < 10; i++) {
                myCalenders.add(setupCalender(selectedDay));
                selectedDay.add(Calendar.DAY_OF_YEAR, 1);
            }
            for (int i = 0; i < 10 ; i++) {
                myCalenders.add(setupCalender(selectedDay));
                selectedDay.add(Calendar.DAY_OF_YEAR, 1);
            }
        }

        return myCalenders;
    }

    // setup calender for each date
    private MyCalender setupCalender(Calendar theDay) {
        int day;
        String dayOfTheWeek;
        MyCalender myCalender = new MyCalender();
        day = theDay.get(Calendar.DAY_OF_MONTH);
        dayOfTheWeek = dayFormat.format(theDay.getTime());

        // if the language is persis, calculate persian date
        if (FlightsResultFragment.persianCalender)
        {
            PersianDate persianDate = DateConverter.
                    civilToPersian(new CivilDate(theDay.get(Calendar.YEAR),theDay.get(Calendar.MONTH) + 1,theDay.get(Calendar.DAY_OF_MONTH) ));
            myCalender.setDayFarsi(String.valueOf(persianDate.getDayOfMonth()));
            myCalender.setDayOfWeekFarsi(getDayOfWeekName(persianDate.getYear(), persianDate.getDayOfWeek()));
        }
        myCalender.setDay(String.valueOf(day));
        myCalender.setDayOfTheWeek(dayOfTheWeek);
        myCalender.setMinPrice("95$");
        myCalender.setMin(false);
        if (day%5==0) {
            myCalender.setMin(true);
        }
        myCalender.setStandardDate(standardFormatDate.format("yyyy-MM-dd", theDay).toString());

        return myCalender;
    }

    // get day of week name, when the language is persis
    private String getDayOfWeekName(int year, int dayOfWeek) {

        if (year == 1397)
        switch (dayOfWeek)
        {
            case 1:
                return "پنج شنبه";
            case 2:
                return "جمعه";
            case 3:
                return "شنبه";
            case 4:
                return "یک شنبه";
            case 5:
                return "دو شنبه";
            case 6:
                return "سه شنبه";
            case 7:
                return "چهار شنبه";
            default:
                return "جمعه";
        }
        else
            switch (dayOfWeek)
            {
                case 3:
                    return "پنج شنبه";
                case 4:
                    return "جمعه";
                case 5:
                    return "شنبه";
                case 6:
                    return "یک شنبه";
                case 7:
                    return "دو شنبه";
                case 1:
                    return "سه شنبه";
                case 2:
                    return "چهار شنبه";
                default:
                    return "جمعه";
            }
    }

    // calculate the difference between two days
    private float daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();

        if (startDate.get(Calendar.DAY_OF_YEAR) == endDate.get(Calendar.DAY_OF_YEAR) &&
                startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR))
        {
            selectedDayIsToday = true;
            return 0;
        }

        return  TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }

    // generate fake data for flights
    public ArrayList<Flight> getFakeFlightList() {
        ArrayList<Flight> flights = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Flight flight = new Flight();

            if (i==0)
            {
                flight.setAirline("Mahan Air");
                flight.setType("Charter");
                flight.setOriginIataCode("TEH");
                flight.setDestinationIataCode("MHD");
                flight.setAdultPrice("95");
                flight.setDepartureTime("09:00");
                flight.setAirlineLogo("https://ghasedak24.com/uploads/wysiwyg/images/mahan11.jpg");
            }
            else if (i==1)
            {
                flight.setAirline("Qeshm Air");
                flight.setType("Charter");
                flight.setOriginIataCode("TEH");
                flight.setDestinationIataCode("MHD");
                flight.setAdultPrice("60");
                flight.setDepartureTime("10:00");
                flight.setAirlineLogo("https://www.hamburg-airport.de/images/QB_angepasst.jpg");
            }
            else
            {
                flight.setAirline("Iran Air");
                flight.setType("Charter");
                flight.setOriginIataCode("TEH");
                flight.setDestinationIataCode("MHD");
                flight.setAdultPrice("70$");
                flight.setDepartureTime("15:30");
                flight.setAirlineLogo("https://www.caa.gov.qa/en-us/CAA%20Images/English%20news/En-Logos/iran%20air%200231.jpg");
            }


            flights.add(flight);
        }
        return flights;
    }

}
