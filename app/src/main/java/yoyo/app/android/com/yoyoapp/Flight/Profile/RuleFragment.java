package yoyo.app.android.com.yoyoapp.Flight.Profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment;
import yoyo.app.android.com.yoyoapp.R;


public class RuleFragment extends BaseFragment {

    private ImageView backImageView;
    private View view;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rule, container, false);

        fragmentManager = getFragmentManager();
        backImageView = view.findViewById(R.id.iv_rules_back);


        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return view;
    }

}
