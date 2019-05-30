package yoyo.app.android.com.yoyoapp.Trip.details;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import yoyo.app.android.com.yoyoapp.Addapters.AdapterImageSlider;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.GoogleMapActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.adapter.DayPlanRecyclerviewAddaptor;


import java.util.ArrayList;
import java.util.List;

public class TripDetailsFragment extends Fragment {

    private static final String TAG = "TripDetailsFragment";
    public static final String EXTRA_NAME = "cheese_name";
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private TripDetailsViewModel tripDetailsViewModel;
    private TextView tourNameTextview,dayPlanTextview;
    private FloatingActionButton floatingActionButton;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_details, container, false);
        setHasOptionsMenu(true);

        init();
        setupToolbar();
        setupDayRecyclerview();
        floatingActionButton.setOnClickListener(v-> sendToGoogleMap());
//        getTripDetails();


        ArrayList<String> images = new ArrayList<>();
        images.add("https://images.unsplash.com/photo-1530311583484-ea8bf4c407fb?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=5471ccdab61f9634e1d9dba21bb50a34&auto=format&fit=crop&w=750&q=80");
        images.add("https://www.igomorocco.com/wp-content/uploads/2019/01/desert-tour-from-marrakech-to-fes.jpg");
        initComponent(images);


        return view;
    }

    private void sendToGoogleMap() {
        startActivity(new Intent(getContext(), GoogleMapActivity.class));
    }

    private void setupDayRecyclerview() {
        RecyclerView recyclerView = view.findViewById(R.id.rv_tripdetails_day_plan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DayPlanRecyclerviewAddaptor addaptor = new DayPlanRecyclerviewAddaptor(getContext(), 3, new DayPlanRecyclerviewAddaptor.OnDayClickedListner() {
            @Override
            public void onClicked(int position) {
                dayPlanTextview.setText("day "+ ++position +":  Hiking, biking, mountain biking, horseback riding, and swimming.Hiking, biking, mountain biking, horseback riding, and swimming.\nHiking, biking, mountain biking, horseback riding, and swimming.\n");
            }
        });
        recyclerView.setAdapter(addaptor);
    }

    private void setupToolbar() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    collapsingToolbar.setTitle("Yazd Desert Tour");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    private void init() {
        tourNameTextview = view.findViewById(R.id.tv_tripdetails);
        dayPlanTextview = view.findViewById(R.id.tv_tripdetails_day_plan);
        floatingActionButton = view.findViewById(R.id.fb_tripdetails);
//        descriptionTextview = view.findViewById(R.id.tv_tripdetails_desc);
    }



    private void getTripDetails() {
        Bundle bundle = getArguments();
        String tripId = bundle.getString("tripId");
        tripDetailsViewModel = ViewModelProviders.of(getActivity()).get(TripDetailsViewModel.class);
        tripDetailsViewModel.initDetails(tripId);
        tripDetailsViewModel.getDetails().observe(getActivity(), new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                if (trip != null) {
                    tourNameTextview.setText(trip.getTour().getName()+ "• " + trip.getTitle());
//                    ArrayList<String> images = new ArrayList<>();
//                    images.add("https://www.igomorocco.com/wp-content/uploads/2019/01/desert-tour-from-marrakech-to-fes.jpg");
//                    images.add(trip.getImage());
//                    initComponent(images);

//                    descriptionTextview.setText(tr);


                }
            }
        });
    }


    private void initComponent(ArrayList<String> images) {
        layout_dots = (LinearLayout) view.findViewById(R.id.layout_dots);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
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
           getFragmentManager().popBackStack();
        }

        return super.onOptionsItemSelected(item);
    }

}
