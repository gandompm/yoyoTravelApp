package yoyo.app.android.com.yoyoapp.Addapters;

import android.content.Context;
import androidx.annotation.NonNull;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dagang.library.GradientButton;
import yoyo.app.android.com.yoyoapp.DataModels.Tour;
import yoyo.app.android.com.yoyoapp.R;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;


import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */

public class FoldingCellListAdapter extends ArrayAdapter<Tour> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private Context context;

    public FoldingCellListAdapter(Context context, List<Tour> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get tour for selected view
        Tour tour = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.item_tour, parent, false);
            // binding view parts to view holder
            viewHolder.price = cell.findViewById(R.id.title_price);
            viewHolder.previousPrice = cell.findViewById(R.id.title_previous_price);
            viewHolder.nightNum = cell.findViewById(R.id.tv_tour_item_small_night_num);
            viewHolder.dayNum = cell.findViewById(R.id.tv_tour_item_small_day_num);
            viewHolder.stardDate = cell.findViewById(R.id.tv_tour_item_small_start_date);
            viewHolder.endDate = cell.findViewById(R.id.tv_tour_item_small_end_date);
            viewHolder.destination = cell.findViewById(R.id.tv_tour_item_destination);
            viewHolder.language = cell.findViewById(R.id.tv_tour_item_small_language);
            viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);
            viewHolder.capacity = cell.findViewById(R.id.tv_tour_item_small_capacity);
            viewHolder.tourGroup = cell.findViewById(R.id.tv_tour_item_small_tour_group);
            viewHolder.tourTitle = cell.findViewById(R.id.tv_tour_item_small_tour_title);
            viewHolder.tourLeaderName = cell.findViewById(R.id.tv_tour_item_big_tour_leader_name);
            viewHolder.tourLeaderImg = cell.findViewById(R.id.iv_tour_item_big_tour_leader_img);
            viewHolder.startPoint = cell.findViewById(R.id.tv_tour_item_big_start_point_address);
            viewHolder.endPoint = cell.findViewById(R.id.tv_tour_item_big_end_point_address);
            viewHolder.moveTime = cell.findViewById(R.id.tv_tour_item_big_start_day_and_time);
            viewHolder.endTime = cell.findViewById(R.id.tv_tour_item_big_end_day_and_time);
            viewHolder.tourImg = cell.findViewById(R.id.iv_tour_item_big_tour_img);
            viewHolder.priceBig = cell.findViewById(R.id.tv_tour_item_big_price);



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

        if (null == tour)
            return cell;

        // bind data from selected element to view through view holder
        viewHolder.price.setText(tour.getPrice());
        viewHolder.previousPrice.setPaintFlags(viewHolder.previousPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.previousPrice.setText(tour.getPreviousPrice());
        viewHolder.nightNum.setText(String.valueOf(tour.getDayNum())+ " Day");
        viewHolder.dayNum.setText(String.valueOf(tour.getNightNum())+ " Night");
        viewHolder.stardDate.setText(tour.getStartDate());
        viewHolder.endDate.setText(tour.getEndDate());
        viewHolder.destination.setText(tour.getDestination());
        viewHolder.language.setText(tour.getLanguage());
        viewHolder.capacity.setText(tour.getCapacity().toString());
        viewHolder.tourLeaderName.setText(tour.getTourLeaderName());
        Picasso.with(context).load(tour.getTourLeaderImg()).into(viewHolder.tourLeaderImg);
        viewHolder.startPoint.setText(tour.getStartPoint());
        viewHolder.endPoint.setText(tour.getEndPoint());
        viewHolder.moveTime.setText(tour.getMoveTime());
        viewHolder.endTime.setText(tour.getEndTime());
        viewHolder.tourGroup.setText(tour.getTourGroup());
        viewHolder.tourTitle.setText(tour.getTourTitle());
        Picasso.with(context).load(tour.getTourImg()).into(viewHolder.tourImg);
        viewHolder.priceBig.setText(tour.getPrice());

        // set custom btn handler for list tour from that tour
        if (tour.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(tour.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in tour
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
        private TextView stardDate;
        private TextView endDate;
        private TextView destination;
        private TextView dayNum;
        private TextView nightNum;
        private TextView capacity;
        private TextView tourLeaderName;
        private ImageView tourLeaderImg;
        private TextView startPoint;
        private TextView endPoint;
        private TextView moveTime;
        private TextView endTime;
        private TextView tourGroup;
        private TextView tourTitle;
        private ImageView tourImg;
        private TextView priceBig;
    }
}