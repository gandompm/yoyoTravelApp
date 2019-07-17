package yoyo.app.android.com.yoyoapp.Trip.authentication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.Utils;


public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";
    private Button signinButton;
    private EditText emailPhoneNumberEditText,passwordEditText;
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
        emailPhoneNumberEditText = view.findViewById(R.id.et_signin_email_phone_number);
        passwordEditText = view.findViewById(R.id.et_signin_password);
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManager(getContext());
        progressBar = view.findViewById(R.id.progressbar);
    }

    // before signing in, check the probable errors
    private void setupSigninButton() {
        signinButton.setOnClickListener(v -> {

            Boolean flag = true;
            if (emailPhoneNumberEditText.getText().toString().equals("")){
                Toasty.error(getContext(),"Email Or Phone Number can not be empty!").show();
                flag = false;
            }
            else if (passwordEditText.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.Password_can_not_be_empty)).show();
                flag = false;
            }
            else if (passwordEditText.getText().length() <= 7){
                Toasty.error(getContext(),"Password can not be less than 8 characters").show();
                flag =false;

            }

            if (flag){
                progressBar.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject();
                try {
                    // TODO: 7/16/2019  
                    jsonObject.put("username", emailPhoneNumberEditText.getText().toString());
                    jsonObject.put("password", passwordEditText.getText().toString());
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();

                }

                // todo sign in request
                sendToSignInPage(jsonObject);
            }else {
                progressBar.setVisibility(View.GONE);

            }

        });
    }

    private void sendToSignInPage(JSONObject jsonObject) {
        signinButton.setClickable(false);
        AuthViewModel authViewModel = ViewModelProviders.of(getActivity()).get(AuthViewModel.class);
        authViewModel.initSignIn(jsonObject);
        authViewModel.getSignInResult().observe(getActivity(), user -> {
            if (user != null) {
                userSharedManager.saveUser(user);
                Toasty.success(getContext(),getString(R.string.welcome_to_yoyo_app)).show();
                if (((AuthenticationActivity)getActivity()).isFromSchedule)
                {
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                }
                else {
                    Intent i = new Intent();
                    i.putExtra(Utils.KEY_BUNDLE_MAINACTIVITY, true);
//                    startActivity(i);
//                    getActivity().finish();
//                    getActivity().overridePendingTransition(0, 0);
                    getActivity().setResult(getActivity().RESULT_OK,i);
                    getActivity().finish();
                }
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                Toasty.error(getContext(),getString(R.string.failed)).show();
            }
            signinButton.setClickable(true);
        });
    }


}
