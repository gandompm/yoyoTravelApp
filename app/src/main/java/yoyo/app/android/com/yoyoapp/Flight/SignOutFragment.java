package yoyo.app.android.com.yoyoapp.Flight;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.R;

public class SignOutFragment extends Fragment {

    private TextView signoutTextview;
    private UserSharedManagerFlight userSharedManager;
    private FragmentManager fragmentManager;
    private ApiServiceFlight apiServiceFlight;
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
        apiServiceFlight = new ApiServiceFlight(getContext());
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManagerFlight(getContext());
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

        signoutTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceFlight.signout(new ApiServiceFlight.onSignOutDone() {
                    @Override
                    public void onDone(boolean result) {
                        userSharedManager.clearSharedPref();
                        startActivity(new Intent(getContext(), MainFlightActivity.class));
                        getActivity().finish();
                    }
                });
            }
        });
    }

}
