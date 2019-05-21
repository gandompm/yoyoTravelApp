package yoyo.app.android.com.yoyoapp.Trip.adapter;

import android.content.Context;
import android.graphics.Paint;
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

public class FoldingCellRecyclerviewAdapter extends RecyclerView.Adapter<FoldingCellRecyclerviewAdapter.TripViewholder> {

    private static final String TAG = "FoldingCellRecyclerview";
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private Context context;
    private ArrayList<Trip> tripArrayList;

    public FoldingCellRecyclerviewAdapter(Context context, ArrayList<Trip> tripArrayList) {
        this.context = context;
        this.tripArrayList = tripArrayList;
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
            TripDetailsFragment tirpDetailsFragment = new TripDetailsFragment();
            tirpDetailsFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, tirpDetailsFragment,"tirpdetails");
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
        private TextView previousPrice;
        private GradientButton contentRequestBtn;
        private TextView language;
        private TextView startDate;
        private TextView endDate;
        private TextView destination;
        private TextView dayNum;
        private TextView nightNum;
        private TextView capacity;
        private TextView tirpLeaderName;
        private ImageView tirpLeaderImg;
        private TextView startPoint;
        private TextView endPoint;
        private TextView moveTime;
        private TextView endTime;
        private TextView tirpGroup;
        private TextView tirpTitle;
        private ImageView tirpImg;
        private TextView priceBig;

        public TripViewholder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.title_price);
            previousPrice = itemView.findViewById(R.id.title_previous_price);
            nightNum = itemView.findViewById(R.id.tv_tirp_item_small_night_num);
            dayNum = itemView.findViewById(R.id.tv_tirp_item_small_day_num);
            startDate = itemView.findViewById(R.id.tv_tirp_item_small_start_date);
            endDate = itemView.findViewById(R.id.tv_tirp_item_small_end_date);
            destination = itemView.findViewById(R.id.tv_tirp_item_destination);
            language = itemView.findViewById(R.id.tv_tirp_item_small_language);
            contentRequestBtn = itemView.findViewById(R.id.content_request_btn);
            capacity = itemView.findViewById(R.id.tv_tirp_item_small_capacity);
            tirpGroup = itemView.findViewById(R.id.tv_tirp_item_small_tirp_group);
            tirpTitle = itemView.findViewById(R.id.tv_tirp_item_small_tirp_title);
            tirpLeaderName = itemView.findViewById(R.id.tv_tirp_item_big_tirp_leader_name);
            tirpLeaderImg = itemView.findViewById(R.id.iv_tirp_item_big_tirp_leader_img);
            startPoint = itemView.findViewById(R.id.tv_tirp_item_big_start_point_address);
            endPoint = itemView.findViewById(R.id.tv_tirp_item_big_end_point_address);
            moveTime = itemView.findViewById(R.id.tv_tirp_item_big_start_day_and_time);
            endTime = itemView.findViewById(R.id.tv_tirp_item_big_end_day_and_time);
            tirpImg = itemView.findViewById(R.id.iv_tirp_item_big_tirp_img);
            priceBig = itemView.findViewById(R.id.tv_tirp_item_big_price);
        }

        public void bindTrip(Trip trip) {
            // bind data from selected element to view through view holder
            price.setText(trip.getPrice());
            previousPrice.setPaintFlags(previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            previousPrice.setText(trip.getPreviousPrice());
            nightNum.setText(String.valueOf(trip.getDayNum())+ " Day");
            dayNum.setText(String.valueOf(trip.getNightNum())+ " Night");
            startDate.setText(trip.getStartDate());
            endDate.setText(trip.getEndDate());
            destination.setText(trip.getDestination());
            language.setText(trip.getLanguage());
            capacity.setText(String.valueOf(trip.getRemainingCapacity()));
            tirpLeaderName.setText(trip.getTripLeaderName());
//        Picasso.with(context).load(trip.getTripLeaderImg()).into(tirpLeaderImg);
            startPoint.setText(trip.getStartPoint());
            endPoint.setText(trip.getEndPoint());
            moveTime.setText(trip.getStartTime());
            endTime.setText(trip.getEndTime());
            tirpGroup.setText(trip.getCategory());
            tirpTitle.setText(trip.getTitle());
            Picasso.with(context).load(trip.getImage()).into(tirpImg);
            priceBig.setText(trip.getPrice());
        }
    }
}
