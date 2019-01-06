package yoyo.app.android.com.yoyoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GoogleAuthentication {

    private static final String TAG = "GoogleAuthentication";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;
    private Context context;

    public GoogleAuthentication(Context context) {
        this.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PROFILE))
//                .requestScopes(new Scope("https://www.googleapis.com/auth/userinfo.profile"))
//                .requestServerAuthCode(String.valueOf(R.string.server_client_id),false)
                .requestEmail()
                .requestServerAuthCode(context.getString(R.string.server_client_id))
//                .requestIdToken(context.getString(R.string.server_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, gso);


    }

    public void getGoogleSignupIntent(OnSingnInIntentRecieved onSingnInIntentRecieved) {

        Intent signInIntent = googleSignInClient.getSignInIntent();
        onSingnInIntentRecieved.onIntentRecieved(signInIntent);

    }

    public interface OnSingnInIntentRecieved
    {
        void onIntentRecieved(Intent intent);
    }

    public void getGoogleSignInAcount(OnGoogleSignInAcountRecieved onGoogleSignInAcountRecieved){

        account = GoogleSignIn.getLastSignedInAccount(context);
        onGoogleSignInAcountRecieved.onAcountReceved(account);
    }

    public void handleSignInResult(Intent data ,OnGoogleSignInAcountRecieved onGoogleSignInAcountRecieved) {

        Task<GoogleSignInAccount> completeTask = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            account = completeTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            onGoogleSignInAcountRecieved.onAcountReceved(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            onGoogleSignInAcountRecieved.onAcountReceved(account);
        }
    }

    public interface OnGoogleSignInAcountRecieved
    {
        void onAcountReceved(GoogleSignInAccount googleSignInAccount);
    }

    public void googleSignOut()
    {
        googleSignInClient.signOut()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_framelayout,new MainPageFragment());
                        fragmentTransaction.commit();
                    }
                });
    }
}
