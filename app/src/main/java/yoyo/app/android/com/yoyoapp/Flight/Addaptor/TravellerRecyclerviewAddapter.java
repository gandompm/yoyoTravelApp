package yoyo.app.android.com.yoyoapp.Flight.Addaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import yoyo.app.android.com.yoyoapp.Flight.DataModel.Traveller;
import yoyo.app.android.com.yoyoapp.R;

import java.util.ArrayList;

public class TravellerRecyclerviewAddapter extends RecyclerView.Adapter<TravellerRecyclerviewAddapter.TravellerViewholder> {

    private ArrayList<Traveller> travellers;
    private OnItemSelected onItemSelected;
    private Context context;

    public TravellerRecyclerviewAddapter(ArrayList<Traveller> travellers, Context context, OnItemSelected onItemSelected) {
        this.travellers = travellers;
        this.context = context;
        this.onItemSelected = onItemSelected;
    }


    @NonNull
    @Override
    public TravellerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_traveller,parent,false);
        return new TravellerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravellerViewholder holder, final int position) {
        holder.bindFlightItem(travellers.get(position), position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onItemSelected.onSendResult(travellers.get(position) , position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return travellers.size();
    }

    public class TravellerViewholder extends RecyclerView.ViewHolder
    {
        private TextView ageTextview;
        private TextView nameTextview;
        private TextView numTextview;
        private ImageView detailsButton;

        public TravellerViewholder(@NonNull View itemView) {
            super(itemView);
            ageTextview = itemView.findViewById(R.id.tv_traverller_item_age);
            nameTextview = itemView.findViewById(R.id.tv_traverller_item_name);
            numTextview = itemView.findViewById(R.id.tv_traverller_item_num);
            detailsButton = itemView.findViewById(R.id.button_traveller_item);
        }

        public void bindFlightItem(Traveller traveller, int position) {
            if (traveller.getFirstName()!= null || traveller.getLastName()!= null)
            {
                nameTextview.setText(traveller.getFirstName() + " " + traveller.getLastName());
            }
            ageTextview.setText(traveller.getAgeClass());
            numTextview.setText("Traveller "+ String.valueOf(position + 1));

        }
    }
    // callback for when user click on one item of traveller list
    public interface OnItemSelected
    {
        void onSendResult(Traveller traveller, int position);
    }
}
