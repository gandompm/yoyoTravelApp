package yoyo.app.android.com.yoyoapp.Addapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.TravellerCompanionsEditFragment;

import java.util.ArrayList;

public class TravellerCompanionRecyclerviewAddaptor extends RecyclerView.Adapter<TravellerCompanionRecyclerviewAddaptor.TravellerViewholder> {

    private ArrayList<Traveller> travellers;
    private Context context;
    private Boolean fromMyTravellerDialogFragment;
    private OnItemSelected onItemSelected;

    public TravellerCompanionRecyclerviewAddaptor(boolean fromMyTravellerDialogFragment, ArrayList<Traveller> travellers, Context context) {
        this.travellers = travellers;
        this.context = context;
        this.fromMyTravellerDialogFragment = fromMyTravellerDialogFragment;
    }

    public TravellerCompanionRecyclerviewAddaptor(boolean fromMyTravellerDialogFragment, ArrayList<Traveller> travellers, Context context, OnItemSelected onItemSelected) {
        this.travellers = travellers;
        this.context = context;
        this.fromMyTravellerDialogFragment = fromMyTravellerDialogFragment;
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
                Bundle bundle = new Bundle();
                Traveller traveller = travellers.get(position);
                bundle.putString("firstName",traveller.getFirstName());
                bundle.putString("lastName",traveller.getLastName());
                bundle.putString("email",traveller.getEmail());
                bundle.putString("gender",traveller.getGender());
                bundle.putString("country",traveller.getCountry());
                bundle.putString("dateOfBirth",traveller.getDateOfBirth());
                bundle.putString("datePassport",traveller.getExpiryDateOfPassport());
                bundle.putString("iranianCode",traveller.getIranianNationalCode());
                bundle.putString("passport",traveller.getPassportNumber());
                bundle.putString("ageClass",traveller.getAgeClass());
                bundle.putString("phone",traveller.getPhoneNumber());
                bundle.putInt("id",traveller.getTravellerId());

                if (!fromMyTravellerDialogFragment)
                {
                    TravellerCompanionsEditFragment detailsFragment = new TravellerCompanionsEditFragment();
                    detailsFragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_framelayout,detailsFragment).addToBackStack("traveller companion edit");
                    fragmentTransaction.commit();
                }
                else
                {
                    onItemSelected.onSendResult(traveller);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return travellers.size();
    }

    public class TravellerViewholder extends RecyclerView.ViewHolder
    {
        private TextView emailTextview;
        private TextView nameTextview;
        private TextView abbreviationTextview;

        public TravellerViewholder(@NonNull View itemView) {
            super(itemView);
            emailTextview = itemView.findViewById(R.id.tv_traverller_companion_email);
            nameTextview = itemView.findViewById(R.id.tv_traverller_companion_name);
            abbreviationTextview = itemView.findViewById(R.id.tv_traveller_companion_name_abbreviation);
        }

        public void bindTravellerCompanionItem(Traveller traveller) {

            nameTextview.setText(traveller.getFirstName() + " " + traveller.getLastName());
            emailTextview.setText(traveller.getEmail());
            abbreviationTextview.setText("PG");
        }
    }
    public interface OnItemSelected
    {
        void onSendResult(Traveller traveller);
    }

}
