package yoyo.app.android.com.yoyoapp.trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class TravellerCompanionRecyclerviewAddapter extends RecyclerView.Adapter<TravellerCompanionRecyclerviewAddapter.TravellerViewholder> {
    private ArrayList<Traveller> travellers;
    private Context context;
    private OnItemSelected onItemSelected;

    public TravellerCompanionRecyclerviewAddapter(ArrayList<Traveller> travellers, Context context, OnItemSelected onItemSelected) {
        this.travellers = travellers;
        this.context = context;
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public TravellerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_traveller_companion,parent,false);
        return new TravellerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravellerViewholder holder, final int position) {
        holder.bindTravellerCompanionItem(travellers.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Traveller traveller = travellers.get(position);

                onItemSelected.onSendResult(traveller);
            }
        });
    }

    @Override
    public int getItemCount() {
        return travellers.size();
    }

    public class TravellerViewholder extends RecyclerView.ViewHolder
    {
        private TextView nameTextview;
        private TextView abbrevationTextview;

        public TravellerViewholder(@NonNull View itemView) {
            super(itemView);
            nameTextview = itemView.findViewById(R.id.tv_traverller_companion_name);
            abbrevationTextview = itemView.findViewById(R.id.tv_traveller_companion_name_abbreviation);
        }

        public void bindTravellerCompanionItem(Traveller traveller) {

            abbrevationTextview.setText(traveller.getFirstName().substring(0,1).toUpperCase() + traveller.getLastName().substring(0,1).toUpperCase());
            nameTextview.setText(traveller.getFirstName() + " " + traveller.getLastName());
        }
    }

    // callback for when user click on one item of traveller list
    public interface OnItemSelected
    {
        void onSendResult(Traveller traveller);
    }
}
