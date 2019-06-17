package yoyo.app.android.com.yoyoapp;


import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;


public class AboutCityFragment extends Fragment {

    private NestedScrollView nestedScrollView;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_city, container, false);

        nestedScrollView = view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), nestedScrollView);

        return view;
    }

}
