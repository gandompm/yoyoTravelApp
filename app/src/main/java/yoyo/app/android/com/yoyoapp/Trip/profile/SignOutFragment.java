package yoyo.app.android.com.yoyoapp.Trip.profile;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;

public class SignOutFragment extends Fragment {

    private TextView signoutTextview;
    private UserSharedManager userSharedManager;
    private FragmentManager fragmentManager;
    private View view;
    ImageView backImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_out, container, false);

        init();
        onClick();

        return view;
    }

    private void init() {
        backImageView = view.findViewById(R.id.iv_sign_out_back);
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManager(getContext());
        signoutTextview = view.findViewById(R.id.tv_sign_out_log_out);
    }

    // sign out and erase the shared pref data for user
    private void onClick() {

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        signoutTextview.setOnClickListener(v->{
            userSharedManager.clearSharedPref();
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        });


    }

}
