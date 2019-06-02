package yoyo.app.android.com.yoyoapp.Trip.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.InfiniteScrollProvider;
import yoyo.app.android.com.yoyoapp.Trip.adapter.FoldingCellRecyclerviewAdapter;
import yoyo.app.android.com.yoyoapp.Utils;

import java.util.List;


public class TripResultFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "TripsListSearchResultFr";
    private FloatingActionButton floatingActionButton;
    private Bundle bundle;
    private RecyclerView recyclerView;
    private ShimmerRecyclerView shimmerRecycler;
    private FoldingCellRecyclerviewAdapter adapter;
    private TripListViewModel tripListViewModel;
    private TripQuery tripQuery;
    private int page = 1;
    private View view;
    private ImageView backImageView;
    private TextView backTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_result,container,false);

        bundle = getArguments();
        init();
        setupRecyclerview();
        setupDate();
        setupFloatingActionButton();
        getTrips();
        setupOnclickListener();

        return view;
    }

    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.mainrv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FoldingCellRecyclerviewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView, () -> {
            Toast.makeText(getContext(), "load more", Toast.LENGTH_SHORT).show();
            tripListViewModel.initTripList(page, tripQuery);
            tripListViewModel.getTripList().observe(getActivity(), trips -> {
                if (trips != null) {
                    adapter.addTrips(trips);
                    setupSnackBar();

                    page++;
                }
            });
        });
    }


    private void getTrips() {
        shimmerRecycler.showShimmerAdapter();
        tripListViewModel = ViewModelProviders.of(getActivity()).get(TripListViewModel.class);
        tripListViewModel.initTripList(page,tripQuery);
        tripListViewModel.getTripList().observe(getActivity(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if (trips != null) {
                    shimmerRecycler.hideShimmerAdapter();

                    adapter.addTrips(trips);
                    setupSnackBar();

                    page++;
                }
            }
        });
    }

    private void setupSnackBar() {
        // TODO: 5/30/2019 fixing showing sum of results
        Snackbar snackbar = Snackbar.make(view, 27 +" " + getString(R.string.results), Snackbar.LENGTH_LONG)
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


    }

    private void init() {
        floatingActionButton = view.findViewById(R.id.fbutton_hotellistsearchresult);
        shimmerRecycler = view.findViewById(R.id.shimmer_recycler_view);
        backImageView = view.findViewById(R.id.iv_trip_search_back);
        backTextView = view.findViewById(R.id.tv_trip_search_back);

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
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.container ,new TripFilterFragment());
                fragmentTransaction.addToBackStack("trip_filter");
                fragmentTransaction.commit();
                floatingActionButton.hide();
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
            getFragmentManager().popBackStack();

        }
    }





}

