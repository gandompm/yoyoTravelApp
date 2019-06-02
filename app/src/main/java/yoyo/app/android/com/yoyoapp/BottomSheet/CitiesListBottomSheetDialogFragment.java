package yoyo.app.android.com.yoyoapp.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yoyo.app.android.com.yoyoapp.Addapters.CitiesRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.DataModels.City;
import yoyo.app.android.com.yoyoapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesListBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cities_list, container, false);

//        RecyclerView recyclerView = view.findViewById(R.id.rv_cities);
        CitiesRecyclerviewAddaptor citiesRecyclerviewAddaptor = new CitiesRecyclerviewAddaptor(City.getFakeCities(), getContext(), new CitiesRecyclerviewAddaptor.RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked() {
                dismiss();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(citiesRecyclerviewAddaptor);


        return view;
    }






}
