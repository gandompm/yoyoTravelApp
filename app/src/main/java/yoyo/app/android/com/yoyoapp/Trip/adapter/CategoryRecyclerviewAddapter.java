package yoyo.app.android.com.yoyoapp.Trip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.DataModels.Schedule;
import yoyo.app.android.com.yoyoapp.R;
import yoyo.app.android.com.yoyoapp.Trip.TripActivity;

import java.util.ArrayList;

public class CategoryRecyclerviewAddapter extends RecyclerView.Adapter<CategoryRecyclerviewAddapter.AirlineViewholder> {

    private static final String TAG = "AirlineRecyclerviewAdda";
    private ArrayList<Category> categories;
    private ArrayList<String> selectedCategories;
    private Context context;
    private OnItemCategorySelected onItemCategorySelected;

    public CategoryRecyclerviewAddapter(ArrayList<Category> categories, Context context, OnItemCategorySelected onItemCategorySelected) {
        this.categories = categories;
        this.context = context;
        this.onItemCategorySelected = onItemCategorySelected;
        selectedCategories = new ArrayList<>();
        for (Category category :
                ((TripActivity)context).categories) {
            selectedCategories.add(category.getName());
        }
    }

    @NonNull
    @Override
    public AirlineViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new AirlineViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AirlineViewholder holder, final int position) {
        final Category category = categories.get(position);
        holder.bindAirlineItem(category);
        holder.itemView.setOnClickListener(v -> {
            category.setSelected(!category.isSelected());
            onItemCategorySelected.onSelectd(category);
            if (category.isSelected())
            {
                holder.nameTextview.setTextColor(context.getResources().getColor(R.color.white));
                holder.nameTextview.setBackground(context.getResources().getDrawable(R.drawable.dark_round_border_bg));
            }
            else
            {
                holder.nameTextview.setTextColor(context.getResources().getColor(R.color.black1));
                holder.nameTextview.setBackground(context.getResources().getDrawable(R.drawable.blue_round_border_bg));
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }


    public class AirlineViewholder extends RecyclerView.ViewHolder
    {
        private TextView nameTextview;

        public AirlineViewholder(@NonNull View itemView) {
            super(itemView);
            nameTextview = itemView.findViewById(R.id.tv_categoryitem);
        }

        public void bindAirlineItem(Category category)
        {
            nameTextview.setText(category.getName());
            if (selectedCategories.contains(category.getName()))
            {
                category.setSelected(true);
                nameTextview.setTextColor(context.getResources().getColor(R.color.white));
                nameTextview.setBackground(context.getResources().getDrawable(R.drawable.dark_round_border_bg));
            }
            else
            {
                nameTextview.setTextColor(context.getResources().getColor(R.color.black1));
                nameTextview.setBackground(context.getResources().getDrawable(R.drawable.blue_round_border_bg));
            }
        }
    }

    // callback for when user click on one item of airline list
    public interface OnItemCategorySelected
    {
        void onSelectd(Category category);
    }


}
