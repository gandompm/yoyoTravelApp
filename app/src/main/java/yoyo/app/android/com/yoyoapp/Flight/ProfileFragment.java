package yoyo.app.android.com.yoyoapp.Flight;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.User;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.LanguageDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Profile.TravellerCompanionFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight;
import yoyo.app.android.com.yoyoapp.R;


public class ProfileFragment extends Fragment {

    private ApiServiceFlight apiServiceFlight;
    private FragmentManager fragmentManager;
    private User user;
    private TextView nameTextview,languageTextview;
    private UserSharedManagerFlight userSharedManager;
    private boolean isSignedIn = true;
    private ImageView  travellerCompanionImageview, rulesImageview, editProfileImageview ,signoutImageview, languageImageView, aboutImageview;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        init();
        checkingSignIn();
        getLanguage();
        retrieveProfileData();

        travellerCompanionImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // before sending to the next fragment, check that if the user has before signed in or not
                if (isSignedIn)
                {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container,new TravellerCompanionFragment()).addToBackStack("traveller companion");
                    fragmentTransaction.commit();
                }
            }
        });

        rulesImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new RuleFragment()).addToBackStack("rules");
                fragmentTransaction.commit();
            }
        });

        editProfileImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // before sending to the next fragment, check that if the user has before signed in or not
                if (isSignedIn)
                {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container,new EditProfileFragment()).addToBackStack("edit profile");
                    fragmentTransaction.commit();
                }
            }
        });

        signoutImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // before sending to the next fragment, check that if the user has before signed in or not
                if (isSignedIn)
                {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container,new SignOutFragment()).addToBackStack("signout");
                    fragmentTransaction.commit();
                }
            }
        });

        // show user the language dialog fragment
        languageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageDialogFragment languageDialogFragment = new LanguageDialogFragment();
                languageDialogFragment.show(getFragmentManager(), "setting language");
            }
        });

        aboutImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,new AboutFragment()).addToBackStack("about");
                fragmentTransaction.commit();
            }
        });



        return view;
    }

    private void retrieveProfileData() {
        User user = userSharedManager.getClient();
        if (user.getFirstName().equals(""))
        {
            apiServiceFlight.retrieveProfileData(new ApiServiceFlight.OnProfileDataRecieved() {
                @Override
                public void onRecieved(User user) {
                    if (user!=null)
                    nameTextview.setText(user.getFirstName() + " " + user.getLastName());
                }
            });
        }
    }

    // if the user has not signed in yet, pop up the signup activity
    private void checkingSignIn() {
        user = userSharedManager.getClient();

        if (userSharedManager.getToken().equals(""))
        {
            popUpSignInSignUpActivity();
            isSignedIn = false;
        }
        else
        {
            nameTextview.setText(user.getFirstName()+" "+ user.getLastName());
            isSignedIn = true;
        }
    }

    // setup signup activity
    private void popUpSignInSignUpActivity() {
        startActivity(new Intent(getActivity(), SignUpSignInActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_up,  R.anim.no_animation);
    }


    private void init() {
        rulesImageview = view.findViewById(R.id.iv_profile_rules);
        travellerCompanionImageview = view.findViewById(R.id.iv_profile_traveller_companion);
        editProfileImageview = view.findViewById(R.id.iv_profile_edit_profile);
        nameTextview = view.findViewById(R.id.tv_profile_name);
        signoutImageview = view.findViewById(R.id.iv_profile_signout);
        fragmentManager = getFragmentManager();
        apiServiceFlight = new ApiServiceFlight(getContext());
        userSharedManager = new UserSharedManagerFlight(getContext());
        languageImageView = view.findViewById(R.id.iv_profile_language);
        aboutImageview = view.findViewById(R.id.iv_profile_aboout);
        languageTextview = view.findViewById(R.id.tv_profile_language);
    }

    // get the user's language and then setup the language text view
    private void getLanguage() {
        if (userSharedManager.getLanguage().equals("fa"))
        {
            languageTextview.setText(getResources().getString(R.string.farsi_fa));
        }
        if (userSharedManager.getLanguage().equals("en"))
        {
            languageTextview.setText(getResources().getString(R.string.english_en));
        }
        if (userSharedManager.getLanguage().equals("ar"))
        {
            languageTextview.setText(getResources().getString(R.string.arabic_ar));
        }
    }


}
