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
import yoyo.app.android.com.yoyoapp.Utils;

public class CitiesRecyclerviewAddaptor extends RecyclerView.Adapter<CitiesRecyclerviewAddaptor.CityViewholder> {

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
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_NAME(),cities.get(position).getName());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_IMAGE(),cities.get(position).getImage());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_ABOUT(),cities.get(position).getAbout());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_NATURAL_ATT(),cities.get(position).getNaturalAttraction());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_HISTORICAL_ATT(),cities.get(position).getHistoricalAttraction());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_MAN_MADE_ATT(),cities.get(position).getManMadeAttraction());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_FOOD_SOUVENIR(),cities.get(position).getFoodAndSouvenir());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_CLIMATE(),cities.get(position).getClimate());
                bundle.putString(Utils.INSTANCE.getKEY_BUNDLE_CITY_FOOD_TOPEXPERIENCE(),cities.get(position).getTopExperince());

                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CitiyViewPagerFragment citiyViewPagerFragment = new CitiyViewPagerFragment();
                citiyViewPagerFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.main_container, citiyViewPagerFragment).addToBackStack("cities");
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
