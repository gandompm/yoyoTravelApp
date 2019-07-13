package yoyo.app.android.com.yoyoapp.Hotel;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.BottomSheet.RoomBottomSheetDialogFragment;
import com.dagang.library.GradientButton;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import yoyo.app.android.com.yoyoapp.R;


public class HotelDetailsFragment extends Fragment {
    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private GradientButton bookButton;
    private ImageView backImageview;
    private ScrollView scrollView;
    private ImageView hotelImageview;
    private ExpandableTextView expTv1, expTv2;
    private View view;
    private TextView moreFeauturesButton;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotel_details, container, false);

        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // for dash line
        init();
        setupBackButton();
        setupExpandableTextview();
        setupScroolview();
        setupBookButton();
        setupMoreFeature();
        setupHotelImageViewerLibrary();


        return view;
    }

    private void setupMoreFeature() {
        moreFeauturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new MoreFeatureFragment()).addToBackStack("more feature");
                fragmentTransaction.commit();
            }
        });
    }

    private void setupHotelImageViewerLibrary() {
        hotelImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    private void setupBookButton() {
        bookButton.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomBottomSheetDialogFragment roomBottomSheetFragment = RoomBottomSheetDialogFragment.newInstance();
                roomBottomSheetFragment.show(getFragmentManager(), "add_room_bottom_sheet_fragment");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupScroolview() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 0)
                {
                    bookButton.setVisibility(View.GONE);
                }
                else
                {
                    bookButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setupExpandableTextview() {
        expTv1.findViewById(R.id.expand_text_view);
        expTv1.setText("This hotel is connected to the Palais des Congrès and La Defense business district is only 5 minutes away by Metro. A 24-hour concierge, currency exchange and a business center are among the additional services. Porte Maillot Metro Station is an 8-minute walk away and the airport bus shuttles to Charles de Gaulle Airport are located right outside the hotel.");

        expTv2.findViewById(R.id.expand_text_view2);
        expTv2.setText("This hotel is connected to the Palais des Congrès and La Defense business district is only 5 minutes away by Metro. A 24-hour concierge, currency exchange and a business center are among the additional services. Porte Maillot Metro Station is an 8-minute walk away and the airport bus shuttles to Charles de Gaulle Airport are located right outside the hotel.");
    }

    private void init() {
        backImageview = view.findViewById(R.id.iv_hotel_detail_back);
        expTv1 =  view.findViewById(R.id.expand_text_view);
        expTv2 =  view.findViewById(R.id.expand_text_view2);
        bookButton = view.findViewById(R.id.button_hotel_details);
        hotelImageview = view.findViewById(R.id.iv_hotel_detail_img);
        scrollView = view.findViewById(R.id.sv_hoteldetails);
        moreFeauturesButton = view.findViewById(R.id.iv_hoteldetails_features_more );
    }

    private void setupBackButton() {
        backImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

}
