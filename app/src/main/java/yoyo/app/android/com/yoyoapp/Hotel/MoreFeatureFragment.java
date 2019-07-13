package yoyo.app.android.com.yoyoapp.Hotel;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import yoyo.app.android.com.yoyoapp.R;

public class MoreFeatureFragment extends Fragment {


    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more_feauture, container, false);




        return view;
    }

}
