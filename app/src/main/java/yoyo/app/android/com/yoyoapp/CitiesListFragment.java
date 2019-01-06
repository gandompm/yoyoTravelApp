package yoyo.app.android.com.yoyoapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yoyo.app.android.com.yoyoapp.Addapters.CitiesRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.DataModels.City;


public class CitiesListFragment extends Fragment {

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cities_list, container, false);
//
//        RecyclerView recyclerView = view.findViewById(R.id.rv_cities);
//        CitiesRecyclerviewAddaptor citiesRecyclerviewAddaptor = new CitiesRecyclerviewAddaptor(City.getFakeCities(), itemListener, getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(citiesRecyclerviewAddaptor);


        return view;
    }

}
