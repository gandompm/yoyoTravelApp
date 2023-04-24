package yoyo.app.android.com.yoyoapp.MaterialViewPager;


import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yoyo.app.android.com.yoyoapp.Addapters.HotelsRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.DataModels.Hotel;
import yoyo.app.android.com.yoyoapp.R;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;


public class HotelListFragment extends Fragment {

    private View view;
    private NestedScrollView nestedScrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_hotel_list,container,false);

        HotelsRecyclerviewAddaptor hottelsRecyclerviewAddaptor = new HotelsRecyclerviewAddaptor(getContext(),Hotel.fakeHotelData());
        RecyclerView recyclerView =(RecyclerView) view.findViewById(R.id.rv_hotellist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hottelsRecyclerviewAddaptor);

        nestedScrollView = view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), nestedScrollView);

        return view;
    }

}
