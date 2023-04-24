package yoyo.app.android.com.yoyoapp.Flight;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.hbb20.CountryCodePicker;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.User;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.R;


public class EditProfileFragment extends Fragment {

    private Button saveButton;
    private EditText firstnameEditText,phoneNumberEditText,lastnameEditText, usernameEditText;
    private TextView emailTextview;
    private ImageView userImageview, backImageView;
    private CountryCodePicker ccp;
    private Uri imageUri = null;
    private ApiServiceFlight apiServiceFlight;
    private UserSharedManagerFlight userSharedManager;
    private FragmentManager fragmentManager;
    private View view;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        init();
        getDataFromSharedPrefrence();
        onClick();
        retrieveData();
        saveUserData();




        return view;
    }

    // get user's data from shared preferences
    private void getDataFromSharedPrefrence() {
        User user = userSharedManager.getClient();
        if (user != null)
        {
            firstnameEditText.setText(user.getFirstName());
            lastnameEditText.setText(user.getLastName());
            emailTextview.setText(user.getEmail());
            usernameEditText.setText(user.getUserName());
        }
    }

    // update user's data from server after retrieving data form shared pref
    private void retrieveData() {
        apiServiceFlight.retrieveProfileData(new ApiServiceFlight.OnProfileDataRecieved() {
            @Override
            public void onRecieved(User user) {
                if (user != null)
                {
                    firstnameEditText.setText(user.getFirstName());
                    lastnameEditText.setText(user.getLastName());
                    usernameEditText.setText(user.getUserName());
                    emailTextview.setText(user.getEmail());
                }
            }
        });
    }



    private void init() {
        firstnameEditText = view.findViewById(R.id.et_edit_profile_firstname);
        lastnameEditText = view.findViewById(R.id.et_edit_profile_lastname);
        emailTextview = view.findViewById(R.id.tv_edit_profile_email);
        userImageview = view.findViewById(R.id.iv_edit_profile_img);
        backImageView = view.findViewById(R.id.iv_editprofile_back);
        fragmentManager = getFragmentManager();
        apiServiceFlight = new ApiServiceFlight(getContext());
        saveButton = view.findViewById(R.id.button_edit_profile_save);
        usernameEditText = view.findViewById(R.id.et_edit_profile_username);
        userSharedManager = new UserSharedManagerFlight(getContext());
    }

    // pop fragment from back stack
    private void onClick() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });
    }

    // save user's data in server and then save the data in shared pref
    private void saveUserData() {

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstnameEditText.getText().toString().trim().isEmpty())
                {
                    firstnameEditText.setError(getString(R.string.first_name_cannot_be_empty));
                    Toast.makeText(getContext(), getString(R.string.please_complete_firstname), Toast.LENGTH_SHORT).show();
                }
                else if (lastnameEditText.getText().toString().trim().isEmpty())
                {
                    lastnameEditText.setError(getString(R.string.last_name_cannot_be_empty));
                    Toast.makeText(getContext(), getString(R.string.please_complete_lastname), Toast.LENGTH_SHORT).show();
                }
                else if (emailTextview.getText().toString().trim().isEmpty())
                {
                    
                }
                else if (usernameEditText.getText().toString().trim().isEmpty())
                {
                    usernameEditText.setError(getString(R.string.email_cannot_be_empty));
                    Toast.makeText(getContext(), getString(R.string.please_complete_username), Toast.LENGTH_SHORT).show();
                }
                else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("emailOrPassword", usernameEditText.getText().toString());
                        jsonObject.put("first_name", firstnameEditText.getText().toString());
                        jsonObject.put("last_name", lastnameEditText.getText().toString());
                        jsonObject.put("email", emailTextview.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    apiServiceFlight.editProfile(jsonObject, new ApiServiceFlight.OnEditRequest() {
                        @Override
                        public void onResult(User user) {
                            if (user!= null)
                            {
                                Toast.makeText(getContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
                                userSharedManager.saveClient(user);
                                fragmentManager.popBackStack();
                            }
                        }
                    });
                }
            }
        });
    }




}

