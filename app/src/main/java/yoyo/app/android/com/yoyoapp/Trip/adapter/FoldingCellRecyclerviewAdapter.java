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
import yoyo.app.android.com.yoyoapp.Flight.Utils.ItemAnimation;
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

    public void clearTrips() {
        tripArrayList.clear();
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
            Trip trip = tripArrayList.get(position);
            bundle.putString("tripId", trip.getTripId());
            bundle.putString("tourName", trip.getTour().getName());
            bundle.putInt("passengersCount", trip.getTour().getPassengerCount());
            bundle.putString("agencyName",trip.getAgency());
            bundle.putString("leaderName",trip.getTripLeader().getName());
            bundle.putString("leaderPicture",trip.getTripLeader().getPicture());
            bundle.putString("language", trip.getLanguage());
            bundle.putString("title",trip.getTitle());
            bundle.putStringArrayList("itinerary",trip.getItineraries());
            bundle.putStringArrayList("attractions",trip.getAttractions());
            bundle.putStringArrayList("transportation",trip.getTransportations());
            bundle.putStringArrayList("meals",trip.getMeals());
            bundle.putStringArrayList("rules",trip.getRules());
            bundle.putStringArrayList("categories",trip.getCategories());
            bundle.putStringArrayList("gallery",trip.getGallery());
            bundle.putInt("days",trip.getDayNum());
            bundle.putInt("nights",trip.getNightNum());
            bundle.putString("locationTitleFrom", trip.getLocations().get(0).getTitle());
            bundle.putString("locationTitleTo", trip.getLocations().get(1).getTitle());
            bundle.putDouble("fromLat",trip.getLocations().get(0).getLat());
            bundle.putDouble("fromLong",trip.getLocations().get(0).getLon());
            bundle.putDouble("toLat",trip.getLocations().get(1).getLat());
            bundle.putDouble("toLong",trip.getLocations().get(1).getLon());
            bundle.putString("summary",trip.getSummary());
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
            // fixing with and height must be > 0
            if(cell.getParent() != null)
            cell.unfold(true);
        } else {
            if(cell.getParent() != null)
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
        private TextView typeBig;
        private TextView titleBig;
        private TextView transport;
        private TextView tripGroup;
        private TextView tripTitle;
        private TextView durationBig;
        private ImageView tripImg;
        private TextView groupBig;
        private TextView priceBig;
        private TextView passengerCount;

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
            durationBig = itemView.findViewById(R.id.tv_trip_item_big_duration);
            tripLeaderName = itemView.findViewById(R.id.tv_trip_item_big_trip_leader_name);
            tripLeaderImg = itemView.findViewById(R.id.iv_trip_item_big_trip_leader_img);
            fromBig = itemView.findViewById(R.id.tv_trip_item_big_start_point);
            toBig = itemView.findViewById(R.id.tv_trip_item_big_end_point);
            typeBig = itemView.findViewById(R.id.tv_trip_item_big_type);
            transport = itemView.findViewById(R.id.tv_trip_item_big_transport);
            tripImg = itemView.findViewById(R.id.iv_trip_item_big_trip_img);
            priceBig = itemView.findViewById(R.id.tv_trip_item_big_price);
            titleBig = itemView.findViewById(R.id.tv_trip_item_big_group_name);
//            groupBig = itemView.findViewById(R.id.button_trip_item_big_trip_name);
            passengerCount = itemView.findViewById(R.id.tv_trip_item_big_passenger_count);
        }

        public void bindTrip(Trip trip) {
            // bind data from selected element to view through view holder
            operator.setText(trip.getAgency());
            tripTitle.setText(trip.getTitle());
            tripGroup.setText(trip.getTour().getName());
            Picasso.with(context).load(trip.getGallery().get(0)).into(tripImg);
            from.setText(trip.getLocations().get(0).getTitle());
            to.setText(trip.getLocations().get(1).getTitle());
            language.setText(trip.getTripLeader().getLanguage());
            duraion.setText(String.valueOf(trip.getDayNum()) + " Days");
            durationBig.setText(trip.getDayNum() + " Days");
            tripLeaderName.setText(trip.getTripLeader().getName());
            Picasso.with(context).load(trip.getTripLeader().getPicture()).into(tripLeaderImg);
            type.setText(trip.getCategories().get(0));
            fromBig.setText(trip.getLocations().get(0).getTitle());
            toBig.setText(trip.getLocations().get(1).getTitle());
            passengerCount.setText(trip.getTour().getPassengerCount()+ " people have purchased this tour");
            // transport
            String transports ="";
            for (String transport : trip.getTransportations()) {
                transports = transport + " ";
            }
            transport.setText(transports);
            // type
            String types ="";
            for (String type : trip.getCategories()) {
                types = type + " ";
            }
            typeBig.setText(types);
            titleBig.setText(trip.getTitle());
//            groupBig.setText(trip.getTour().getName());

            priceBig.setText("20$");
            price.setText("20$");
            startDate.setText("");
            endDate.setText("");
//            capacity.setText("");
        }
    }
}
