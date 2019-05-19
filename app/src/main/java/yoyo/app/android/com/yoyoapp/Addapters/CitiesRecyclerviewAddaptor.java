package yoyo.app.android.com.yoyoapp.Addapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.DataModels.City;
import yoyo.app.android.com.yoyoapp.MaterialViewPager.CitiyViewPagerFragment;
import yoyo.app.android.com.yoyoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesRecyclerviewAddaptor extends RecyclerView.Adapter<CitiesRecyclerviewAddaptor.CityViewholder> {

    public static final String KEY_BUNDLE_CITY_IMAGE = "the image of city";
    public static final String KEY_BUNDLE_CITY_NAME = "the name of city";
    private ArrayList<City> cities;
    private RecyclerViewClickListener itemListener;
    private Context context;

    public CitiesRecyclerviewAddaptor(ArrayList<City> cities, Context context ,RecyclerViewClickListener itemListener) {
        this.cities = cities;
        this.itemListener = itemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CityViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_city,parent,false);
        return new CitiesRecyclerviewAddaptor.CityViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewholder holder, final int position) {
        holder.bindCity(cities.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(KEY_BUNDLE_CITY_NAME,cities.get(position).getName());
                bundle.putString(KEY_BUNDLE_CITY_IMAGE,cities.get(position).getImage());

                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CitiyViewPagerFragment citiyViewPagerFragment = new CitiyViewPagerFragment();
                citiyViewPagerFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, citiyViewPagerFragment).addToBackStack("cities");
                fragmentTransaction.commit();

                itemListener.recyclerViewListClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class CityViewholder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private ImageView imageView;
        public CityViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_item_city_name);
            imageView = itemView.findViewById(R.id.iv_item_city);
        }
        public void bindCity(City city)
        {
            name.setText(city.getName());
            Picasso.with(context).load(city.getImage()).into(imageView);
        }
    }

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked();
    }

}
