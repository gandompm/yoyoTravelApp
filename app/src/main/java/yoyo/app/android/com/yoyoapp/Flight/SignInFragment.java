package yoyo.app.android.com.yoyoapp.Flight;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import es.dmoral.toasty.Toasty;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.User;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.R;


public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";
    private Button signinButton;
    private EditText userNameEditText ,passwordEditText;
    private TextView userNameTextView ,passwordTextView;
    private ApiServiceFlight apiServiceFlight;
    private FragmentManager fragmentManager;
    private ProgressBar progressBar;
    private UserSharedManagerFlight userSharedManager;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in_flight, container, false);

        init();
        setupSigninButton();

        return view;
    }

    private void init() {
        signinButton = view.findViewById(R.id.Button_signin_signin);
        apiServiceFlight = new ApiServiceFlight(getContext());
        userNameEditText = view.findViewById(R.id.et_signin_email_username);
        passwordEditText = view.findViewById(R.id.et_signin_password);
        userNameTextView = view.findViewById(R.id.tv_signin_email_username);
        passwordTextView = view.findViewById(R.id.tv_signin_password);
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManagerFlight(getContext());
        progressBar = view.findViewById(R.id.progressbar);
    }

    // before signing in, check the probable errors
    private void setupSigninButton() {
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean flag = true;
                if (userNameEditText.getText().toString().equals("")){
                    Log.d(TAG, "onClick: xxxxxx1");
                    Toasty.error(getContext(),getString(R.string.youserUsername_can_not_be_emp_y)).show();
                    flag = false;
                }
                if (passwordEditText.getText().toString().equals("")){
                    Log.d(TAG, "onClick: xxxxxx2");
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

                    apiServiceFlight.setupSignIn(jsonObject, result -> {
                        progressBar.setVisibility(View.GONE);
                        if (result != null)
                        {
                            User user = new User();
                            user.setToken(result);
                            userSharedManager.saveClient(user);
                            Toast.makeText(getContext(), getString(R.string.welcome_to_yoyo_app), Toast.LENGTH_SHORT).show();
                            Toasty.success(getContext(),getString(R.string.welcome_to_yoyo_app)).show();
                            getActivity().finish();
                        }
                        else
                        {
                            Toasty.error(getContext(),getString(R.string.failed)).show();
                        }
                    });
                }
            }
        });
    }


}
