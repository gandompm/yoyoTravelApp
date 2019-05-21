package yoyo.app.android.com.yoyoapp.Trip.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import yoyo.app.android.com.yoyoapp.BottomSheet.TripFilterBottomSheet;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.DatePickerFragment;
import yoyo.app.android.com.yoyoapp.Trip.adapter.FoldingCellRecyclerviewAdapter;
import yoyo.app.android.com.yoyoapp.Utils;

import java.util.ArrayList;
import java.util.List;


public class TripResultFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "TripsListSearchResultFr";
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private Bundle bundle;
    private TextView cityNameTextview ,startDateTextview ,endDateTextview , durationTextview;
    private RecyclerView recyclerView;
    private TripFilterBottomSheet tirpFilterBottomSheet;
    private ShimmerRecyclerView shimmerRecycler;
    private BottomSheetBehavior bottomSheetBehaviorFilter;
    private RelativeLayout relativeLayout;
    private ArrayList<Trip> tirpArrayList;
    private FoldingCellRecyclerviewAdapter adapter;
    private TripListViewModel tirpListViewModel;
    private TripQuery tripQuery;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_list_search_result2,container,false);

        bundle = getArguments();
        init();
        setupRecyclerview();
        tirpFilterBottomSheet = new TripFilterBottomSheet(getContext(),view);
        setupDate();
        setupFloatingActionButton();
        getTirps();
        setupToolbar();


        return view;
    }

    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.mainrv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void getTirps() {
        shimmerRecycler.showShimmerAdapter();
        tirpArrayList = new ArrayList<>();


        tirpListViewModel = ViewModelProviders.of(getActivity()).get(TripListViewModel.class);
        tirpListViewModel.initTripList(tripQuery);
        tirpListViewModel.getTripList().observe(getActivity(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> tirps) {
                if (tirps != null) {
                    shimmerRecycler.hideShimmerAdapter();
                    tirpArrayList.clear();
                    tirpArrayList.addAll(tirps);

                    if (adapter == null) {
                        adapter = new FoldingCellRecyclerviewAdapter(getContext(),tirpArrayList);
                        recyclerView.setAdapter(adapter);
                        setupSnackBar();
                    }
                    else
                    {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void setupSnackBar() {
        Snackbar snackbar = Snackbar.make(view, tirpArrayList.size()  +" " + getString(R.string.results), Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();

        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        snackbar.show();
    }

    private void setupDate() {
        tripQuery = new TripQuery();
        String searchText = bundle.getString(Utils.KEY_BUNDLE_SEARCH_STRING_CODE);
        String startDate = bundle.getString(Utils.KEY_BUNDLE_FROM_DATE_CODE);
        String endDate = bundle.getString(Utils.KEY_BUNDLE_TO_DATE_CODE);
        String duration = bundle.getString(Utils.KEY_BUNDLE_NIGHT_NUM_CODE);
        tripQuery.setFromTime(bundle.getLong(Utils.KEY_BUNDLE_FROM_TIME_CODE));
        tripQuery.setToTime(bundle.getLong(Utils.KEY_BUNDLE_TO_TIME_CODE));
        tripQuery.setFromPrice(bundle.getInt(Utils.KEY_BUNDLE_FROM_PRICE_CODE));
        tripQuery.setToPrice(bundle.getInt(Utils.KEY_BUNDLE_TO_PRICE_CODE));
        tripQuery.setLocation(bundle.getString(Utils.KEY_BUNDLE_LOCATION_CODE));
        tripQuery.setCategories(bundle.getStringArrayList(Utils.KEY_BUNDLE_CATEGORIES_CODE));
        startDateTextview.setOnClickListener(this);
        endDateTextview.setOnClickListener(this);

        cityNameTextview.setText(searchText);
        startDateTextview.setText(startDate);
        endDateTextview.setText(endDate);
        durationTextview.setText(duration+ " " + getString(R.string.nights));
    }

    private void init() {
        floatingActionButton = view.findViewById(R.id.fbutton_hotellistsearchresult);
        cityNameTextview = view.findViewById(R.id.tv_tirp_list_city);
        toolbar = view.findViewById(R.id.tb_hotelsearch);
        endDateTextview = view.findViewById(R.id.tv_search_check_out);
        durationTextview = view.findViewById(R.id.tv_search_night_num);
        startDateTextview = view.findViewById(R.id.tv_search_check_in);
        shimmerRecycler = view.findViewById(R.id.shimmer_recycler_view);
        relativeLayout = view.findViewById(R.id.bottom_sheet_tirp_filter);
        bottomSheetBehaviorFilter = BottomSheetBehavior.from(relativeLayout);
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    private void setupFloatingActionButton() {

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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandBottonSheet(bottomSheetBehaviorFilter);
                floatingActionButton.hide();
            }
        });
    }

    public void expandBottonSheet(final BottomSheetBehavior bottomSheetBehavior)
    {
        final CardView filterCardView = view.findViewById(R.id.cv_tirp_list);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setPeekHeight(0);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

                if (slideOffset < 0.5)
                {
                    filterCardView.setVisibility(View.VISIBLE);

                }
                else if (slideOffset > 0.5)
                {
                    filterCardView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if ( v.getId() == R.id.tv_search_check_in
                || v.getId() == R.id.tv_search_check_out)
        {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_up,R.anim.no_animation);
            fragmentTransaction.add(R.id.container,new DatePickerFragment(arrayList -> {
                durationTextview.setText(arrayList.get(2));
                startDateTextview.setText(arrayList.get(3));
                endDateTextview.setText(arrayList.get(4));
            }));
            fragmentTransaction.addToBackStack("date_picker");
            fragmentTransaction.commit();
        }
    }


}

