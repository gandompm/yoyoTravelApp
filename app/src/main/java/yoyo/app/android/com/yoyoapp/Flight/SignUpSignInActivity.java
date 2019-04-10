package yoyo.app.android.com.yoyoapp.Flight;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.MyPagerAdapter;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.LanguageDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup;
import yoyo.app.android.com.yoyoapp.R;

public class SignUpSignInActivity extends AppCompatActivity {

    private MyPagerAdapter myPagerAdapter;
    private ImageView closeImageview;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private Button changeLanButton;
    private LanguageSetup languageSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signin_signup);
        init();

        setupSignInSignUpBottomSheet();
        setupCloseImageview();
        setupChangeLang();
        myPagerAdapter.notifyDataSetChanged();
    }

    private void init() {
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewPager_signinsignup);
        tabLayout = findViewById(R.id.tabs_signinsignup);
        closeImageview = findViewById(R.id.iv_signinsignup_close);
        fragmentManager = getSupportFragmentManager();
        changeLanButton = findViewById(R.id.button_signupsignin_language);
    }

    // setup view pager
    private void setupSignInSignUpBottomSheet() {
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
    }

    // view pager for navigating between sign up and sign in fragment
    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignUpFragment(),getResources().getString(R.string.sign_up));
        adapter.addFragment(new SignInFragment(), getResources().getString(R.string.sign_in));
        viewPager.setAdapter(adapter);
    }

    // close and finish activity
    private void setupCloseImageview() {
        closeImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.no_animation,  R.anim.slide_down);
            }
        });
    }

    // change language
    private void setupChangeLang() {

        changeLanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageDialogFragment languageDialogFragment = new LanguageDialogFragment();
                languageDialogFragment.show(getSupportFragmentManager(), "setting language");
            }
        });
    }




}
