package yoyo.app.android.com.yoyoapp.Flight;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import es.dmoral.toasty.Toasty;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.User;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.R;


public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";
    private ApiServiceFlight apiServiceFlight;
    private EditText firstNameEditText, lastNameEditText , userNameEditText , passwordEditText, emailEditText;
    private TextView firstNameTextView, lastNameTextView , userNameTextView , passwordTextView, emailTextView;
    private Button signUpButton;
    private ProgressBar progressBar;
    private UserSharedManagerFlight userSharedManager;
    private View view;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_flight,container,false);

        init();
        setupSignup();

        return view;
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
                        jsonObjectRequest.put("first_name", firstNameEditText.getText().toString());
                        jsonObjectRequest.put("last_name", lastNameEditText.getText().toString());
                        jsonObjectRequest.put("emailOrPassword", userNameEditText.getText().toString());
                        jsonObjectRequest.put("email", emailEditText.getText().toString());
                        jsonObjectRequest.put("password1", passwordEditText.getText().toString());
                        jsonObjectRequest.put("password2", passwordEditText.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    apiServiceFlight.setupSignUp(jsonObjectRequest, new ApiServiceFlight.OnSignupComplete() {
                        @Override
                        public void onSignUp(User user) {
                            progressBar.setVisibility(View.GONE);
                            if (user != null)
                            {
                                Toasty.success(getContext(),getString(R.string.thanks_for_joining)).show();
                                userSharedManager.saveClient(user);
                                Log.d(TAG, "onResponse: qw2 : "+ user.getToken());
                                getActivity().finish();
                            }
                            else
                            {
                                Toasty.error(getContext(),getString(R.string.failed)).show();
                            }
                        }
                    });
                }

            }
        });
    }



    private void init() {
        firstNameEditText = view.findViewById(R.id.et_signup_firstname);
        lastNameEditText = view.findViewById(R.id.et_signup_lastname);
        userNameEditText = view.findViewById(R.id.et_signup_username);
        passwordEditText = view.findViewById(R.id.et_signup_password);
        emailEditText = view.findViewById(R.id.et_signup_email);
        firstNameTextView = view.findViewById(R.id.tv_signup_firstname);
        lastNameTextView = view.findViewById(R.id.tv_signup_lastname);
        userNameTextView = view.findViewById(R.id.tv_signup_username);
        passwordTextView = view.findViewById(R.id.tv_signup_password);
        emailTextView = view.findViewById(R.id.tv_signup_email);
        signUpButton = view.findViewById(R.id.button_signup_signup);
        progressBar = view.findViewById(R.id.progressbar);
        apiServiceFlight = new ApiServiceFlight(getContext());
        userSharedManager = new UserSharedManagerFlight(getContext());
        fragmentManager = getFragmentManager();
    }

}
