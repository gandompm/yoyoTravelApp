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
import yoyo.app.android.com.yoyoapp.MainActivity;
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
    private ImageView  travellerCompanionImageview, rulesImageview, editProfileImageview ,signoutImageview, languageImageView, aboutImageview;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_trip, container, false);

        init();
        checkingSignIn();
        retrieveProfileData();

        travellerCompanionImageview.setOnClickListener(v -> {if(isSignedIn) setupTravellerCompanionPage();else popUpSignInSignUpActivity();});
        rulesImageview.setOnClickListener(v -> setupRulesPage());
        editProfileImageview.setOnClickListener(v -> {if(isSignedIn) setupEditProfilePage(); else popUpSignInSignUpActivity();});
        signoutImageview.setOnClickListener(v -> { if (isSignedIn) setupSignOutPage(); else popUpSignInSignUpActivity();});
        aboutImageview.setOnClickListener(v -> setupAboutPage());
        languageImageView.setOnClickListener(v -> setupLanguage());


        return view;
    }


    private void setupAboutPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((MainActivity)getActivity()).getCurrentContainer(),new AboutFragment()).addToBackStack(String.valueOf(((MainActivity)getActivity()).getCurrentContainer()));
        fragmentTransaction.commit();
    }

    // show user the language dialog fragment
    private void setupLanguage() {
        LanguageDialogFragment languageDialogFragment = new LanguageDialogFragment();
        languageDialogFragment.show(getFragmentManager(), "setting language");
    }

    private void setupSignOutPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((MainActivity)getActivity()).getCurrentContainer(),new SignOutFragment()).addToBackStack(String.valueOf(((MainActivity)getActivity()).getCurrentContainer()));
        fragmentTransaction.commit();
    }

    private void setupEditProfilePage() {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(((MainActivity)getActivity()).getCurrentContainer(),new EditProfileFragment()).addToBackStack(String.valueOf(((MainActivity)getActivity()).getCurrentContainer()));
            fragmentTransaction.commit();
    }

    private void setupRulesPage() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(((MainActivity)getActivity()).getCurrentContainer(),new RuleFragment()).addToBackStack(String.valueOf(((MainActivity)getActivity()).getCurrentContainer()));
        fragmentTransaction.commit();
    }

    private void setupTravellerCompanionPage() {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(((MainActivity)getActivity()).getCurrentContainer(),new TravellerCompanionFragment()).addToBackStack(String.valueOf(((MainActivity)getActivity()).getCurrentContainer()));
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
        if( !user.getProfilePicture().equalsIgnoreCase("") ){
            byte[] b = Base64.decode(user.getProfilePicture(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            circleImageView.setImageBitmap(bitmap);
        }
        isSignedIn = true;
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
