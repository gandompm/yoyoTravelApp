package yoyo.app.android.com.yoyoapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import yoyo.app.android.com.yoyoapp.Addapters.OrdersRecyclerviewAddapter;
import yoyo.app.android.com.yoyoapp.BottomSheet.SignUpBottomSheet;
import yoyo.app.android.com.yoyoapp.DataModels.Client;
import yoyo.app.android.com.yoyoapp.DataModels.Order;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class OrdersPageFragment extends Fragment {
    private static final String TAG = "OrdersPageFragment";
    private static final int RC_SIGN_IN = 9001;
    private RecyclerView recyclerView;
    private Button googleButton;
    private GoogleAuthentication googleSignIn;
    private SignUpBottomSheet signUpBottomSheet;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders_page, container, false);

        init();
        signUpBottomSheet.hideBotttomNavigationView();
        setupRecyclerview();
        signUpBottomSheet.collapseBottomSheet();
        signUpBottomSheet.completeInformation();
        signUpBottomSheet.setupGoogleSignUp();
        onStartOrderActivity();

        return view;
    }

    private void setupRecyclerview() {

        OrdersRecyclerviewAddapter ordersRecyclerviewAddapter = new OrdersRecyclerviewAddapter(getContext(),Order.getFakeData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ordersRecyclerviewAddapter);
    }

    private void init() {
        recyclerView = view.findViewById(R.id.rv_orders);
        googleSignIn = new GoogleAuthentication(getContext());
        signUpBottomSheet = new SignUpBottomSheet(getContext(),view);
        googleButton = view.findViewById(R.id.button_signup_google);
    }

    public void onStartOrderActivity()
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
            signUpBottomSheet.handleSignInResult(data);
            startActivity(new Intent(getContext(),CompleteInfoActivity.class));
        }
    }

}
