package yoyo.app.android.com.yoyoapp.Flight;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import yoyo.app.android.com.yoyoapp.Flight.Addaptor.MyPagerAdapter;
import yoyo.app.android.com.yoyoapp.Flight.Dialog.LanguageDialogFragment;
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup;
import yoyo.app.android.com.yoyoapp.R;

public class SignUpSignInActivity extends AppCompatActivity {

    private static final String TAG = "SignUpSignInActivity";
    private MyPagerAdapter adapter;
    private ImageView closeImageview;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private TextView signupMessage1;
    private TextView signupMessage2;
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
        adapter.notifyDataSetChanged();
        changeTheTitle();
    }

    private void changeTheTitle() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected:qqq onPageSelected");
                if (position == 1)
                {
                    signupMessage1.setText(getResources().getString(R.string.sign_in));
                    signupMessage2.setText(getString(R.string.welcome_back));
                }
                if (position == 0)
                {
                    signupMessage1.setText(getResources().getString(R.string.register_with_us_and));
                    signupMessage2.setText(getResources().getString(R.string.get_exclusive_deals_and_offers));
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void init() {
        mViewPager = findViewById(R.id.viewPager_signinsignup);
        tabLayout = findViewById(R.id.tabs_signinsignup);
        closeImageview = findViewById(R.id.iv_signinsignup_close);
        fragmentManager = getSupportFragmentManager();
        changeLanButton = findViewById(R.id.button_signupsignin_language);
        signupMessage1 = findViewById(R.id.signup_title_1);
        signupMessage2 = findViewById(R.id.signup_title_2);
    }

    // setup view pager
    private void setupSignInSignUpBottomSheet() {
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
    }

    // view pager for navigating between sign up and sign in fragment
    private void setupViewPager(ViewPager viewPager) {
        adapter = new MyPagerAdapter(getSupportFragmentManager());
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
