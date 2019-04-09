package yoyo.app.android.com.yoyoapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import yoyo.app.android.com.yoyoapp.BottomSheet.SignUpBottomSheet;
import yoyo.app.android.com.yoyoapp.DataModels.Client;
import yoyo.app.android.com.yoyoapp.SearchDialog.SearchDialogFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private static final int RC_SIGN_IN = 9001;
    private ImageView editProfileCardImageview, signOutCardImageview, reportCardImageview , settingCardImageview ,travellerCompanionImageview;
    private SignUpBottomSheet signUpBottomSheet;
    private FragmentManager fragmentManager;
    private GoogleAuthentication googleSignIn;
    private Client client;
    private Button googleButton ;
    private TextView nameTextview;
    private CircleImageView clientImageview ;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        init();
        signUpBottomSheet.hideBotttomNavigationView();
        setupSharedPref();
        setupEditProfileButton();
        setupSignupButton();
        signUpBottomSheet.collapseBottomSheet();
        signUpBottomSheet.completeInformation();
        signUpBottomSheet.setupGoogleSignUp();
        setupReportButton();
        setupSettingButton();
        onStartProfileActivity();
        setupTravellerCompanionButton();

        return view;
    }

    private void setupTravellerCompanionButton() {
        travellerCompanionImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_framelayout,new TravellerCompanionFragment()).addToBackStack("traveller companion");
                fragmentTransaction.commit();
            }
        });
    }

    private void setupSettingButton() {
        settingCardImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_framelayout,new SearchDialogFragment())
                        .addToBackStack("setting");
                fragmentTransaction.commit();
            }
        });
    }

    private void setupSharedPref() {
        UserSharedManager userSharedManager = new UserSharedManager(getContext());
        client = userSharedManager.getClient();

        if (client!= null && client.getFirstName()!= "")
        {
            nameTextview.setText(client.getFirstName());
            Picasso.with(getContext()).load(client.getPicture()).into(clientImageview);
        }
    }

    private void setupReportButton() {
        reportCardImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_framelayout,new ReportFragment())
                        .addToBackStack("report");
                fragmentTransaction.commit();
            }
        });
    }

    private void setupSignupButton() {
        signOutCardImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_framelayout,new SignOutFragment())
                        .addToBackStack("signout");
                fragmentTransaction.commit();
            }
        });

    }

    private void setupEditProfileButton() {
        editProfileCardImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_framelayout,new EditProfileFragment())
                        .addToBackStack("edit");
                fragmentTransaction.commit();
            }
        });
    }

    private void init() {
        fragmentManager = getFragmentManager();
        editProfileCardImageview = view.findViewById(R.id.iv_profile_edit);
        signOutCardImageview = view.findViewById(R.id.iv_profile_sign_out);
        googleSignIn = new GoogleAuthentication(getContext());
        signUpBottomSheet = new SignUpBottomSheet(getContext(),view);
        googleButton = view.findViewById(R.id.button_signup_google);
        nameTextview = view.findViewById(R.id.tv_profile_name);
        clientImageview = view.findViewById(R.id.iv_profile);
        settingCardImageview = view.findViewById(R.id.iv_profile_setting);
        reportCardImageview = view.findViewById(R.id.iv_profile_report_problem);
        travellerCompanionImageview = view.findViewById(R.id.iv_profile_travel_companion);
    }

    public void onStartProfileActivity()
    {
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSignIn.getGoogleSignupIntent(new GoogleAuthentication.OnSingnInIntentRecieved() {
                    @Override
                    public void onIntentRecieved(Intent intent) {
                        startActivityForResult(intent, RC_SIGN_IN);

                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        googleSignIn.getGoogleSignInAcount(new GoogleAuthentication.OnGoogleSignInAcountRecieved() {
            @Override
            public void onAcountReceved(GoogleSignInAccount googleSignInAccount) {
                signUpBottomSheet.updateUI(googleSignInAccount);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            UserSharedManager userSharedManager = new UserSharedManager(getContext());
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            userSharedManager.saveDataToSharedPrefrence(acct);
//            signUpBottomSheet.handleSignInResult(data);
            startActivity(new Intent(getContext(),CompleteInfoActivity.class));

        }
    }



}
