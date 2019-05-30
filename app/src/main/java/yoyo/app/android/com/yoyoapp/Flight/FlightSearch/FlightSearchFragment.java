package yoyo.app.android.com.yoyoapp.Flight.FlightSearch;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.skydoves.elasticviews.ElasticImageView;
import com.thekhaeng.pushdownanim.PushDownAnim;
import es.dmoral.toasty.Toasty;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import yoyo.app.android.com.yoyoapp.Flight.BottomSheet.DatePickerBottomSheet;
import yoyo.app.android.com.yoyoapp.Flight.BottomSheet.DatePickerShamsiBottomSheet;
import yoyo.app.android.com.yoyoapp.Flight.BottomSheet.TravellerBottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.City;
import yoyo.app.android.com.yoyoapp.Flight.FlightResult.FlightsResultFragment;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.Flight.SearchDialog.SampleSearchModel;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;
import java.util.ArrayList;
import java.util.Calendar;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_STATIC_DP;


public class FlightSearchFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "FlightSearchFragment";
    private ImageView datePickerCardImageview, departureCardImageview, destinationCardImageview ,travellerCardImageview;
    private TextView departureTextview, departureCityTextview, destinationTextview, destinationCityTextview , datePickerDateTextview, datePickerMonthTextview ;
    private TextView travellerNumTextView, adultNumTextView, childNumTextView, infantNumTextView;
    private TextView oneWayTextview ,roundWayTextview;
    private ElasticImageView switchCityEImageview;
    private Button searchFlightButton;
    public  int adultCount, childCount,infantCount ,sum;
    public  static boolean isOneWay = true;
    private BottomSheetBehavior bottomSheetBehavior;
    private DatePickerBottomSheet datePickerBottomSheet;
    private ProgressBar progressBar;
    private ArrayList<SampleSearchModel> citiesList;
    private UserSharedManagerFlight userSharedManager;
    private DatePickerShamsiBottomSheet datePickerShamsiBottomSheet;
    private FlightSearchPresenter flightPresenter;
    private ConstraintLayout constraintLayout;
    private LottieAnimationView lottieAnimationView;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_flight, container, false);

        lottieAnimationView = view.findViewById(R.id.lottie_loading_bar);

        init();
        chooseDatePicker();
        setupOneWayFunc();
        setupSearchButton();
        setupTravellerNum();
        getCitiesList();

        return view;
    }

    private void init() {
        constraintLayout = view.findViewById(R.id.cl_flight);
        userSharedManager = new UserSharedManagerFlight(getContext());
        datePickerCardImageview = view.findViewById(R.id.iv_flight_datepicker);
        searchFlightButton = view.findViewById(R.id.button_flight_search);
        destinationTextview = view.findViewById(R.id.tv_flight_city_complete_destination);
        departureTextview = view.findViewById(R.id.tv_flight_city_complete_departure);
        destinationCardImageview = view.findViewById(R.id.iv_flight_destination);
        departureCardImageview = view.findViewById(R.id.iv_flight_departure);
        datePickerDateTextview = view.findViewById(R.id.tv_flight_date_picker_weekday);
        oneWayTextview = view.findViewById(R.id.tv_flight_oneway);
        roundWayTextview = view.findViewById(R.id.tv_flight_roundway);
        datePickerMonthTextview = view.findViewById(R.id.tv_flight_date_picker_month);
        switchCityEImageview = view.findViewById(R.id.iv_flight_switch);
        departureCityTextview = view.findViewById(R.id.tv_flight_city_departure);
        destinationCityTextview = view.findViewById(R.id.tv_flight_city_destination);
        travellerCardImageview = view.findViewById(R.id.iv_flight_person_num);
        travellerNumTextView = view.findViewById(R.id.tv_flight_travellers_num);
        adultNumTextView = view.findViewById(R.id.tv_flight_adult_num);
        childNumTextView = view.findViewById(R.id.tv_flight_child_num);
        infantNumTextView = view.findViewById(R.id.tv_flight_infant_num);
        progressBar = view.findViewById(R.id.progressBar_flight_booking);
        adultCount = ((MainFlightActivity)getContext()).adultCount;
        childCount = ((MainFlightActivity)getContext()).childCount;
        infantCount = ((MainFlightActivity)getContext()).infantCount;
        sum = ((MainFlightActivity)getContext()).sum;
        citiesList = new ArrayList<>();
        flightPresenter = new FlightSearchPresenter(getContext(),constraintLayout);
        travellerCardImageview.setOnClickListener(this);
        switchCityEImageview.setOnClickListener(this);
        oneWayTextview.setOnClickListener(this);
        roundWayTextview.setOnClickListener(this);
        destinationCardImageview.setOnClickListener(this);
        departureCardImageview.setOnClickListener(this);
        destinationTextview.setOnClickListener(this);
        departureTextview.setOnClickListener(this);
        datePickerCardImageview.setOnClickListener(this);
    }

    // get cities list from server
    private void getCitiesList() {
               flightPresenter.getCitiesListFromServer(cities -> {
                   for (int j = 0; j < cities.size(); j++) {
                       City city = cities.get(j);
                       SampleSearchModel sampleSearchModel = new SampleSearchModel(city.getName() +" ("+ city.getCode()+") " );
                       citiesList.add(sampleSearchModel);
                   }
               });
    }

    // if the language is persish -> setup the shamsi calender
    // else setup gregorian calender
    private void chooseDatePicker() {
        if (userSharedManager.getLanguage().equals("fa")){
            LinearLayout llBottomSheetShamsi = view.findViewById(R.id.ll_datepicker_bottom_sheet_shamsi);
            bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheetShamsi);
            datePickerShamsiBottomSheet = new DatePickerShamsiBottomSheet(getContext(), view);
        }else {
            LinearLayout llBottomSheet = view.findViewById(R.id.ll_datepicker_bottom_sheet);
            bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
            datePickerBottomSheet = new DatePickerBottomSheet(getContext(),view);
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    // change the ui when the user click on one way tab
    private void setupOneWayFunc() {
        isOneWay = true;
        oneWayTextview.setBackground(getResources().getDrawable(R.drawable.colorprimary_roundshape));
        oneWayTextview.setTextColor(getResources().getColor(R.color.white));
        roundWayTextview.setTextColor(getResources().getColor(R.color.black1));
        roundWayTextview.setBackground(getResources().getDrawable(R.drawable.white_roundshape));
        if (userSharedManager.getLanguage().equals("en")){
            datePickerMonthTextview.setText(datePickerBottomSheet.startDateString);
            datePickerDateTextview.setText(datePickerBottomSheet.fromDayOfWeek);
        }
    }

    // setting ui for traveller number text
    private void setupTravellerNum() {
        travellerNumTextView.setText(sum + getString(R.string.traveler));
        adultNumTextView.setText(String.valueOf(adultCount));
        childNumTextView.setText(String.valueOf(childCount));
        infantNumTextView.setText(String.valueOf(infantCount));
    }

    // sending the desired flight data to next page (flight result)
    private void setupSearchButton() {
        PushDownAnim.setPushDownAnimTo( searchFlightButton )
                .setScale( MODE_STATIC_DP, 8  );

        searchFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationView.playAnimation();
                lottieAnimationView.setVisibility(View.VISIBLE);

                if (!departureCityTextview.getText().equals(destinationCityTextview.getText()))
                {

                progressBar.setVisibility(View.VISIBLE);
                FlightsResultFragment flightsResultFragment = new FlightsResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString("originCityCode", departureCityTextview.getText().toString());
                bundle.putString("originCity", departureTextview.getText().toString());
                bundle.putString("destinationCityCode", destinationCityTextview.getText().toString());
                bundle.putString("destinationCity", destinationTextview.getText().toString());
                if (!((MainFlightActivity)getContext()).isDateChanged)
                {
                    String standardDateFormat = getStandardDateFormat(Calendar.getInstance());
                    String newValueDepartureDate = convertToEnglishNumbers(standardDateFormat);
                    bundle.putString("departureDate", newValueDepartureDate);
                    ((MainFlightActivity)getContext()).standardDate = Calendar.getInstance();
                }
                else
                {
                    String standardDateFormat = getStandardDateFormat(((MainFlightActivity)getContext()).standardDate);
                    String newValueDepartureDate = convertToEnglishNumbers(standardDateFormat);
                    bundle.putString("departureDate", newValueDepartureDate);
                }
                bundle.putInt("adultCount", adultCount);
                bundle.putInt("childCount", childCount);
                bundle.putInt("infantCount", infantCount);
                flightsResultFragment.setArguments(bundle);
                progressBar.setVisibility(View.GONE);
                ((MainFlightActivity)getContext()).idAircrafts.clear();
                ((MainFlightActivity)getContext()).iataCodeAirlines.clear();
                ((MainFlightActivity)getContext()).dayTimes.clear();

                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(flightsResultFragment);
                    lottieAnimationView.setVisibility(View.GONE);
                    lottieAnimationView.pauseAnimation();
                }


                }
                else
                {
                    Toasty.error(getContext(),getString(R.string.same_destination_origin)).show();
                }
            }
        });
    }

    // converting persian flight date number to english for the devices that their local language is persian
    private String convertToEnglishNumbers(String standardFormatDate) {
        return standardFormatDate.replace("١", "1").replace("٢", "2").replace("٣", "3").replace("٤", "4").replace("٥", "5")
                                .replace("٦", "6").replace("٧", "7").replace("٨", "8").replace("٩", "9").replace("٠", "0");
    }

    // handling click listeners for all buttons
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_flight_datepicker
                || v.getId() == R.id.tv_flight_date_picker_month)
        {
            setupDatePicker();
        }
        if (v.getId() == R.id.iv_flight_destination )
        {
            setupSearchDialog(false);
        }

        if (v.getId() == R.id.iv_flight_departure)
        {
            setupSearchDialog(true);
        }
        if (v.getId() == R.id.tv_flight_oneway)
        {
            setupOneWayFunc();
            if (userSharedManager.getLanguage().equals("en")){
                datePickerBottomSheet.checkInFunc();
                datePickerBottomSheet.resetCalendar();
            }
        }
        if (v.getId() == R.id.tv_flight_roundway)
        {
            setupRoundFunc();
            if (userSharedManager.getLanguage().equals("en")){
                datePickerBottomSheet.checkInFunc();
                datePickerBottomSheet.resetCalendar();
            }
        }
        if (v.getId() == R.id.iv_flight_person_num)
        {
            setupBottomNavigation();
        }
        if (v.getId() == R.id.iv_flight_switch)
        {
            String des = destinationTextview.getText().toString();
            destinationTextview.setText(departureTextview.getText().toString());
            departureTextview.setText(des);
            String depCity = departureCityTextview.getText().toString();
            departureCityTextview.setText(destinationCityTextview.getText().toString());
            destinationCityTextview.setText(depCity);
        }
    }

    // setup traveller number bottomsheet (adult, child , infant)
    private void setupBottomNavigation() {
        TravellerBottomSheetDialogFragment dialogFragment = new TravellerBottomSheetDialogFragment();
        dialogFragment.show(getFragmentManager(),"TravellerBottomSheet");
    }

    // change the ui when the user click on round way tab
    private void setupRoundFunc() {
        isOneWay = false;
        roundWayTextview.setBackground(getResources().getDrawable(R.drawable.colorprimary_roundshape));
        roundWayTextview.setTextColor(getResources().getColor(R.color.white));
        oneWayTextview.setTextColor(getResources().getColor(R.color.black1));
        oneWayTextview.setBackground(getResources().getDrawable(R.drawable.white_roundshape));
        if (userSharedManager.getLanguage().equals("en")){
            datePickerDateTextview.setText(datePickerBottomSheet.fromDayOfWeek +"-"+ datePickerBottomSheet.toDayOfWeek );
            datePickerMonthTextview.setText(datePickerBottomSheet.startDateString +" - "+ datePickerBottomSheet.endDateString);
        }
    }

    // expanding datepicker buttomsheet
    private void setupDatePicker() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    // setup cities search dialog
    private void setupSearchDialog(final boolean isFromTextview)
    {
        SimpleSearchDialogCompat<Searchable> simpleSearchDialogCompat =  new SimpleSearchDialogCompat(getContext(), "Search...",
                "What are you looking for...?", null, citiesList,
                new SearchResultListener<SampleSearchModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, SampleSearchModel item, int position) {

                        if (isFromTextview)
                        {
                            int iend = item.getTitle().indexOf("(");
                            departureCityTextview.setText( item.getTitle().substring(iend+1 , item.getTitle().indexOf(")")));
                            departureTextview.setText(item.getTitle().substring(0 , iend));
                        }
                        else {
                            int iend = item.getTitle().indexOf("(");
                            destinationCityTextview.setText( item.getTitle().substring(iend+1 , item.getTitle().indexOf(")")));
                            destinationTextview.setText(item.getTitle().substring(0 , iend));
                        }
                        dialog.dismiss();
                    }
                });

        simpleSearchDialogCompat.show();
    }

    // converting date to a standard format for sending to server
    public String getStandardDateFormat(Calendar calendar)
    {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return df.format("yyyy-MM-dd",calendar).toString();
    }




}
