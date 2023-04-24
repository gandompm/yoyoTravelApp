package yoyo.app.android.com.yoyoapp.MaterialViewPager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import yoyo.app.android.com.yoyoapp.Addapters.HotelsRecyclerviewAddaptor;
import yoyo.app.android.com.yoyoapp.DataModels.Hotel;
import yoyo.app.android.com.yoyoapp.R;

public class HotelListActivity extends AppCompatActivity {

    private static final String TAG = "HotelListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hotel_list);

        HotelsRecyclerviewAddaptor hottelsRecyclerviewAddaptor = new HotelsRecyclerviewAddaptor(HotelListActivity.this,Hotel.fakeHotelData());
        Log.d(TAG, "onCreate: " + Hotel.fakeHotelData());
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.rv_hotellist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HotelListActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hottelsRecyclerviewAddaptor);

    }
}