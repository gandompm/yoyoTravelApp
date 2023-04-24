package yoyo.app.android.com.yoyoapp.Addapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.DataModels.Room;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RoomsRecyclerviewAddaptor extends RecyclerView.Adapter<RoomsRecyclerviewAddaptor.RoomsViewPager> {

    private Context context;
    private ArrayList<Room> rooms;
    private OnPlusClickedListner onPlusClickedListner;

    public RoomsRecyclerviewAddaptor(Context context, ArrayList<Room> rooms, OnPlusClickedListner onPlusClickedListner)
    {
        this.context = context;
        this.rooms = rooms;
        this.onPlusClickedListner = onPlusClickedListner;
    }

    @NonNull
    @Override
    public RoomsViewPager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_room,parent,false);
        return new RoomsViewPager(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoomsViewPager holder, final int position) {
        holder.bindOffer(rooms.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.increaseNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rooms.get(position).getNum() < 9)
                {
                    holder.roomNum.setText(String.valueOf(rooms.get(position).increaseNum()));
                    onPlusClickedListner.onPlusClicked(true);
                }
            }
        });

        holder.decreaseNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rooms.get(position).getNum() > 0)
                {
                    holder.roomNum.setText(String.valueOf(rooms.get(position).decreaseNum()));
                    onPlusClickedListner.onPlusClicked(false);
                }
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
        private TextView roomNum;
        private ImageView decreaseNum;
        private ImageView increaseNum;
        private int roomBookedNum;

        public RoomsViewPager(View itemView) {
            super(itemView);
            roomType = itemView.findViewById(R.id.tv_hotel_item_name);
            roomNum = itemView.findViewById(R.id.tv_roomitem_num);
            decreaseNum = itemView.findViewById(R.id.iv_roomitem_minus);
            increaseNum = itemView.findViewById(R.id.iv_roomitem_plus);
        }

        public void bindOffer(Room item)
        {
            roomType.setText(item.getType());
        }
    }

    public interface OnPlusClickedListner
    {
        void onPlusClicked(boolean plusClicked);
    }
}
