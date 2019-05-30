package yoyo.app.android.com.yoyoapp.Trip.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.dagang.library.GradientButton;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.details.TripDetailsFragment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FoldingCellRecyclerviewAdapter extends RecyclerView.Adapter<FoldingCellRecyclerviewAdapter.TripViewholder> {

    private static final String TAG = "FoldingCellRecyclerview";
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private Context context;
    private ArrayList<Trip> tripArrayList = new ArrayList<>();

    public FoldingCellRecyclerviewAdapter(Context context) {
        this.context = context;
    }

    public void addTrips(List<Trip> trips) {
        tripArrayList.addAll(trips);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_trip,parent,false);
        return new TripViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewholder holder, int position) {
        FoldingCell cell = (FoldingCell) holder.itemView;
        holder.bindTrip(tripArrayList.get(position));
        holder.contentRequestBtn.getButton().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("tripId", tripArrayList.get(position).getTripId());
            TripDetailsFragment tripDetailsFragment = new TripDetailsFragment();
            tripDetailsFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, tripDetailsFragment,"tripdetails");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        holder.itemView.setOnClickListener(v->
        {
            ((FoldingCell) holder.itemView).toggle(false);
            registerToggle(position);
        });

        // for existing cell set valid valid state(without animation)
        if (unfoldedIndexes.contains(position)) {
            cell.unfold(true);
        } else {
            cell.fold(true);
        }

    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
        Log.d(TAG, "onBindViewHolder: " + unfoldedIndexes);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    @Override
    public int getItemCount() {
        return tripArrayList.size();
    }

    public class TripViewholder extends RecyclerView.ViewHolder{

        private TextView price;
        private GradientButton contentRequestBtn;
        private TextView language;
        private TextView startDate;
        private TextView endDate;
        private TextView from;
        private TextView to;
        private TextView type;
        private TextView operator;
        private TextView capacity;
        private TextView tripLeaderName;
        private TextView duraion;
        private ImageView tripLeaderImg;
        private TextView fromBig;
        private TextView toBig;
        private TextView moveTime;
        private TextView endTime;
        private TextView tripGroup;
        private TextView tripTitle;
        private ImageView tripImg;
        private TextView priceBig;

        public TripViewholder(@NonNull View itemView) {
            super(itemView);
            tripTitle = itemView.findViewById(R.id.tv_trip_item_small_trip_title);
            tripGroup = itemView.findViewById(R.id.tv_trip_item_small_trip_group);
            startDate = itemView.findViewById(R.id.tv_trip_item_small_start_date);
            endDate = itemView.findViewById(R.id.tv_trip_item_small_end_date);
            from = itemView.findViewById(R.id.tv_trip_item_small_from);
            to = itemView.findViewById(R.id.tv_trip_item_small_to);
            price = itemView.findViewById(R.id.tv_trip_item_small_price);
            operator = itemView.findViewById(R.id.tv_trip_item_small_operator);
            type = itemView.findViewById(R.id.tv_trip_item_small_type);
            language = itemView.findViewById(R.id.tv_trip_item_small_language);
            contentRequestBtn = itemView.findViewById(R.id.content_request_btn);
            capacity = itemView.findViewById(R.id.tv_trip_item_small_capacity);
            duraion = itemView.findViewById(R.id.tv_trip_item_small_duration);
            tripLeaderName = itemView.findViewById(R.id.tv_trip_item_big_trip_leader_name);
            tripLeaderImg = itemView.findViewById(R.id.iv_trip_item_big_trip_leader_img);
            fromBig = itemView.findViewById(R.id.tv_trip_item_big_start_point_address);
            toBig = itemView.findViewById(R.id.tv_trip_item_big_end_point_address);
            moveTime = itemView.findViewById(R.id.tv_trip_item_big_start_day_and_time);
            endTime = itemView.findViewById(R.id.tv_trip_item_big_end_day_and_time);
            tripImg = itemView.findViewById(R.id.iv_trip_item_big_trip_img);
            priceBig = itemView.findViewById(R.id.tv_trip_item_big_price);
        }

        public void bindTrip(Trip trip) {
            // bind data from selected element to view through view holder
            price.setText(trip.getPrice());
            operator.setText("Homa Sa'adat");
            startDate.setText(trip.getStartDate());
            endDate.setText(trip.getEndDate());
            from.setText(trip.getStartPoint());
            to.setText(trip.getEndPoint());
            type.setText(trip.getCategory());
            language.setText(trip.getLanguage());
            capacity.setText(String.valueOf(trip.getRemainingCapacity()));
//            tripLeaderName.setText(trip.getTripLeaderName());
            duraion.setText(String.valueOf(trip.getDayNum()));
//          Picasso.with(context).load(trip.getTripLeaderImg()).into(tripLeaderImg);
            fromBig.setText(trip.getStartPoint());
            toBig.setText(trip.getEndPoint());
            moveTime.setText(trip.getStartTime());
            endTime.setText(trip.getEndTime());
            tripGroup.setText("Kavir Mesr");
            tripTitle.setText(trip.getTitle());
            Picasso.with(context).load(trip.getImage()).into(tripImg);
            priceBig.setText(trip.getPrice());
        }
    }
}
