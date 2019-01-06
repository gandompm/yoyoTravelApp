package yoyo.app.android.com.yoyoapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


public class SignOutFragment extends Fragment {


    private GoogleAuthentication googleAuthentication;
    private Button signout;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_out, container, false);

        init();
        setupSignout();

        return view;
    }

    private void init() {
        signout = view.findViewById(R.id.button_sign_out_log_out);
        googleAuthentication = new GoogleAuthentication(getContext());
    }



    private void setupSignout() {

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleAuthentication.googleSignOut();
                UserSharedManager userSharedManager = new UserSharedManager(getContext());
                userSharedManager.clearSharedPref();
            }
        });
    }
}
