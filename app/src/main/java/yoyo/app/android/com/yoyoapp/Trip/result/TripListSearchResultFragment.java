package yoyo.app.android.com.yoyoapp.Trip.result;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import yoyo.app.android.com.yoyoapp.BottomSheet.TripFilterBottomSheet;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.Trip.DatePickerFragment;
import yoyo.app.android.com.yoyoapp.Trip.adapter.FoldingCellListAdapter;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ramotion.foldingcell.FoldingCell;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Utils;


public class TripListSearchResultFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "TripsListSearchResultFr";
    private FloatingActionButton floatingActionButton;
    private ListView theListView;
    private Toolbar toolbar;
    private Bundle bundle;
    private TextView cityNameTextview ,startDateTextview ,endDateTextview , durationTextview;
    private int listSize;
    private TripFilterBottomSheet tirpFilterBottomSheet;
    private ShimmerRecyclerView shimmerRecycler;
    private BottomSheetBehavior bottomSheetBehaviorFilter;
    private RelativeLayout relativeLayout;
    private ArrayList<Trip> tirpArrayList;
    private FoldingCellListAdapter adapter;
    private TripListViewModel tirpListViewModel;
    private TripQuery tripQuery;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_list_search_result,container,false);

        bundle = getArguments();
        init();
        tirpFilterBottomSheet = new TripFilterBottomSheet(getContext(),view);
        setupDate();
        getTirps();
        floatingActionButtonFunction();
        setupToolbar();

        return view;
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
                        setupFoldingcell();
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
        Snackbar snackbar = Snackbar.make(view, listSize  +" " + getString(R.string.results), com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
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
        theListView = view.findViewById(R.id.mainListView);
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


    private void setupFoldingcell() {


//        // add custom btn handler to first list item
//        tirpArrayList.get(0).setRequestBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext().getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
//            }
//        });

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        adapter = new FoldingCellListAdapter(getContext(), tirpArrayList);


        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

        listSize = tirpArrayList.size();
    }
    private void floatingActionButtonFunction() {

        theListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
                {
                    floatingActionButton.show();
                }
                else
                {
                    floatingActionButton.hide();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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

