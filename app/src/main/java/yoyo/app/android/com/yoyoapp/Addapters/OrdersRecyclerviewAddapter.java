package yoyo.app.android.com.yoyoapp.Addapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import yoyo.app.android.com.yoyoapp.DataModels.Hotel;
import yoyo.app.android.com.yoyoapp.DataModels.Order;
import yoyo.app.android.com.yoyoapp.OrdersPageFragment;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersRecyclerviewAddapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HOTEL = 1;
    public static final int TOUR = 2;
    public static final int FLIGHT = 3;
    private Context context;
    private ArrayList<Order> orders;

    public OrdersRecyclerviewAddapter(Context context,ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == HOTEL)
        {
            View view = layoutInflater.inflate(R.layout.item_order_hotel,parent,false);
            return new HotelOrderViewholder(view);
        } else if (viewType == TOUR) {
            View view = layoutInflater.inflate(R.layout.item_order_tour,parent,false);
            return new TourOrderViewholder(view);
        }
        else
        {
            View view = layoutInflater.inflate(R.layout.item_order_tour,parent,false);
            return new TourOrderViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOTEL)
        {
            ((HotelOrderViewholder)holder).bindOrder(orders.get(position));
        }
        else if (getItemViewType(position) == TOUR)
        {
            ((TourOrderViewholder)holder).bindOrder(orders.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrdersPageFragment ordersPageFragment = new OrdersPageFragment();
                }
            });
        }
        else
        {
            ((TourOrderViewholder)holder).bindOrder(orders.get(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (orders.get(position).isHotelOrder())
        {
            return HOTEL;
        }
        else if (orders.get(position).isFlightOrder())
        {
            return FLIGHT;
        }
        else
        {
            return TOUR;
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class HotelOrderViewholder extends RecyclerView.ViewHolder
    {
        private TextView titleTextview;
        private ImageView stateImageview;
        private TextView totalPrice;
        private TextView orderNumber;
        private TextView personSum;
        private TextView roomSum;

        public HotelOrderViewholder(@NonNull View itemView) {
            super(itemView);
            stateImageview = itemView.findViewById(R.id.iv_item_order_state);
            titleTextview = itemView.findViewById(R.id.tv_item_order_title);
            totalPrice = itemView.findViewById(R.id.tv_total_price);
            orderNumber = itemView.findViewById(R.id.tv_order_number);
            personSum = itemView.findViewById(R.id.tv_order_person_sum);
            roomSum = itemView.findViewById(R.id.tv_order_room_sum);
        }

        public void bindOrder(Order order) {
            titleTextview.setText(order.getName());
            totalPrice.setText(order.getTotalPrice());
            orderNumber.setText(order.getOrderNumber());
            personSum.setText(order.getPersonSum());
            roomSum.setText(order.getRoomSum());
        }
    }

    public class TourOrderViewholder extends RecyclerView.ViewHolder
    {
        private TextView titleTextview;
        private ImageView stateImageview;
        private TextView totalPrice;
        private TextView orderNumber;
        private TextView personSum;

        public TourOrderViewholder(@NonNull View itemView) {
            super(itemView);
            stateImageview = itemView.findViewById(R.id.iv_item_order_state);
            titleTextview = itemView.findViewById(R.id.tv_item_order_title);
            totalPrice = itemView.findViewById(R.id.tv_total_price);
            orderNumber = itemView.findViewById(R.id.tv_order_number);
            personSum = itemView.findViewById(R.id.tv_order_person_sum);

        }
        public void bindOrder(Order order)
        {
            titleTextview.setText(order.getName());
            totalPrice.setText(order.getTotalPrice());
            orderNumber.setText(order.getOrderNumber());
            personSum.setText(order.getPersonSum());
        }
    }



}
