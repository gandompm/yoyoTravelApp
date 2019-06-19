package yoyo.app.android.com.yoyoapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;


public class AboutCityFragment extends Fragment {

    private NestedScrollView nestedScrollView;
    private View view;
    private TextView aboutTextView, naturalTextView, historicalTextView, manMadeTextView,
            foodTextView, climateTextView, topExperienceTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_city, container, false);

        init();
        nestedScrollView = view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), nestedScrollView);
        getBundle();

        return view;
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        aboutTextView.setText(bundle.getString(Utils.KEY_BUNDLE_CITY_ABOUT));
        naturalTextView.setText(bundle.getString(Utils.KEY_BUNDLE_CITY_NATURAL_ATT));
        historicalTextView.setText(bundle.getString(Utils.KEY_BUNDLE_CITY_HISTORICAL_ATT));
        manMadeTextView.setText(bundle.getString(Utils.KEY_BUNDLE_CITY_MAN_MADE_ATT));
        foodTextView.setText(bundle.getString(Utils.KEY_BUNDLE_CITY_FOOD_SOUVENIR));
        climateTextView.setText(bundle.getString(Utils.KEY_BUNDLE_CITY_CLIMATE));
        topExperienceTextView.setText(bundle.getString(Utils.KEY_BUNDLE_CITY_FOOD_TOPEXPERIENCE));
    }

    private void init() {
        aboutTextView = view.findViewById(R.id.tv_city_about);
        naturalTextView = view.findViewById(R.id.tv_city_natural_attractions);
        historicalTextView = view.findViewById(R.id.tv_city_historical_attractions);
        manMadeTextView = view.findViewById(R.id.tv_city_man_made_attractions);
        foodTextView = view.findViewById(R.id.tv_city_food_attractions);
        climateTextView = view.findViewById(R.id.tv_city_climate);
        topExperienceTextView = view.findViewById(R.id.tv_city_top_experience);
    }

}
