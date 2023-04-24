package yoyo.app.android.com.yoyoapp.Addapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.DataModels.Hotel;
import yoyo.app.android.com.yoyoapp.Hotel.HotelDetailsFragment;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Parham on 11/22/2018.
 */

public class HotelsRecyclerviewAddaptor extends RecyclerView.Adapter<HotelsRecyclerviewAddaptor.HotelsViewholder> {

    private Context context;
    private ArrayList<Hotel> hotels;

    public HotelsRecyclerviewAddaptor(Context context, ArrayList<Hotel> hotels)
    {
        this.context = context;
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public HotelsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_hotel,parent,false);
        return new HotelsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelsViewholder holder, int position) {
        holder.bindOffer(hotels.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(KEY_BUNDLE_CITY_NAME,cities.get(position).getType());
//                bundle.putString(KEY_BUNDLE_CITY_IMAGE,cities.get(position).getImage());

                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HotelDetailsFragment hotelDetailsFragment = new HotelDetailsFragment();
//                citiyViewPagerFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.main_container, hotelDetailsFragment).addToBackStack("hoteldetails");
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class HotelsViewholder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView address;
//        private TextView type;
        private TextView oldPrice;
        private TextView newPrice;
        private RatingBar stars;

        public HotelsViewholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_hotel_item_name);
            address = itemView.findViewById(R.id.tv_hotel_item_address);
//            type = itemView.findViewById(R.id.tv_hotel_item_type);
            oldPrice = itemView.findViewById(R.id.tv_hotel_item_old_price);
            newPrice = itemView.findViewById(R.id.tv_hotel_item_new_price);
            stars = itemView.findViewById(R.id.ratingBar_hotel_item);
        }

        public void bindOffer(Hotel item)
        {
            name.setText(item.getHotelName());
            address.setText(item.getHotelAddress());
//          type.setText(item.getHotelType());
            oldPrice.setText(item.getHotelOldPrice());
            newPrice.setText(item.getHotelNewPrice());
            stars.setRating((item.getHotelStar()));
            oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}
