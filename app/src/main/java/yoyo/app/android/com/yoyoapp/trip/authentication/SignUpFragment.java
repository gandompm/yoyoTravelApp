package yoyo.app.android.com.yoyoapp.trip.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import es.dmoral.toasty.Toasty;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.Utils;


public class SignUpFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText , passwordEditText, emailEditText, phoneNumberEditText;
    private Button signUpButton;
    private ProgressBar progressBar;
    private UserSharedManager userSharedManager;
    private View view;
    private FragmentManager fragmentManager;

    private static final String TAG = "SignUpFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup,container,false);

        init();
        setupSignup();

        return view;
    }

    private void signUpRequest(JSONObject jsonObjectRequest) {
        signUpButton.setClickable(false);
        AuthViewModel authViewModel = ViewModelProviders.of(getActivity()).get(AuthViewModel.class);
        authViewModel.initSignUp(jsonObjectRequest);
        authViewModel.getSignUpResult().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                progressBar.setVisibility(View.GONE);
                if (user != null)
                {
                    Toasty.success(getContext(),getString(R.string.thanks_for_joining)).show();
                    userSharedManager.saveUser(user);
                    if (((AuthenticationActivity)getActivity()).isFromSchedule)
                    {
                        getActivity().finish();
                        getActivity().overridePendingTransition(0, 0);
                    }
                    else {
                        Intent i = new Intent();
                        i.putExtra(Utils.KEY_BUNDLE_MAINACTIVITY, true);
                        getActivity().setResult(getActivity().RESULT_OK,i);
                        getActivity().finish();
                    }
                }
                signUpButton.setClickable(true);
            }
        });
    }

    // sign up, check errors , save data to shared pref
    private void setupSignup() {

        signUpButton.setOnClickListener(v -> {

            Boolean flag = true;

            if (firstNameEditText.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.first_name_not_empty)).show();
                flag = false;
            }
            else if (lastNameEditText.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.lastname_not_empty)).show();
                flag = false;
            }
            else if (passwordEditText.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.Password_can_not_be_empty)).show();
                flag = false;
            }
            else if (passwordEditText.getText().length() <= 7){
                Toasty.error(getContext(),"Password can not be less than 8 characters").show();
                flag = false;

            }
            else if (emailEditText.getText().toString().equals("")){
                Toasty.error(getContext(),getString(R.string.email_can_not_empty)).show();
                flag = false;
            }
            else if (!emailEditText.getText().toString().matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$")){
                Toasty.error(getContext(),"Your Email Address is incorrect").show();
                flag = false;
            }

            if (flag){
                progressBar.setVisibility(View.VISIBLE);
                JSONObject jsonObjectRequest = new JSONObject();
                try {
                    jsonObjectRequest.put("firstname", firstNameEditText.getText().toString());
                    jsonObjectRequest.put("lastname", lastNameEditText.getText().toString());
                    jsonObjectRequest.put("email", emailEditText.getText().toString());
                    jsonObjectRequest.put("phone_number", phoneNumberEditText.getText().toString());
                    jsonObjectRequest.put("password", passwordEditText.getText().toString());
                    jsonObjectRequest.put("password", passwordEditText.getText().toString());

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                signUpRequest(jsonObjectRequest);
            }

        });
    }



    private void init() {
        firstNameEditText = view.findViewById(R.id.et_signup_firstname);
        lastNameEditText = view.findViewById(R.id.et_signup_lastname);
        passwordEditText = view.findViewById(R.id.et_signup_password);
        phoneNumberEditText = view.findViewById(R.id.et_signup_phone_number);
        emailEditText = view.findViewById(R.id.et_signup_email);
        signUpButton = view.findViewById(R.id.button_signup_signup);
        progressBar = view.findViewById(R.id.progressbar);
        userSharedManager = new UserSharedManager(getContext());
        fragmentManager = getFragmentManager();
    }

}
