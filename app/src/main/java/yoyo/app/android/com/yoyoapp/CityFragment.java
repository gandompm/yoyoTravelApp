package yoyo.app.android.com.yoyoapp;


import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yoyo.app.android.com.yoyoapp.Addapters.CitiesRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.R;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;


public class CityFragment extends Fragment {

    private NestedScrollView nestedScrollView;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city, container, false);

        Bundle bundle = getArguments();
        String cityName = bundle.getString(CitiesRecyclerviewAddaptor.KEY_BUNDLE_CITY_NAME);
        String cityImage = bundle.getString(CitiesRecyclerviewAddaptor.KEY_BUNDLE_CITY_IMAGE);

        nestedScrollView = view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), nestedScrollView);

        return view;
    }

}
