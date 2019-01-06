package yoyo.app.android.com.yoyoapp.BannerSlider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class BasicPagerAdapter extends PagerAdapter {

    private List<String> photoUrls;
    private LayoutInflater layoutInflater;
    private Context context;

    public BasicPagerAdapter(Context context) {
        photoUrls = getBannerList2();
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View view = layoutInflater.inflate(R.layout.viewpager_item, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.banner_viewpager_item);
        TextView tv = (TextView) view.findViewById(R.id.tv_viewpager_item_title);
        tv.setText(title[position]);
        String imageUrl = photoUrls.get(position);
        Picasso.with(context).load(imageUrl).into(iv);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return photoUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }


    private List<String> getBannerList2() {
        List<String> coverList = new ArrayList();
        coverList.add("https://images.unsplash.com/photo-1533982023263-64b4091468ab?ixlib=rb-0.3.5&s=6547ef183c5ce54a71852ab5fe67839b&auto=format&fit=crop&w=750&q=80");
        coverList.add("https://images.unsplash.com/photo-1527126887308-6cdf83c7d844?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=dcf69f6921fc2710608c72d005952d82&auto=format&fit=crop&w=750&q=80");
        coverList.add("https://images.unsplash.com/photo-1512827162677-21e6fa46c057?ixlib=rb-0.3.5&s=e1accc199c611b2c50e09e5296ea837e&auto=format&fit=crop&w=750&q=80");
        coverList.add("https://images.unsplash.com/photo-1530311583484-ea8bf4c407fb?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=5471ccdab61f9634e1d9dba21bb50a34&auto=format&fit=crop&w=750&q=80");
        coverList.add("https://images.unsplash.com/photo-1524693359256-077720b70144?ixlib=rb-0.3.5&s=4615783092cf93aab243ff82e425024e&auto=format&fit=crop&w=401&q=80");
        coverList.add("https://images.unsplash.com/photo-1519122114654-d665e49b122e?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=bebf32ae6f33ce87d5c8b5404443d789&auto=format&fit=crop&w=282&q=80");
        coverList.add("https://images.unsplash.com/photo-1527575594294-781925f28455?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=5a035df778d14c05b4d5c71c001fb6da&auto=format&fit=crop&w=750&q=80");

        return coverList;
    }

    private String title[] = new String[]{"Azadi Tower", "Shiraz", "Tehrans Street", "Kashan", "Yazd", "Esfahan", "Abyaneh Historical Village"};
}
