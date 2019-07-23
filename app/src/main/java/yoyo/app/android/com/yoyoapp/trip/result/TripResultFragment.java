package yoyo.app.android.com.yoyoapp.trip.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.SharedDataViewModel;
import yoyo.app.android.com.yoyoapp.trip.Utils.InfiniteScrollProvider;
import yoyo.app.android.com.yoyoapp.trip.adapter.FoldingCellRecyclerviewAdapter;
import yoyo.app.android.com.yoyoapp.trip.dialog.TripFilterDialogFragment;
import yoyo.app.android.com.yoyoapp.Utils;

import java.util.ArrayList;
import java.util.List;


public class TripResultFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "TripsListSearchResultFr";
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ShimmerRecyclerView shimmerRecycler;
    private FoldingCellRecyclerviewAdapter adapter;
    private TripListViewModel tripListViewModel;
    private TripQuery tripQuery;
    private int page = 1;
    private View view;
    private boolean isMoreDataAvailable = true;
    private ToggleSwitch tripTypeToggleSwitch;
    private JellyRefreshLayout jellyRefreshLayout;
    private ImageView backImageView;
    private TextView backTextView;
    private ArrayList<String> categoryCodes;
    private SharedDataViewModel sharedDataViewModel;

    private int fromPrice = 0;
    private int toPrice = 20000000;
    private int minDuration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_result,container,false);

        init();
        setupRecyclerview();
        setupQuery("FLEXIBLE");
        setupFloatingActionButton();
        getTrips();
        setupToggleButton();
        setupOnclickListener();
        refresh();

        return view;
    }

    private void refresh() {
            jellyRefreshLayout.setPullToRefreshListener(view ->
            {
                jellyRefreshLayout.setRefreshing(true);
                getTrips();
            });
            View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, null);
            jellyRefreshLayout.setLoadingView(loadingView);
            jellyRefreshLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jellyRefreshLayout.setRefreshing(false);
                }
            });
    }


    private void setupToggleButton() {
        tripTypeToggleSwitch.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                page = 1;
                isMoreDataAvailable = true;
                adapter = new FoldingCellRecyclerviewAdapter(getContext(), TripResultFragment.this);
                recyclerView.setAdapter(adapter);
                if(i==0)
                {
                    setupQuery("FIXED");
                    shimmerRecycler.showShimmerAdapter();
                    getTrips();
                }
                else {
                    setupQuery("FLEXIBLE");
                    shimmerRecycler.showShimmerAdapter();
                    getTrips();
                }
            }
        });
    }

    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.mainrv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FoldingCellRecyclerviewAdapter(getContext(),this);
        recyclerView.setAdapter(adapter);
        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView, () -> {
            if (isMoreDataAvailable)
            {
                tripListViewModel.initTripList(page, tripQuery);
                tripListViewModel.getTripList().observe(getActivity(), trips -> {
                    if (trips != null && trips.size() > 0) {
                        Toast.makeText(getContext(), "load more", Toast.LENGTH_SHORT).show();
                        adapter.addTrips(trips);
                        page++;
                    }
                    else
                        isMoreDataAvailable = false;
                });
            }
        });
    }


    private void getTrips() {
        tripListViewModel = ViewModelProviders.of(getActivity()).get(TripListViewModel.class);
        tripListViewModel.initTripList(page,tripQuery);
        tripListViewModel.getTripList().observe(getActivity(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if (trips != null) {
                    if (trips.size()==0)
                    {
                        Toast.makeText(getContext(), "We couldn't find any Tour", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        setupSnackBar(trips.get(0).getResultsSize());
                        adapter.addTrips(trips);
                        page++;
                    }
                    shimmerRecycler.hideShimmerAdapter();
                    jellyRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void setupSnackBar(int resultsSize) {
        // TODO: 5/30/2019 fixing showing sum of results
        Snackbar snackbar = Snackbar.make(view, resultsSize +" " + getString(R.string.results), Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();

        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        snackbar.show();
    }

    private void setupQuery(String reserveType) {
        tripQuery = new TripQuery();
        Bundle bundle = getArguments();
        if (bundle!= null)
        {
            String searchText = bundle.getString(Utils.KEY_BUNDLE_SEARCH_STRING_CODE);
            String startDate = bundle.getString(Utils.KEY_BUNDLE_FROM_DATE_CODE);
            String endDate = bundle.getString(Utils.KEY_BUNDLE_TO_DATE_CODE);
            String duration = bundle.getString(Utils.KEY_BUNDLE_NIGHT_NUM_CODE);
        }
        tripQuery.setType(reserveType);
        tripQuery.setFromTime(sharedDataViewModel.getFromTime().getValue());
        tripQuery.setToTime(sharedDataViewModel.getToTime().getValue());
        tripQuery.setMinDuration(minDuration);
        tripQuery.setFromPrice(fromPrice);
        tripQuery.setToPrice(toPrice);
        tripQuery.setDestination(sharedDataViewModel.getDestination().getValue().getCode());
        tripQuery.setCategories(categoryCodes);
    }

    private void init() {
        jellyRefreshLayout = view.findViewById(R.id.jelly_refresh_home);
        floatingActionButton = view.findViewById(R.id.fbutton_hotellistsearchresult);
        shimmerRecycler = view.findViewById(R.id.shimmer_recycler_view);
        backImageView = view.findViewById(R.id.iv_trip_search_back);
        backTextView = view.findViewById(R.id.tv_trip_search_back);
        tripTypeToggleSwitch = view.findViewById(R.id.toggleSwitch_trip_search);
        tripTypeToggleSwitch.setCheckedPosition(1);
        sharedDataViewModel = ViewModelProviders.of(getActivity()).get(SharedDataViewModel.class);
        categoryCodes = new ArrayList<>();

        sharedDataViewModel.getFromPrice().observe(this, price -> fromPrice = price);
        sharedDataViewModel.getToPrice().observe(this, price -> toPrice = price);
        sharedDataViewModel.getCategories().observe(this, categories -> {
            for (Category category : categories){
                categoryCodes.add(category.getCode());
            }
        });
        sharedDataViewModel.getMinDuration().observe(this, minDuration -> this.minDuration = minDuration);
        sharedDataViewModel.getHasFiltersChanged().observe(this, isChanged -> {
            if(isChanged)
            {
                isMoreDataAvailable = true;
                page = 1;
                adapter = new FoldingCellRecyclerviewAdapter(getContext(), TripResultFragment.this);
                recyclerView.setAdapter(adapter);
                shimmerRecycler.showShimmerAdapter();
                setupQuery("FLEXIBLE");
                getTrips();
            }
        }
        );
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
                    TripFilterDialogFragment tripFilterDialogBottomSheetDialogFragment = TripFilterDialogFragment.newInstance();
                    tripFilterDialogBottomSheetDialogFragment.show(getFragmentManager(), "add_price_filter_dialog_fragment");
            }
        });
    }

    private void setupOnclickListener() {
        backTextView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.iv_trip_search_back
                || v.getId() == R.id.tv_trip_search_back)
        {
            setupBackButton();
        }
    }

    private void setupBackButton() {
        sharedDataViewModel.resetFilters();
        getActivity().onBackPressed();
    }


}

