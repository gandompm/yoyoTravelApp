package yoyo.app.android.com.yoyoapp.Flight.FlightDetails;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.squareup.picasso.Picasso;
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingActivity;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.SignUpDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.Flight.Utils.ItemAnimation;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;

import java.text.DecimalFormat;

public class FlightDetailsFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "FlightDetailsFragment";
    private ImageView plusAdultImageview, minusAdultImageview, plusChildImageview, minusChildImageview, plusinfantImageview, minusInfantImageview ;
    private Button applyTravellerButton;
    private ImageView backImageview ,airlineLogoImageview;
    private TextView adultNumTextview, childNumTextview, infantNumTextview,flightNumberTextview, departureTimeTextview, capacityTextview, airlineTextview;
    private TextView aircraftTextview, originCityTextview, destinationCityTextview, flightPathTextview, originIata, destinationIata ,departureDateTextview;
    private TextView adultPriceTextview, childPriceTextview, infantPriceTextview ,flightTypeTextview, departureTimeTopTextview ,totalPriceTextview;
    private int adultNum= 1, childNum= 0, infantNum =0, sum =0 ,flightId;
    private FragmentManager fragmentManager;
    private ProgressBar bookingProgressbar, progressBar;
    private LinearLayout linearLayout;
    private FlightDetailsPresenter flightDetailsPresenter;
    private DecimalFormat decimalFormat;
    private double adultPrice, childPrice, infantPrice;
    private UserSharedManagerFlight userSharedManager;
    private CardView cardView, cardView2;
    private ConstraintLayout constraintLayout;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_flight_details, container, false);
        init();
        setupAnimation();
        getDataWithBundle();
        setupCounters();
        getFlightDetails();
        return view;
    }

    // animation for card views
    private void setupAnimation() {
        cardView = view.findViewById(R.id.cv_flightdetails);
        cardView2 = view.findViewById(R.id.cv_flightdetails_2);
        constraintLayout = view.findViewById(R.id.cl_flightdetails_2);

        ItemAnimation.animate(constraintLayout, 0,1);
        ItemAnimation.animate(cardView, 1,1);
        ItemAnimation.animate(cardView2, 2,1);
    }

    // calculate the total tickets price
    private void updateTotalPrice() {
        totalPriceTextview.setText(String.valueOf(adultPrice * adultNum + childPrice * childNum + infantPrice * infantNum));
    }

    // get flight id of previous fragment for sending flight detail request
    private void getDataWithBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null)
            flightId = bundle.getInt("flightId", 0);
    }

    // setup adult, child and infant counters
    private void setupCounters() {
        if (adultNum != 1)
        {
            adultNumTextview.setText(String.valueOf(adultNum));
            appearView(minusAdultImageview, adultNumTextview);
        }
        if (childNum != 0)
        {
            childNumTextview.setText(String.valueOf(childNum));
            appearView(minusChildImageview, childNumTextview);
        }
        if (infantNum != 0)
        {
            infantNumTextview.setText(String.valueOf(infantNum));
            appearView(minusInfantImageview, infantNumTextview);
        }
    }

    private void init() {
        decimalFormat = new DecimalFormat("#,###,###");
        linearLayout = view.findViewById(R.id.rv_flightDetails);
        progressBar = view.findViewById(R.id.progressBar_flightdetails);
        departureDateTextview = view.findViewById(R.id.tv_flightdetails_departure_date);
        departureTimeTopTextview = view.findViewById(R.id.tv_flightdetails_departure_time_top);
        flightTypeTextview = view.findViewById(R.id.tv_flightdetails_type);
        adultPriceTextview = view.findViewById(R.id.tv_flightdetails_adult_price);
        childPriceTextview = view.findViewById(R.id.tv_flightdetails_child_price);
        infantPriceTextview = view.findViewById(R.id.tv_flightdetails_infant_price);
        flightNumberTextview = view.findViewById(R.id.tv_flightdetails_flight_nomber);
        departureTimeTextview = view.findViewById(R.id.tv_flightdetails_departure_time);
        capacityTextview = view.findViewById(R.id.tv_flightdetails_capacity);
        airlineTextview = view.findViewById(R.id.tv_flightdetails_airline);
        aircraftTextview = view.findViewById(R.id.tv_flightdetails_aircraft);
        originCityTextview = view.findViewById(R.id.tv_flightdetails_origin);
        destinationCityTextview = view.findViewById(R.id.tv_flightdetails_destination);
        flightPathTextview = view.findViewById(R.id.tv_flightdetails_flight_path);
        originIata = view.findViewById(R.id.tv_flightdetails_origin_iata);
        destinationIata = view.findViewById(R.id.tv_flightdetails_destination_iata);
        plusAdultImageview = view.findViewById(R.id.iv_flight_plus);
        minusAdultImageview = view.findViewById(R.id.iv_flight_minus);
        plusChildImageview = view.findViewById(R.id.iv_flight_plus_child);
        minusChildImageview = view.findViewById(R.id.iv_flight_minus_child);
        plusinfantImageview = view.findViewById(R.id.iv_flight_plus_infant);
        minusInfantImageview = view.findViewById(R.id.iv_flight_minus_infant);
        applyTravellerButton = view.findViewById(R.id.button_flight_apply_traveller);
        adultNumTextview = view.findViewById(R.id.tv_flight_num);
        childNumTextview = view.findViewById(R.id.tv_flight_num_child);
        infantNumTextview = view.findViewById(R.id.tv_flight_num_infant);
        backImageview = view.findViewById(R.id.iv_flightdetails_back);
        totalPriceTextview = view.findViewById(R.id.tv_flightdetails_totalprice);
        bookingProgressbar = view.findViewById(R.id.progressBar_flightdetails_booking);
        airlineLogoImageview = view.findViewById(R.id.iv_flightdetails_airline);
        adultNum = ((MainFlightActivity)getContext()).adultCount;
        childNum = ((MainFlightActivity)getContext()).childCount;
        infantNum = ((MainFlightActivity)getContext()).infantCount;
        flightDetailsPresenter = new FlightDetailsPresenter(getContext(), linearLayout);
        userSharedManager = new UserSharedManagerFlight(getContext());
        sum = ((MainFlightActivity)getContext()).sum;
        fragmentManager = getFragmentManager();
        applyTravellerButton.setOnClickListener(this);
        plusinfantImageview.setOnClickListener(this);
        plusChildImageview.setOnClickListener(this);
        plusAdultImageview.setOnClickListener(this);
        minusInfantImageview.setOnClickListener(this);
        minusChildImageview.setOnClickListener(this);
        minusAdultImageview.setOnClickListener(this);
        backImageview.setOnClickListener(this);
    }

    // send flight details request, and then initializing views
    private void getFlightDetails() {

        progressBar.setVisibility(View.VISIBLE);

        flightDetailsPresenter.getFlightDetails(flightId, flight -> {

            progressBar.setVisibility(View.GONE);
            if (flight != null) {
                departureDateTextview.setText(flight.getDapartureDate());
                flightNumberTextview.setText(flight.getFlightNumber());
                departureTimeTextview.setText(flight.getDepartureTime());
                departureTimeTopTextview.setText(flight.getDepartureTime());
                capacityTextview.setText(String.valueOf(flight.getCapacity()));
                airlineTextview.setText(flight.getAirline());
                aircraftTextview.setText(flight.getAirCraftName() + " (" + flight.getAirCraftManufacturer() + ")");
                originCityTextview.setText(flight.getOriginCity());
                destinationCityTextview.setText(flight.getDestinationCity());
                flightPathTextview.setText(flight.getOriginAirport() + " - " + flight.getDestinationAirport());
                originIata.setText(flight.getOriginIataCode());
                destinationIata.setText(flight.getDestinationIataCode());
                if (flight.getAdultPrice() != null) {
                    // convert prices to standard price formats
                    String adultPrice = decimalFormat.format(Double.valueOf(flight.getAdultPrice()));
                    adultPriceTextview.setText(adultPrice);
                }
                if (flight.getChildPrice() != null) {
                    String childPrice = decimalFormat.format(Double.valueOf(flight.getChildPrice()));
                    childPriceTextview.setText(childPrice);
                }
                if (flight.getInfantPrice() != null) {
                    String infantPrice = decimalFormat.format(Double.valueOf(flight.getInfantPrice()));
                    infantPriceTextview.setText(infantPrice);
                }
                if (flight.getAirlineLogo() != null || !flight.getAirlineLogo().equals("")) {
                    Picasso.with(getContext()).load(flight.getAirlineLogo()).placeholder(getContext().getResources().getDrawable(R.drawable.airline_default_logo))
                            .error(getContext().getResources().getDrawable(R.drawable.airline_default_logo)).into(airlineLogoImageview);
                }
                flightTypeTextview.setText(flight.getType());
                infantPrice = Double.parseDouble(flight.getInfantPrice());
                adultPrice = Double.parseDouble(flight.getAdultPrice());
                childPrice = Double.parseDouble(flight.getChildPrice());
                updateTotalPrice();
            }

        });
    }

    // setup button click listeners
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_flight_plus:
                if (adultNum < 9) {
                    adultNum++;
                    sum++;
                    adultNumTextview.setText(String.valueOf(adultNum));
                    updateTotalPrice();
                }
                if (adultNum ==2)
                {
                    appearView(minusAdultImageview, adultNumTextview);
                }
                break;
            case R.id.iv_flight_minus:
                if (adultNum > 1) {
                    adultNum--;
                    sum--;
                    adultNumTextview.setText(String.valueOf(adultNum));
                    updateTotalPrice();
                }
                if (adultNum == 1)
                {
                    minusAdultImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_light_24dp));
                }
                break;
            case R.id.iv_flight_plus_child:
                if (childNum < 9) {
                    childNum++;
                    sum++;
                    childNumTextview.setText(String.valueOf(childNum));
                    updateTotalPrice();
                }
                if (childNum == 1)
                {
                    appearView(minusChildImageview, childNumTextview);
                }
                break;
            case R.id.iv_flight_minus_child:
                if (childNum > 0) {
                    childNum--;
                    sum--;
                    childNumTextview.setText(String.valueOf(childNum));
                    updateTotalPrice();
                }
                if (childNum == 0)
                {
                    disappearView(minusChildImageview, childNumTextview);
                }
                break;
            case R.id.iv_flight_minus_infant:
                if (infantNum > 0) {
                    infantNum--;
                    sum--;
                    infantNumTextview.setText(String.valueOf(infantNum));
                    updateTotalPrice();
                }
                if (infantNum== 0)
                {
                    disappearView(minusInfantImageview, infantNumTextview);
                }
                break;
            case R.id.iv_flight_plus_infant:
                if (infantNum < 9) {
                    infantNum++;
                    sum++;
                    infantNumTextview.setText(String.valueOf(infantNum));
                    updateTotalPrice();
                }
                if (infantNum==1)
                {
                    appearView(minusInfantImageview, infantNumTextview);
                }
                break;
            case R.id.button_flight_apply_traveller:
                // send user to booking process, when user has before signed in
                if (!userSharedManager.getToken().equals(""))
                goToBookingActivity();
                // show sign up dialog
                else
                {
                    SignUpDialogFragment signUpDialogFragment = new SignUpDialogFragment();
                    signUpDialogFragment.show(getFragmentManager(), "sign up");
                }
                break;
            case R.id.iv_flightdetails_back:
                getActivity().onBackPressed();
                break;
        }
    }

    // send user to booking activity and setup bundle
    public void goToBookingActivity() {

                    bookingProgressbar.setVisibility(View.VISIBLE);

                    flightDetailsPresenter.preReserveFlight(flightId,adultNum,childNum,infantNum, voucherNumber-> {
                        bookingProgressbar.setVisibility(View.GONE);

                            Intent intent = new Intent(getContext(), BookingActivity.class);
                            intent.putExtra("adultNum",adultNum);
                            intent.putExtra("childNum",childNum);
                            intent.putExtra("infantNum",infantNum);
                            intent.putExtra("voucherNumber", voucherNumber);
                            getActivity().finish();
                            startActivity(intent);

                    });

    }

    // appear minus when the number is more than 0
    public void appearView(ImageView imageView,TextView textView)
    {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    // disappear minus when the number equals 0
    public void disappearView(ImageView imageView,TextView textView)
    {
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_light_24dp));
        textView.setTextColor(getResources().getColor(R.color.white1));
    }

}
