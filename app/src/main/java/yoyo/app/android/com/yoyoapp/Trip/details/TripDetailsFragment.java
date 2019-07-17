package yoyo.app.android.com.yoyoapp.Trip.details;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import yoyo.app.android.com.yoyoapp.Addapters.AdapterImageSlider;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.GoogleMapActivity;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.DayPlanRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.Trip.schedule.ScheduleTripFragment;


import java.util.ArrayList;
import java.util.List;

public class TripDetailsFragment extends Fragment {

    private static final String TAG = "TripDetailsFragment";
    public static final String EXTRA_NAME = "cheese_name";
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private String tripId;
    private AdapterImageSlider adapterImageSlider;
    private FloatingActionButton floatingActionButton;
    private ArrayList<String> dayPlans;
    private LatLng fromLatlng,  toLatlng;
    private Button tripDetailsButton;
    private CircleImageView tourLeaderImageview;
    private String tourImage, tripTitle;
    private TextView dayNightNumTextview, priceTextview,titleTextview,
            title2Textview, nameTourLeader, familyNameLeader ,locationFromTextview, locationToTextview,
            attractionsTextview, rulesTextview, transportTextview, tourLeaderLanguageTextview,
            typeTextview, dayPlanTextview, mealsTextview, passengerCountTextview, itineraryTextview,
            descriptionTextview, routeTextview;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_details, container, false);

        init();
        setupViews();
        setupToolbar();
        floatingActionButton.setOnClickListener(v-> sendToGoogleMap());
        tripDetailsButton.setOnClickListener(v-> sendToSchedulePage());



        return view;
    }

    private void sendToSchedulePage() {
        ScheduleTripFragment scheduleTripFragment = new ScheduleTripFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tripId",tripId);
        bundle.putString("tourImage",tourImage);
        bundle.putString("title",tripTitle);
        scheduleTripFragment.setArguments(bundle);
        ((MainActivity)getActivity()).showFragment(this,scheduleTripFragment,false);
    }

    private void setupViews() {
        Bundle bundle = getArguments();

        tripId = bundle.getString("tripId");
        dayNightNumTextview.setText(bundle.getInt("days")+ " Days "+ bundle.getInt("nights")+ " Nights");
        nameTourLeader.setText(bundle.getString("leaderName"));
        titleTextview.setText(bundle.getString("tourName") + " • " + bundle.getInt("passengersCount") + " purchased");
        title2Textview.setText(bundle.getString("title"));
        tripTitle = bundle.getString("title");
        tourLeaderLanguageTextview.setText(bundle.getString("language"));
        locationFromTextview.setText(bundle.getString("locationTitleFrom"));
        locationToTextview.setText(bundle.getString("locationTitleTo"));
        fromLatlng = new LatLng(bundle.getDouble("fromLat"), bundle.getDouble("fromLong"));
        toLatlng = new LatLng(bundle.getDouble("toLat"), bundle.getDouble("toLong"));
        Picasso.with(getContext()).load(bundle.getString("leaderPicture")).placeholder(getContext().getDrawable(R.drawable.avatar)).into(tourLeaderImageview);
        // summary
        descriptionTextview.setText(bundle.getString("summary"));
        // transports
        StringBuilder transports = new StringBuilder();
        for (String transport : bundle.getStringArrayList("transportation")) {
            transports.append(transport).append(" ");
        }
        transportTextview.setText(transports.toString());
        // type
        StringBuilder types = new StringBuilder();
        for (String type : bundle.getStringArrayList("categories")) {
            types.append(type).append("\n");
        }
        typeTextview.setText(types.toString());
        // attractions
        StringBuilder attractions = new StringBuilder();
        for (String attraction : bundle.getStringArrayList("attractions")) {
            attractions.append(attraction).append("\n");
        }
        attractionsTextview.setText(attractions.toString());
        // rules
        StringBuilder rules = new StringBuilder();
        for (String rule : bundle.getStringArrayList("rules")) {
            rules.append(rule).append("\n");
        }
        rulesTextview.setText(rules.toString());
        // meals
        StringBuilder meals = new StringBuilder();
        for (String meal : bundle.getStringArrayList("meals")) {
            meals.append(meal).append("\n");
        }
        mealsTextview.setText(meals.toString());
        // images
        ArrayList<String> images = new ArrayList<>();
        for (String image : bundle.getStringArrayList("gallery")) {
            images.add(image);
        }
        tourImage =  bundle.getStringArrayList("gallery").get(0);
        initComponent(images);
        // day plan
        dayPlans = bundle.getStringArrayList("itinerary");
        dayPlanTextview.setText(bundle.getStringArrayList("itinerary").get(0));
        // route
        routeTextview.setText(bundle.getString("route"));
        setupDayRecyclerview();
    }

    private void init() {
        dayPlans = new ArrayList<>();
        tripDetailsButton = view.findViewById(R.id.button_tripdetails_book);
        dayPlanTextview = view.findViewById(R.id.tv_tripdetails_day_plan);
        floatingActionButton = view.findViewById(R.id.fb_tripdetails);
        dayNightNumTextview = view.findViewById(R.id.tv_tripdetails_daynightnum);
        priceTextview = view.findViewById(R.id.tv_tripdetails_price);
        titleTextview = view.findViewById(R.id.tv_tripdetails_title);
        title2Textview = view.findViewById(R.id.tv_tripdetails_title2);
        nameTourLeader = view.findViewById(R.id.tv_tripdetails_name);
        familyNameLeader = view.findViewById(R.id.tv_tripdetails_tourleader_familyname);
        locationFromTextview = view.findViewById(R.id.tv_tripdetails_location_from);
        locationToTextview = view.findViewById(R.id.tv_tripdetails_location_to);
        routeTextview = view.findViewById(R.id.tv_tripdetails_route);
        attractionsTextview = view.findViewById(R.id.tv_tripdetails_attractions);
        transportTextview = view.findViewById(R.id.tv_tripdetails_transport);
        rulesTextview = view.findViewById(R.id.tv_tripdetails_rule);
        tourLeaderImageview = view.findViewById(R.id.iv_tripdetails_tourleader);
        tourLeaderLanguageTextview = view.findViewById(R.id.tv_tripdetails_tourleader_language);
        typeTextview = view.findViewById(R.id.tv_tripdetails_type);
        mealsTextview = view.findViewById(R.id.tv_tripdetails_meals);
//        passengerCountTextview = view.findViewById(R.id.tv_tripdetails_people_num);
        itineraryTextview = view.findViewById(R.id.tv_tripdetails_itnarary);
        descriptionTextview = view.findViewById(R.id.tv_tripdetails_desc);
    }

    private void sendToGoogleMap() {
        Bundle bundle = new Bundle();
        bundle.putDouble("lat1",fromLatlng.latitude);
        bundle.putDouble("lat2",toLatlng.latitude);
        bundle.putDouble("long1",fromLatlng.longitude);
        bundle.putDouble("long2",toLatlng.longitude);
        Intent intent = new Intent(getContext(), GoogleMapActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setupDayRecyclerview() {
        RecyclerView recyclerView = view.findViewById(R.id.rv_tripdetails_day_plan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DayPlanRecyclerviewAddaptor addaptor = new DayPlanRecyclerviewAddaptor(getContext(), dayPlans, dayplan -> {
            dayPlanTextview.setText(dayplan);
        });
        recyclerView.setAdapter(addaptor);
    }

    private void setupToolbar() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);

        AppBarLayout appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(titleTextview.getText().toString());
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    private void initComponent(ArrayList<String> images) {
        layout_dots = view.findViewById(R.id.layout_dots);
        viewPager = view.findViewById(R.id.pager);
        adapterImageSlider = new AdapterImageSlider(getActivity(), images);

        List<String> items = new ArrayList<>();
        for (String url : images) {
            items.add(url);
        }

        adapterImageSlider.setItems(items);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
