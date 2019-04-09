package yoyo.app.android.com.yoyoapp;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import yoyo.app.android.com.yoyoapp.BottomSheet.DatePickerBottomSheet;
import yoyo.app.android.com.yoyoapp.BottomSheet.TourFilterBottomSheet;
import yoyo.app.android.com.yoyoapp.DataModels.Tour;
import yoyo.app.android.com.yoyoapp.Addapters.FoldingCellListAdapter;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ramotion.foldingcell.FoldingCell;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ToursListSearchResultFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ToursListSearchResultFr";
    private FloatingActionButton floatingActionButton;
    private ListView theListView;
    private Toolbar toolbar;
    private Bundle bundle;
    private TextView cityNameTextview ,startDateTextview ,endDateTextview , durationTextview;
    private int listSize;
    private TourFilterBottomSheet tourFilterBottomSheet;
    private ShimmerRecyclerView shimmerRecycler;
    private DatePickerBottomSheet datePickerBottomSheet;
    private BottomSheetBehavior bottomSheetBehaviorFilter,bottomSheetBehaviorDatePicker;
    private RelativeLayout relativeLayout;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tour_list_search_result,container,false);

        bundle = getArguments();
        init();
        datePickerBottomSheet = new DatePickerBottomSheet(getContext(),view);
        setupShimmerLayout();
        tourFilterBottomSheet = new TourFilterBottomSheet(getContext(),view);
        setupDate();
        floatingActionButtonFunction();
        setupToolbar();

        return view;
    }

    private void setupShimmerLayout() {
        shimmerRecycler.showShimmerAdapter();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerRecycler.hideShimmerAdapter();
                try {
                    setupFoldingcell();
                    setupSnackBar();
                }
                catch (NullPointerException exeption)
                {
                    Log.e(TAG, "setupSnackBar: null pointer exception: " + exeption.toString() );
                }

            }
        }, 3000);
    }

    private void setupSnackBar() {
        Snackbar snackbar = Snackbar.make(view, listSize  +" " + getString(R.string.results), com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();

        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        snackbar.show();
    }

    private void setupDate() {
        String searchText = bundle.getString(SearchFragment.KEY_BUNDLE_SEARCH_STRING_CODE);
        String startDate = bundle.getString(SearchFragment.KEY_BUNDLE_FROM_DATE_CODE);
        String endDate = bundle.getString(SearchFragment.KEY_BUNDLE_TO_DATE_CODE);
        long duration = bundle.getLong(SearchFragment.KEY_BUNDLE_NIGHT_NUM_CODE);
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
        cityNameTextview = view.findViewById(R.id.tv_tour_list_city);
        toolbar = view.findViewById(R.id.tb_hotelsearch);
        endDateTextview = view.findViewById(R.id.tv_search_check_out);
        durationTextview = view.findViewById(R.id.tv_search_night_num);
        startDateTextview = view.findViewById(R.id.tv_search_check_in);
        shimmerRecycler = view.findViewById(R.id.shimmer_recycler_view);
        relativeLayout = view.findViewById(R.id.bottom_sheet_tour_filter);
        bottomSheetBehaviorFilter = BottomSheetBehavior.from(relativeLayout);
        LinearLayout llBottomSheet = (LinearLayout) view.findViewById(R.id.ll_datepicker_bottom_sheet);
        bottomSheetBehaviorDatePicker = BottomSheetBehavior.from(llBottomSheet);
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


        // prepare elements to display
        final ArrayList<Tour> tours = Tour.getTestingList();

        // add custom btn handler to first list item
        tours.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });

        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getContext(), tours);

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

        listSize = tours.size();
    }
    private void floatingActionButtonFunction() {

        theListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
//                {
//                    floatingActionButton.hide();
//                }
//                else
//                {
//                    floatingActionButton.show();
//                }
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
        final CardView filterCardView = view.findViewById(R.id.cv_tour_list);
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
            bottomSheetBehaviorDatePicker.setState(BottomSheetBehavior.STATE_EXPANDED);
            expandBottonSheet(bottomSheetBehaviorDatePicker);
        }
    }


}

