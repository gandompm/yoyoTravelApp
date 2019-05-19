package yoyo.app.android.com.yoyoapp.Trip.dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;
import yoyo.app.android.com.yoyoapp.Trip.adapter.CategoryRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.Trip.search.TripSearchViewModel;

import java.util.ArrayList;
import java.util.List;


public class CategotyFilterBottomSheetDialogFragment extends BottomSheetDialogFragment implements CategoryRecyclerviewAddapter.OnItemCategorySelected {

    private ArrayList<Category> categorieList;
    private RecyclerView recyclerView;
    private CategoryRecyclerviewAddapter adapter;
    private ArrayList<String> categoryNames;
    private TripSearchViewModel tripSearchViewModel;
    private TextView resetTextview;
    private Button applyButton;
    private BotomSheetListener bottomSheetListener;
    private ImageView closeImageview;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_filter_category,container,false);


        init();
        setupRecyclerview();
        getCategories();
        setupApplyButton();
        setupCloseButton();
        setupResetButton();

        return view;
    }

    private void setupResetButton() {
        resetTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TripActivity)getActivity()).categories.clear();
                for (Category cat :
                        categorieList) {
                    cat.setSelected(false);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupCloseButton() {
        closeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }



    private void setupApplyButton() {
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save selected airlines to main activity for later usages
                ((TripActivity)getActivity()).categories = categoryNames;
                bottomSheetListener.onApplyClicked(categoryNames);
                dismiss();
            }
        });
    }

    private void init() {
        categorieList = new ArrayList<>();
        categoryNames = ((TripActivity)getActivity()).categories;
        tripSearchViewModel = ViewModelProviders.of(getActivity()).get(TripSearchViewModel.class);
        applyButton = view.findViewById(R.id.button_filter_price_apply);
        closeImageview = view.findViewById(R.id.iv_filter_price_close);
        resetTextview = view.findViewById(R.id.tv_filter_price_reset);
    }

    public interface BotomSheetListener
    {
        void onApplyClicked(ArrayList<String> catNames);
    }

    private void getCategories() {
        tripSearchViewModel.initCategoryList();
        tripSearchViewModel.getCategoryList().observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if (categories != null) {
                    categorieList.clear();
                    categorieList.addAll(categories);
                    if (adapter == null)
                    {
                        adapter = new CategoryRecyclerviewAddapter(categorieList, getContext(), CategotyFilterBottomSheetDialogFragment.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                        adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setupRecyclerview() {
        recyclerView = view.findViewById(R.id.rv_category);
        recyclerView.setLayoutManager( new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onSelectd(String catName) {
        if (categoryNames.contains(catName)) {
            categoryNames.remove(catName);
        } else {
            categoryNames.add(catName);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        bottomSheetListener = (BotomSheetListener) context;
    }

    public static CategotyFilterBottomSheetDialogFragment newInstance()
    {
        return new CategotyFilterBottomSheetDialogFragment();
    }
}
