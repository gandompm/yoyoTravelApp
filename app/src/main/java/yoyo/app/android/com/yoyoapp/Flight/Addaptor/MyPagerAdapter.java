package yoyo.app.android.com.yoyoapp.Flight.Addaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MyPagerAdapter";
    private  List<Fragment> mFragmentList = new ArrayList<>();
    private  List<String> mFragmentTitleList = new ArrayList<>();

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }





    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
