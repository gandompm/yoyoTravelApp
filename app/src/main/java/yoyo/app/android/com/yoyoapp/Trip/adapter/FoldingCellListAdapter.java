package yoyo.app.android.com.yoyoapp.Trip.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.dagang.library.GradientButton;
import yoyo.app.android.com.yoyoapp.DataModels.Trip;
import yoyo.app.android.com.yoyoapp.R;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;
import yoyo.app.android.com.yoyoapp.Trip.details.TripDetailsFragment;


import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */

public class FoldingCellListAdapter extends ArrayAdapter<Trip> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private Context context;

    public FoldingCellListAdapter(Context context, List<Trip> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get trip for selected view
        Trip trip = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.item_trip, parent, false);
            // binding view parts to view holder
            viewHolder.price = cell.findViewById(R.id.title_price);
            viewHolder.previousPrice = cell.findViewById(R.id.tv_trip_item_small_price);
            viewHolder.nightNum = cell.findViewById(R.id.tv_trip_item_small_operator_title);
            viewHolder.dayNum = cell.findViewById(R.id.tv_trip_item_small_operator);
            viewHolder.startDate = cell.findViewById(R.id.tv_trip_item_small_start_date);
            viewHolder.endDate = cell.findViewById(R.id.tv_trip_item_small_end_date);
            viewHolder.destination = cell.findViewById(R.id.tv_trip_item_small_type);
            viewHolder.language = cell.findViewById(R.id.tv_trip_item_small_language);
            viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);
            viewHolder.capacity = cell.findViewById(R.id.tv_trip_item_small_capacity);
            viewHolder.tripGroup = cell.findViewById(R.id.tv_trip_item_small_trip_group);
            viewHolder.tripTitle = cell.findViewById(R.id.tv_trip_item_small_trip_title);
            viewHolder.tripLeaderName = cell.findViewById(R.id.tv_trip_item_big_trip_leader_name);
            viewHolder.tripLeaderImg = cell.findViewById(R.id.iv_trip_item_big_trip_leader_img);
            viewHolder.startPoint = cell.findViewById(R.id.tv_trip_item_big_start_point_address);
            viewHolder.endPoint = cell.findViewById(R.id.tv_trip_item_big_end_point_address);
            viewHolder.moveTime = cell.findViewById(R.id.tv_trip_item_big_start_day_and_time);
            viewHolder.endTime = cell.findViewById(R.id.tv_trip_item_big_end_day_and_time);
            viewHolder.tripImg = cell.findViewById(R.id.iv_trip_item_big_trip_img);
            viewHolder.priceBig = cell.findViewById(R.id.tv_trip_item_big_price);



                    cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == trip)
            return cell;

        // bind data from selected element to view through view holder
        viewHolder.price.setText(trip.getPrice());
        viewHolder.previousPrice.setPaintFlags(viewHolder.previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.previousPrice.setText(trip.getPreviousPrice());
        viewHolder.nightNum.setText(String.valueOf(trip.getDayNum())+ " Day");
        viewHolder.dayNum.setText(String.valueOf(trip.getNightNum())+ " Night");
        viewHolder.startDate.setText(trip.getStartDate());
        viewHolder.endDate.setText(trip.getEndDate());
        viewHolder.destination.setText(trip.getDestination());
        viewHolder.language.setText(trip.getLanguage());
        viewHolder.capacity.setText(String.valueOf(trip.getRemainingCapacity()));
//        viewHolder.tripLeaderName.setText(trip.getTripLeaderName());
//        Picasso.with(context).load(trip.getTripLeaderImg()).into(viewHolder.tripLeaderImg);
        viewHolder.startPoint.setText(trip.getStartPoint());
        viewHolder.endPoint.setText(trip.getEndPoint());
        viewHolder.moveTime.setText(trip.getStartTime());
        viewHolder.endTime.setText(trip.getEndTime());
        viewHolder.tripGroup.setText(trip.getCategory());
        viewHolder.tripTitle.setText(trip.getTitle());
        Picasso.with(context).load(trip.getImage()).into(viewHolder.tripImg);
        viewHolder.priceBig.setText(trip.getPrice());
        viewHolder.contentRequestBtn.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tripId", trip.getTripId());
                TripDetailsFragment tripDetailsFragment = new TripDetailsFragment();
                tripDetailsFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.container, tripDetailsFragment,"tripdetails");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        // set custom btn handler for list trip from that trip
        if (trip.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(trip.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in trip
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
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
        private TextView tripLeaderName;
        private ImageView tripLeaderImg;
        private TextView startPoint;
        private TextView endPoint;
        private TextView moveTime;
        private TextView endTime;
        private TextView tripGroup;
        private TextView tripTitle;
        private ImageView tripImg;
        private TextView priceBig;
    }
}