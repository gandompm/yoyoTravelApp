package yoyo.app.android.com.yoyoapp.BottomSheet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import yoyo.app.android.com.yoyoapp.ApiService;
import yoyo.app.android.com.yoyoapp.CompleteInfoActivity;
import yoyo.app.android.com.yoyoapp.GoogleAuthentication;
import yoyo.app.android.com.yoyoapp.MainActivity;
import yoyo.app.android.com.yoyoapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.shaishavgandhi.loginbuttons.TwitterButton;

public class SignUpBottomSheet {

    private static final String TAG = "SignUpBottomSheet";
    private LinearLayout linearLayout;
    private GoogleAuthentication googleSignIn;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView closeImageview;
    private TwitterButton twitterButton;
    private Context context;
    private Button changeLanButton;
    public static String idToken;
    private View view;

    public SignUpBottomSheet(Context context,View view) {
        this.context = context;
        this.view = view;
        init();
        setupChangeLang();
    }

    private void setupChangeLang() {

        changeLanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] listItems = {"english" ,"العربية"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("choose language...");
                builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ((MainActivity)context).setLocale("en");

                        } else if (which == 1) {
                            ((MainActivity)context).setLocale("ar");
                        }
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    //
    //Smile:)
    //

    private void init()
    {
        closeImageview = view.findViewById(R.id.iv_sign_up_close);
        twitterButton = view.findViewById(R.id.button_signup_twitter);
        googleSignIn = new GoogleAuthentication(context);
        linearLayout = view.findViewById(R.id.bottom_sheet_sign_up);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        changeLanButton = view.findViewById(R.id.button_signup_language);
    }

    public void handleSignInResult(Intent data) {
        googleSignIn.handleSignInResult(data, new GoogleAuthentication.OnGoogleSignInAcountRecieved() {
            @Override
            public void onAcountReceved(GoogleSignInAccount account) {
                if (account!= null)
                {
                    updateUI(account);
                }
                else
                {
                    updateUI(null);
                }
            }
        });
    }

    public void updateUI(GoogleSignInAccount account) {

        if (account == null)
        {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            ((MainActivity)context).bottomNavigation.setVisibility(View.GONE);
        }
        else
        {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                idToken = acct.getIdToken();



                Log.d(TAG, "\n\n\n\n\n\npersonName "+ personName + "\n");
                Log.d(TAG, "personGivenName "+ personGivenName+ "\n");
                Log.d(TAG, "personFamilyName "+ personFamilyName+ "\n");
                Log.d(TAG, "personEmail "+ personEmail+ "\n");
                Log.d(TAG, "personId "+ personId+ "\n");
                Log.d(TAG, "acout "+ acct.getAccount().toString()+ "\n");
                Log.d(TAG, "auth "+ acct.getServerAuthCode()+ "\n");
                Log.d(TAG, "idtoken "+ acct.getIdToken()+ "\n\n\n\n\n");

                ApiService apiService = new ApiService(context);
                apiService.sendTestRequest(acct.getServerAuthCode());

            }
        }
    }

    public void setupGoogleSignUp() {

//        googleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                googleSignIn.getGoogleSignupIntent(new GoogleAuthentication.OnSingnInIntentRecieved() {
//                    @Override
//                    public void onIntentRecieved(Intent intent) {
//
//                        ordersPageFragment.onStartOrderActivity(intent);
//                    }
//                });
//            }
//        });
    }

    public void collapseBottomSheet() {

        closeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                ((MainActivity)context).bottomNavigation.setVisibility(View.VISIBLE);

            }
        });
    }

    public void completeInformation() {
        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,CompleteInfoActivity.class));
                ((MainActivity)context).bottomNavigation.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hideBotttomNavigationView() {
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setPeekHeight(0);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

                if (slideOffset < 0.5)
                {
                    ((MainActivity)context).bottomNavigation.setVisibility(View.VISIBLE);

                }
                else if (slideOffset > 0.5)
                {
                    ((MainActivity)context).bottomNavigation.setVisibility(View.GONE);
                }
            }
        });
    }
}
