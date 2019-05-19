package yoyo.app.android.com.yoyoapp.Flight.FlightResult;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.cunoraz.continuouscrollable.ContinuousScrollableImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import flight.app.android.com.flightbookingapp.Utils.ToggleSwitch;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.CalenderRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.FlightRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Flight.BottomSheet.FlightFilterBottomSheet;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.Flight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.MyCalender;
import yoyo.app.android.com.yoyoapp.Flight.Enum.FilterFlight;
import yoyo.app.android.com.yoyoapp.Flight.FlightDetails.FlightDetailsFragment;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;
import java.util.Calendar;

public class FlightsResultFragment extends BaseFragment {
    private static final String TAG = "FlightsResultFragment";
    private TextView adultNumTextview, childNumTextview, infantNumTextview, originIataTextview,
            destinationIataTextview, originCityTextview, destinationCityTextview, noResultTextview, noResult2Textview;
    private int adultCount, childCount, infantCount;
    private RecyclerView recyclerView, calenderRecyclerview;
    private FlightRecyclerviewAddapter flightRecyclerviewAddapter;
    private CalenderRecyclerviewAddapter calenderRecyclerviewAddapter;
    private ImageView backImageview;
    private ContinuousScrollableImageView noResultImageview;
    private ArrayList<Flight> flightArrayList;
    private LinearLayoutManager linearLayoutManager;
    private FlightFilterBottomSheet flightFilterBottomSheet;
    private String startDate, endDate;
    private ArrayList<MyCalender> myCalenders;
    private PersianCalendarHandler calendarHandler;
    private FloatingActionButton floatingActionButton;
    private ShimmerRecyclerView shimmerRecycler;
    private LinearLayoutManager llManager;
    private ToggleSwitch filterToggleswitch;
    private FilterFlight filterFlight;
    public  static boolean persianCalender;
    private FlightResultPresenter flightResultPresenter;
    private ConstraintLayout noResultConstraintLayout;
    private int position = 0;
    private String minprice = "0", maxprice = "900000000";
    private String originCityCode, destinationCityCode, departureDate, destinationCity, departureCity;
    private UserSharedManagerFlight userSharedManager;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_flights_result, container, false);

        init();
        getDataWithBundle();
        getFlights();
        setupCalenderRecyclerview();
        getBestPrices();
        setupFilter();
        setupBackButton();
        setupRecyclerview();
        setupOrderToggleSwitch();
        hideFloatingActionButton();

        return view;
    }


    // setup order toggle switch for ordering flights based on price, capacity and departure time
    private void setupOrderToggleSwitch() {

        filterToggleswitch.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if (i == 0) {
                    if (filterFlight.equals(FilterFlight.PRICE))
                        filterFlight = FilterFlight.PRICEDEC;
                    else
                        filterFlight = FilterFlight.PRICE;

                } else if (i == 1) {
                    if (filterFlight.equals(FilterFlight.DEPARTURETIME))
                        filterFlight = FilterFlight.DEPARTURETIMEDEC;
                    else
                        filterFlight = FilterFlight.DEPARTURETIME;
                } else {
                    if (filterFlight.equals(FilterFlight.CAPACITY))
                        filterFlight = FilterFlight.CAPACITYDEC;
                    else
                        filterFlight = FilterFlight.CAPACITY;
                }
                getFlights();
            }
        });
    }

    // pop this fragment form stack when the user press back
    private void setupBackButton() {
        backImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    // get flight data from previous fragment for sending flight request
    private void getDataWithBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            originCityCode = bundle.getString("originCityCode");
            destinationCityCode = bundle.getString("destinationCityCode");
            departureDate = bundle.getString("departureDate");
            destinationCity = bundle.getString("destinationCity");
            departureCity = bundle.getString("originCity");
            adultCount = ((MainFlightActivity) getContext()).adultCount;
            childCount = ((MainFlightActivity) getContext()).childCount;
            infantCount = ((MainFlightActivity) getContext()).infantCount;
            destinationIataTextview.setText(destinationCityCode);
            originIataTextview.setText(originCityCode);
            destinationCityTextview.setText(destinationCity);
            originCityTextview.setText(departureCity);
            adultNumTextview.setText(String.valueOf(adultCount) + " " + getString(R.string.adult));
            childNumTextview.setText(String.valueOf(childCount) + " " + getString(R.string.child));
            infantNumTextview.setText(String.valueOf(infantCount) + " " + getString(R.string.infant));
        }
    }

    // setup flight filter bottomsheet
    // onrecived callback for getting max and min price and then sending a flight request
    private void setupFilter() {
        flightFilterBottomSheet = new FlightFilterBottomSheet(getContext(), view, new FlightFilterBottomSheet.OnsendDataToResultPage() {
            @Override
            public void onRecieved(String min, String max) {
                minprice = min;
                maxprice = max;
                getFlights();
            }
        });
    }

    // sending a flight request and update ui changes
    private void getFlights() {
        noResultConstraintLayout.setVisibility(View.GONE);
        shimmerRecycler.showShimmerAdapter();
        llManager.scrollToPosition(0);

        flightResultPresenter.sendFlightRequest(originCityCode, destinationCityCode, departureDate, adultCount, childCount,
                infantCount, minprice, maxprice, filterFlight.toString(), flights ->
                {
                    flightArrayList.clear();

                    if (flights != null) {
                        flightArrayList.addAll(flights);
                    }
                    if (flights == null || flights.size() == 0) {
                        noResultConstraintLayout.setVisibility(View.VISIBLE);
                    }

                    shimmerRecycler.hideShimmerAdapter();
                    flightRecyclerviewAddapter.notifyDataSetChanged();
                });
    }

    // setup calender with price
    private void setupCalenderRecyclerview() {
        // checking if the language is persian
        if (userSharedManager.getLanguage().equals("fa")) {
            persianCalender = true;
        } else {
            persianCalender = false;
        }
        calenderRecyclerview = view.findViewById(R.id.rv_flightresult_calender);
        // get a list of dates
        myCalenders = flightResultPresenter.getCalenderData();
        startDate = myCalenders.get(0).getStandardDate();
        endDate = myCalenders.get(myCalenders.size() - 1).getStandardDate();

        calculateDiffDaysForPosition();

        calenderRecyclerviewAddapter = new CalenderRecyclerviewAddapter(myCalenders, getContext(), persianCalender, position, new CalenderRecyclerviewAddapter.OnItemClicked() {
            @Override
            public void onClicked(String standardDate, int newPosition) {
                departureDate = standardDate;
                getFlights();
                if (newPosition > position)
                    linearLayoutManager.scrollToPosition(newPosition + 2);
                else
                    linearLayoutManager.scrollToPosition(newPosition - 2);
                position = newPosition;
            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        calenderRecyclerview.setLayoutManager(linearLayoutManager);
        calenderRecyclerview.setAdapter(calenderRecyclerviewAddapter);
        linearLayoutManager.scrollToPosition(position - 2);
    }

    // for showing the right position in the date list
    private void calculateDiffDaysForPosition() {
        long millis2 = ((MainFlightActivity)getContext()).standardDate.getTimeInMillis();
        long millis1 = Calendar.getInstance().getTimeInMillis();
        long diff = millis2 - millis1;
        diff =  diff / (24 * 60 * 60 * 1000);
        if (diff < 10 )
        {
            position =(int) diff + 1;
            if (((MainFlightActivity)getContext()).standardDate.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) &&
                    ((MainFlightActivity)getContext()).standardDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))
            {
                position = 0 ;
            }
        }
        else
            position = 10;
    }

    // get best price for the calender
    private void getBestPrices() {
        flightResultPresenter.getBestPrices(originCityCode, destinationCityCode, startDate, endDate, myCalenderArrayList ->
        {
            if (myCalenderArrayList != null) {
                for (int i = 0; i < myCalenderArrayList.size(); i++) {
                    if (myCalenders.get(i).getStandardDate().equals(myCalenderArrayList.get(i).getStandardDate())) {
                        myCalenders.get(i).setMinPrice(myCalenderArrayList.get(i).getMinPrice());
                        myCalenders.get(i).setMin(myCalenderArrayList.get(i).isMin());
                    }
                }
                calenderRecyclerviewAddapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        noResultTextview = view.findViewById(R.id.tv_flightresult_noresult);
        noResultImageview = view.findViewById(R.id.iv_flightresult_noresult);
        userSharedManager = new UserSharedManagerFlight(getContext());
        shimmerRecycler = view.findViewById(R.id.shimmer_recycler_view);
        filterToggleswitch = view.findViewById(R.id.toggleSwitch_filter_tirp);
        filterToggleswitch.setCheckedPosition(0);
        flightArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rv_flightresult);
        adultNumTextview = view.findViewById(R.id.tv_flightresults_adult_num);
        childNumTextview = view.findViewById(R.id.tv_flightresults_child_num);
        infantNumTextview = view.findViewById(R.id.tv_flightresults_infant_num);
        originCityTextview = view.findViewById(R.id.tv_flightresults_origin);
        destinationCityTextview = view.findViewById(R.id.tv_flightresults_destination_city);
        originIataTextview = view.findViewById(R.id.tv_flightresults_origin_iata);
        destinationIataTextview = view.findViewById(R.id.tv_flightresults_destination_iata);
        calenderRecyclerview = view.findViewById(R.id.rv_flightresult_calender);
        backImageview = view.findViewById(R.id.iv_flightresults_back);
        llManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        floatingActionButton = view.findViewById(R.id.fbutton_flightresult);
        filterFlight = FilterFlight.PRICE;
        flightResultPresenter = new FlightResultPresenter(getContext(), recyclerView);
        noResult2Textview = view.findViewById(R.id.tv_flightresult_noresult2);
        noResultConstraintLayout = view.findViewById(R.id.cl_flightresult);
    }

    // setup flight recyclerview
    private void setupRecyclerview() {
        flightRecyclerviewAddapter = new FlightRecyclerviewAddapter(getContext() ,flightArrayList , new FlightRecyclerviewAddapter.OnFlightClicked() {
            @Override
            public void onClicked(int flightId) {
                Bundle bundle = new Bundle();
                bundle.putInt("flightId",flightId);
                FlightDetailsFragment flightDetailsFragment = new FlightDetailsFragment();
                flightDetailsFragment.setArguments(bundle);
                if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(flightDetailsFragment);
                }
            }
        });
        recyclerView.setLayoutManager(llManager);
        recyclerView.setAdapter(flightRecyclerviewAddapter);
    }

    // hiding floating action button when the user scroll to bottom
    private void hideFloatingActionButton() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
                    floatingActionButton.hide();
                } else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
                    floatingActionButton.show();
                }
            }
        });
    }



}
