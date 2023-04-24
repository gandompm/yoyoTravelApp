package yoyo.app.android.com.yoyoapp.SearchDialog;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class SearchDialogFragment extends Fragment {


    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_dialog,container,false);

        new SimpleSearchDialogCompat(getContext(), "Search...",
                "What are you looking for...?", null, createSampleData(),
                new SearchResultListener<SampleSearchModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           SampleSearchModel item, int position) {
                        Toast.makeText(getContext(), item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();


        return view;
    }
    private ArrayList<SampleSearchModel> createSampleData(){
        ArrayList<SampleSearchModel> items = new ArrayList<>();
        items.add(new SampleSearchModel("First item"));
        items.add(new SampleSearchModel("Second item"));
        items.add(new SampleSearchModel("Third item"));
        items.add(new SampleSearchModel("The ultimate item"));
        items.add(new SampleSearchModel("Last item"));
        items.add(new SampleSearchModel("Lorem ipsum"));
        items.add(new SampleSearchModel("Dolor sit"));
        items.add(new SampleSearchModel("Some random word"));
        items.add(new SampleSearchModel("guess who's back"));
        return items;
    }
}
