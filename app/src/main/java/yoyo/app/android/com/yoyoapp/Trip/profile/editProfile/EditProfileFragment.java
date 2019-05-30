package yoyo.app.android.com.yoyoapp.Trip.profile.editProfile;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.hbb20.CountryCodePicker;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;


public class EditProfileFragment extends Fragment {

    private Button saveButton;
    private EditText firstnameEditText, phoneNumberEditText,lastnameEditText, usernameEditText;
    private TextView emailTextview;
    private ImageView userImageview, backImageView;
    private CountryCodePicker ccp;
    private Uri imageUri = null;
    private UserSharedManager userSharedManager;
    private FragmentManager fragmentManager;
    private User user;
    private EditProfileViewModel editProfileViewModel;
    private View view;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile_trip, container, false);

        init();
        getDataFromSharedPrefrence();
        onClick();
        retrieveData();
        saveUserData();


        return view;
    }

    // get user's data from shared preferences
    private void getDataFromSharedPrefrence() {
        user = userSharedManager.getUser();
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
        editProfileViewModel.initGetProfile();
        editProfileViewModel.getProfile().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    firstnameEditText.setText(user.getFirstName());
                    lastnameEditText.setText(user.getLastName());
                    usernameEditText.setText(user.getUserName());
                    emailTextview.setText(user.getEmail());
                    phoneNumberEditText.setText(user.getPhoneNumber());
                }
            }
        });
    }



    private void init() {
        editProfileViewModel = ViewModelProviders.of(getActivity()).get(EditProfileViewModel.class);
        firstnameEditText = view.findViewById(R.id.et_edit_profile_firstname);
        lastnameEditText = view.findViewById(R.id.et_edit_profile_lastname);
        emailTextview = view.findViewById(R.id.tv_edit_profile_email);
        userImageview = view.findViewById(R.id.iv_edit_profile_img);
        backImageView = view.findViewById(R.id.iv_editprofile_back);
        fragmentManager = getFragmentManager();
        saveButton = view.findViewById(R.id.button_edit_profile_save);
        usernameEditText = view.findViewById(R.id.et_edit_profile_username);
        phoneNumberEditText = view.findViewById(R.id.et_edit_profile_phone_number);
        userSharedManager = new UserSharedManager(getContext());
    }

    // pop fragment from back stack
    private void onClick() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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
                        // todo fix user id
                        jsonObject.put("user_id",userSharedManager.getUser().getId());
                        jsonObject.put("username", usernameEditText.getText().toString());
                        jsonObject.put("firstname", firstnameEditText.getText().toString());
                        jsonObject.put("lastname", lastnameEditText.getText().toString());
                        jsonObject.put("email", emailTextview.getText().toString());
                        jsonObject.put("phone_number",phoneNumberEditText.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    editProfileViewModel.initEditProfile(jsonObject);
                    editProfileViewModel.getEditedProfile().observe(getActivity(), new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            if (user!= null)
                            {
                                Toast.makeText(getContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
                                userSharedManager.saveUser(user);
                                fragmentManager.popBackStack();
                            }
                            else
                            {
                                Toast.makeText(getContext(), getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }




}

