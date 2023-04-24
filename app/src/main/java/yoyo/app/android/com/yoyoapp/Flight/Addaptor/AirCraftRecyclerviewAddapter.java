package yoyo.app.android.com.yoyoapp.Flight.Addaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.AirCraft;
import yoyo.app.android.com.yoyoapp.Flight.MainFlightActivity;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class AirCraftRecyclerviewAddapter extends RecyclerView.Adapter<AirCraftRecyclerviewAddapter.AircraftVeiwholder> {

    private ArrayList<AirCraft> aircrafts;
    private Context context;
    private OnItemAircraftSelected onItemAircraftSelected;

    public AirCraftRecyclerviewAddapter(ArrayList<AirCraft> aircrafts, Context context, OnItemAircraftSelected onItemAircraftSelected) {
        this.aircrafts = aircrafts;
        this.context = context;
        this.onItemAircraftSelected = onItemAircraftSelected;
    }

    @NonNull
    @Override
    public AircraftVeiwholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aircraft, parent, false);
        return new AircraftVeiwholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AircraftVeiwholder holder, final int position) {
        final AirCraft airCraft = aircrafts.get(position);
        holder.bindAircraftItem(airCraft);
        holder.emptyCircleImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                airCraft.setSelected(!airCraft.isSelected());
                onItemAircraftSelected.onSelectd(airCraft.getId(),position);
                if (airCraft.isSelected())
                    holder.greenCircleImageview.setVisibility(View.VISIBLE);
                else
                    holder.greenCircleImageview.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return aircrafts == null ? 0 : aircrafts.size();
    }

    public class AircraftVeiwholder extends RecyclerView.ViewHolder
    {
        private TextView airlineTextview;
        private ImageView greenCircleImageview;
        private ImageView emptyCircleImageview;

        public AircraftVeiwholder(@NonNull View itemView) {
            super(itemView);
            airlineTextview = itemView.findViewById(R.id.tv_aircraftitem);
            greenCircleImageview = itemView.findViewById(R.id.iv_aircraftitem_green_check);
            emptyCircleImageview = itemView.findViewById(R.id.iv_aircraftitem_circle);
        }

        public void bindAircraftItem(AirCraft airCraft)
        {
            airlineTextview.setText(airCraft.getManufacturer());
            if (((MainFlightActivity)context).idAircrafts.contains(String.valueOf(airCraft.getId())))
            {
                airCraft.setSelected(true);
                greenCircleImageview.setVisibility(View.VISIBLE);
            }
            else
                greenCircleImageview.setVisibility(View.GONE);

        }
    }
    // callback for when user click on one item of aircraft list
    public interface OnItemAircraftSelected
    {
        void onSelectd(int manufacturerId, int position);
    }
}
