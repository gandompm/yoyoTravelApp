package yoyo.app.android.com.yoyoapp.Trip.authentication;

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
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.Utils;


public class SignUpFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText , userNameEditText , passwordEditText, emailEditText, phoneNumberEditText;
    private Button signUpButton;
    private ProgressBar progressBar;
    private UserSharedManager userSharedManager;
    private View view;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup,container,false);

        init();
        setupSignup();

        return view;
    }

    private void signUpRequest(JSONObject jsonObjectRequest) {
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
            }
        });
    }

    // sign up, check errors , save data to shared pref
    private void setupSignup() {

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean flag = true;

                if (firstNameEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.first_name_not_empty)).show();
                    flag =false;
                }
                if (lastNameEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.lastname_not_empty)).show();
                    flag =false;
                }
                if (userNameEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.youserUsername_can_not_be_emp_y)).show();
                    flag =false;
                }
                if (passwordEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.Password_can_not_be_empty)).show();
                    flag =false;
                }
                if (emailEditText.getText().toString().equals("")){
                    Toasty.error(getContext(),getString(R.string.email_can_not_empty)).show();
                    flag =false;
                }



                if (flag){

                    progressBar.setVisibility(View.VISIBLE);
                    JSONObject jsonObjectRequest = new JSONObject();
                    try {
                        jsonObjectRequest.put("firstname", firstNameEditText.getText().toString());
                        jsonObjectRequest.put("lastname", lastNameEditText.getText().toString());
                        jsonObjectRequest.put("username", userNameEditText.getText().toString());
                        jsonObjectRequest.put("email", emailEditText.getText().toString());
                        jsonObjectRequest.put("phone_number", phoneNumberEditText.getText().toString());
                        jsonObjectRequest.put("password", passwordEditText.getText().toString());
                        jsonObjectRequest.put("password", passwordEditText.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    signUpRequest(jsonObjectRequest);
                }

            }
        });
    }



    private void init() {
        firstNameEditText = view.findViewById(R.id.et_signup_firstname);
        lastNameEditText = view.findViewById(R.id.et_signup_lastname);
        userNameEditText = view.findViewById(R.id.et_signup_username);
        passwordEditText = view.findViewById(R.id.et_signup_password);
        phoneNumberEditText = view.findViewById(R.id.et_signup_phone_number);
        emailEditText = view.findViewById(R.id.et_signup_email);
        signUpButton = view.findViewById(R.id.button_signup_signup);
        progressBar = view.findViewById(R.id.progressbar);
        userSharedManager = new UserSharedManager(getContext());
        fragmentManager = getFragmentManager();
    }

}
