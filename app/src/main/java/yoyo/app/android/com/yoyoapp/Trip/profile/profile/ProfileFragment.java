package yoyo.app.android.com.yoyoapp.Trip.profile.profile;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import de.hdodenhof.circleimageview.CircleImageView;
import org.json.JSONException;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.User;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.LanguageDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Profile.*;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.Utils.UserSharedManager;
import yoyo.app.android.com.yoyoapp.Trip.authentication.AuthenticationActivity;
import yoyo.app.android.com.yoyoapp.Trip.profile.SignOutFragment;
import yoyo.app.android.com.yoyoapp.Trip.profile.editProfile.EditProfileFragment;
import yoyo.app.android.com.yoyoapp.Trip.profile.travellerCompanion.TravellerCompanionFragment;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    private FragmentManager fragmentManager;
    private User user;
    private TextView nameTextview, languageTextview;
    private ImageView circleImageView;
    private UserSharedManager userSharedManager;
    private boolean isSignedIn = true;
    private ProfileViewModel profileViewModel;
    private String encodedProfileImage;
    private ImageView  travellerCompanionImageview, rulesImageview, editProfileImageview ,signoutImageview, languageImageView, aboutImageview;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_trip, container, false);

        init();
        checkingSignIn();
        retrieveProfileData();

        travellerCompanionImageview.setOnClickListener(v -> {if(isSignedIn) setupTravellerCompanionPage();});
        rulesImageview.setOnClickListener(v -> setupRulesPage());
        editProfileImageview.setOnClickListener(v -> {if(isSignedIn) setupEditProfilePage();});
        signoutImageview.setOnClickListener(v -> { if (isSignedIn) setupSignOutPage(); });
        aboutImageview.setOnClickListener(v -> setupAboutPage());
        languageImageView.setOnClickListener(v -> setupLanguage());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                saveImageProfile(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
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

    private void saveImageRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profile_picture", encodedProfileImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        profileViewModel.sendImageProfile(jsonObject);
        profileViewModel.getProfilePicture().observe(getActivity(), profilePicture -> {
            if (profilePicture != null) {
                //  profileProgressbar.setVisibility(View.GONE);
                userSharedManager.saveProfilePhoto(encodedProfileImage);
            }
            else
            {
//                profileProgressbar.setVisibility(View.VISIBLE);
            }
        });
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    private void setupAboutPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new AboutFragment()).addToBackStack("about");
        fragmentTransaction.commit();
    }

    // show user the language dialog fragment
    private void setupLanguage() {
        LanguageDialogFragment languageDialogFragment = new LanguageDialogFragment();
        languageDialogFragment.show(getFragmentManager(), "setting language");
    }

    private void setupSignOutPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new SignOutFragment()).addToBackStack("signout");
        fragmentTransaction.commit();
    }

    private void setupEditProfilePage() {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new EditProfileFragment()).addToBackStack("edit profile");
            fragmentTransaction.commit();
    }

    private void setupRulesPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,new RuleFragment()).addToBackStack("rules");
        fragmentTransaction.commit();
    }

    private void setupTravellerCompanionPage() {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new TravellerCompanionFragment()).addToBackStack("traveller companion");
            fragmentTransaction.commit();
    }

    private void retrieveProfileData() {
        User user = userSharedManager.getUser();
        if (user.getFirstName().equals(""))
        {
            //todo set profile data
//            apiServiceFlight.retrieveProfileData(new ApiServiceFlight.OnProfileDataRecieved() {
//                @Override
//                public void onRecieved(User user) {
//                    if (user!=null)
//                    nameTextview.setText(user.getFirstName() + " " + user.getLastName());
//                }
//            });
        }
    }

    // if the user has not signed in yet, pop up the signup activity
    private void checkingSignIn() {
        user = userSharedManager.getUser();
        if (userSharedManager.getToken().equals(""))
        {
            popUpSignInSignUpActivity();
            isSignedIn = false;
        }
        else
        {
            setupViewsFromSharedPref();
        }
    }

    private void setupViewsFromSharedPref() {
        nameTextview.setText(user.getFirstName()+" "+ user.getLastName());
        isSignedIn = true;
        if( !user.getProfilePicture().equalsIgnoreCase("") ){
            byte[] b = Base64.decode(user.getProfilePicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            circleImageView.setImageBitmap(bitmap);
        }
    }

    // setup signup activity
    private void popUpSignInSignUpActivity() {
        startActivity(new Intent(getActivity(), AuthenticationActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_up,  R.anim.no_animation);
    }


    private void init() {
        profileViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
        rulesImageview = view.findViewById(R.id.iv_profile_rules);
        travellerCompanionImageview = view.findViewById(R.id.iv_profile_traveller_companion);
        editProfileImageview = view.findViewById(R.id.iv_profile_edit_profile);
        nameTextview = view.findViewById(R.id.tv_profile_name);
        signoutImageview = view.findViewById(R.id.iv_profile_signout);
        fragmentManager = getFragmentManager();
        userSharedManager = new UserSharedManager(getContext());
        languageImageView = view.findViewById(R.id.iv_profile_language);
        aboutImageview = view.findViewById(R.id.iv_profile_aboout);
        languageTextview = view.findViewById(R.id.tv_profile_language);
        circleImageView = view.findViewById(R.id.iv_profile);
    }




}
