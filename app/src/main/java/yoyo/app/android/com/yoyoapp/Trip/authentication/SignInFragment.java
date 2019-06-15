package yoyo.app.android.com.yoyoapp.Trip.authentication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.Utils;


public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";
    private Button signinButton;
    private EditText userNameEditText ,passwordEditText;
    private TextView userNameTextView ,passwordTextView;
    private FragmentManager fragmentManager;
    private ProgressBar progressBar;
    private UserSharedManager userSharedManager;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        init();
        setupSigninButton();

        return view;
    }

    private void init() {
        signinButton = view.findViewById(R.id.Button_signin_signin);
        userNameEditText = view.findViewById(R.id.et_signin_email_username);
        passwordEditText = view.findViewById(R.id.et_signin_password);
        userNameTextView = view.findViewById(R.id.tv_signin_email_username);
        passwordTextView = view.findViewById(R.id.tv_signin_password);
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManager(getContext());
        progressBar = view.findViewById(R.id.progressbar);
    }

    // before signing in, check the probable errors
    private void setupSigninButton() {
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean flag = true;
                if (userNameEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.youserUsername_can_not_be_emp_y)).show();
                    flag = false;
                }
                if (passwordEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.Password_can_not_be_empty)).show();
                    flag = false;
                }

                if (flag){
                    progressBar.setVisibility(View.VISIBLE);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", userNameEditText.getText().toString());
                        jsonObject.put("password", passwordEditText.getText().toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // todo sign in request
                    sendToSignInPage(jsonObject);
                }
            }
        });
    }

    private void sendToSignInPage(JSONObject jsonObject) {
        AuthViewModel authViewModel = ViewModelProviders.of(getActivity()).get(AuthViewModel.class);
        authViewModel.initSignIn(jsonObject);
        authViewModel.getSignInResult().observe(getActivity(), user -> {
            if (user != null) {
                userSharedManager.saveUser(user);
                Toasty.success(getContext(),getString(R.string.welcome_to_yoyo_app)).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra(Utils.KEY_BUNDLE_MAINACTIVITY, true);
                startActivity(i);
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
            }
            else
            {
                Toasty.error(getContext(),getString(R.string.failed)).show();
            }
        });
    }


}
