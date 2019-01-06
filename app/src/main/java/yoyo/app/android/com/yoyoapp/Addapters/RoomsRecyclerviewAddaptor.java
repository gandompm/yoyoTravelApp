package yoyo.app.android.com.yoyoapp.Addapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.DataModels.Hotel;
import yoyo.app.android.com.yoyoapp.DataModels.Room;
import yoyo.app.android.com.yoyoapp.HotelDetailsFragment;
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

public class RoomsRecyclerviewAddaptor extends RecyclerView.Adapter<RoomsRecyclerviewAddaptor.RoomsViewPager> {

    private Context context;
    private ArrayList<Room> rooms;

    public RoomsRecyclerviewAddaptor(Context context, ArrayList<Room> rooms)
    {
        this.context = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public RoomsViewPager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_room,parent,false);
        return new RoomsViewPager(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsViewPager holder, int position) {
        holder.bindOffer(rooms.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class RoomsViewPager extends RecyclerView.ViewHolder
    {
        private TextView roomType;

        public RoomsViewPager(View itemView) {
            super(itemView);
            roomType = itemView.findViewById(R.id.tv_hotel_item_name);
        }

        public void bindOffer(Room item)
        {
            roomType.setText(item.getType());
        }
    }
}
