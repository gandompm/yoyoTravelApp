package yoyo.app.android.com.yoyoapp.Hotel;


import android.graphics.Typeface;
import android.os.Bundle;

import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.BottomSheet.DatePickerBottomSheet;
import com.appyvet.materialrangebar.RangeBar;
import yoyo.app.android.com.yoyoapp.DataModels.Hotel;
import yoyo.app.android.com.yoyoapp.Addapters.HotelsRecyclerviewAddaptor;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Utils;


public class HotelListSearchResultFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "HotelListSearchResultFr";
    private FloatingActionButton floatingActionButton;
    private BottomSheetBehavior bottomSheetBehaviorFilter, bottomSheetBehaviorDatePicker;
    private CardView cardview;
    private RecyclerView recyclerView;
    private Button filterButton;
    private Toolbar toolbar;
    private RangeBar rangeBar;
    private Bundle bundle;
    private TextView cityNameTextview ,startDateTextview ,endDateTextview , durationTextview;
    private TextView priceRange;
    private ShimmerRecyclerView shimmerRecycler;
    private int listSize;
    private ImageView closeButton;
    private DatePickerBottomSheet datePickerBottomSheet;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotle_list_search_result,container,false);

        init();
        datePickerBottomSheet = new DatePickerBottomSheet(getContext(),view);
        setupShimmerLayout();
        bundle = getArguments();
        setupToolbarViews();
        floatingActionButtonFunction();
        setupFilterButton();
        setupToolbar();
        setupCloseBottomSheet();

        priceRange = view.findViewById(R.id.tv_filterhotel_price);
        rangeBar = view.findViewById(R.id.rangebar_filterhotel_price);
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                priceRange.setText(getString(R.string.from_capital) + " " + String.valueOf(leftPinValue) + "$" + " " +getString(R.string.to) + " " + String.valueOf(rightPinValue) + "$");
            }
        });

        return view;
    }

    private void setupCloseBottomSheet() {
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehaviorFilter.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void setupShimmerLayout() {
        shimmerRecycler.showShimmerAdapter();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerRecycler.hideShimmerAdapter();
                try {
                    setupRecyclerview();
                    setupSnackBar();
                }
                catch (NullPointerException exeption)
                {
                    Log.e(TAG, "setupSnackBar: null pointer exception: " + exeption.toString() );
                }

            }
        }, 3000);
    }

    private void setupToolbarViews() {
        String searchText = bundle.getString(Utils.INSTANCE.getKEY_BUNDLE_SEARCH_STRING_CODE());
        String startDate = bundle.getString(Utils.INSTANCE.getKEY_BUNDLE_FROM_DATE_CODE());
        String endDate = bundle.getString(Utils.INSTANCE.getKEY_BUNDLE_TO_DATE_CODE());
        long duration = bundle.getLong(Utils.INSTANCE.getKEY_BUNDLE_NIGHT_NUM_CODE());
        startDateTextview.setOnClickListener(this);
        endDateTextview.setOnClickListener(this);

        cityNameTextview.setText(searchText);
        startDateTextview.setText(startDate);
        endDateTextview.setText(endDate);
        durationTextview.setText(duration + " " + getString(R.string.nights));
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

    private void setupFilterButton() {
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehaviorFilter.setState(BottomSheetBehavior.STATE_HIDDEN);
                floatingActionButton.show();
            }
        });
    }

    private void floatingActionButtonFunction() {

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

    private void setupRecyclerview() {

        HotelsRecyclerviewAddaptor hottelsRecyclerviewAddaptor = new HotelsRecyclerviewAddaptor(getContext(),Hotel.fakeHotelData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hottelsRecyclerviewAddaptor);

        listSize = Hotel.fakeHotelData().size();
    }

    private void init() {

        floatingActionButton = view.findViewById(R.id.fbutton_hotellistsearchresult);
        cardview =  view.findViewById(R.id.bottom_sheet_hotel_filter);
        bottomSheetBehaviorFilter = BottomSheetBehavior.from(cardview);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_hotellistsearchresult);
        endDateTextview = view.findViewById(R.id.tv_search_check_out);
        durationTextview = view.findViewById(R.id.tv_search_night_num);
        cityNameTextview = view.findViewById(R.id.tv_hotel_list_city);
        filterButton = view.findViewById(R.id.button_filterhotel_filter);
        startDateTextview = view.findViewById(R.id.tv_search_check_in);
        toolbar =(Toolbar) view.findViewById(R.id.tb_hotelsearch);
        closeButton = view.findViewById(R.id.iv_filter_hotel_close);
        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.cl_hotellist);
        shimmerRecycler = (ShimmerRecyclerView) view.findViewById(R.id.shimmer_recycler_view);
        LinearLayout llBottomSheet = (LinearLayout) view.findViewById(R.id.ll_datepicker_bottom_sheet);
        bottomSheetBehaviorDatePicker = BottomSheetBehavior.from(llBottomSheet);

    }

    private void setupSnackBar() {
        Snackbar snackbar = Snackbar.make(view, listSize  + " " + getString(R.string.results), Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
        TextView searchResultNumHotel = (TextView) sbView.findViewById(R.id.snackbar_text);
        searchResultNumHotel.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        Typeface typeface = ResourcesCompat.getFont(getContext(),R.font.roboto_medium);
        searchResultNumHotel.setTypeface(typeface);
        snackbar.show();
    }


    public void expandBottonSheet(final BottomSheetBehavior bottomSheetBehavior)
    {
        final CardView filterCardView = view.findViewById(R.id.cv_hotel_list);
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

