package yoyo.app.android.com.yoyoapp.Trip.profile.editProfile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {

    private Button saveButton;
    private EditText firstnameEditText, phoneNumberEditText,lastnameEditText, usernameEditText;
    private TextView emailTextview;
    private ImageView backImageView;
    private ImageView circleImageView;
    private UserSharedManager userSharedManager;
    private FragmentManager fragmentManager;
    private ProgressBar profilePictureProgressbar;
    private User user;
    private String encodedProfileImage;
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
        circleImageView.setOnClickListener(v->openImageFromGallery());


        return view;
    }

    private void openImageFromGallery() {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            cropingFunc();
                        } else {
                            Toast.makeText(getContext(), "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private void cropingFunc() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(512, 512)
                .start(getActivity());
    }

    private void saveImageProfile(Uri imageUri) {

        circleImageView.setImageURI(imageUri);
        final InputStream imageStream;
        try {
            imageStream = getContext().getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            encodedProfileImage = encodeImage(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        saveImageRequest();
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    private void saveImageRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profile_picture", encodedProfileImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editProfileViewModel.sendImageProfile(jsonObject);
        editProfileViewModel.getProfilePicture().observe(getActivity(), profilePicture -> {
            if (profilePicture != null) {
                userSharedManager.saveProfilePhoto(encodedProfileImage);
            }
            else
            {
            }
            profilePictureProgressbar.setVisibility(View.GONE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                profilePictureProgressbar.setVisibility(View.VISIBLE);
                saveImageProfile(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
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
            if( !user.getProfilePicture().equalsIgnoreCase("") ){
                byte[] b = Base64.decode(user.getProfilePicture(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                circleImageView.setImageBitmap(bitmap);
            }
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
        backImageView = view.findViewById(R.id.iv_editprofile_back);
        fragmentManager = getFragmentManager();
        saveButton = view.findViewById(R.id.button_edit_profile_save);
        usernameEditText = view.findViewById(R.id.et_edit_profile_username);
        phoneNumberEditText = view.findViewById(R.id.et_edit_profile_phone_number);
        circleImageView = view.findViewById(R.id.iv_edit_profile_img);
        profilePictureProgressbar = view.findViewById(R.id.progressbar_edit_profile_image);
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

