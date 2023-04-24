package yoyo.app.android.com.yoyoapp.Flight.FlightSearch;


import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Consumer;
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight;
import yoyo.app.android.com.yoyoapp.Flight.DataModel.City;
import yoyo.app.android.com.yoyoapp.Flight.Utils.CheckInternetConnection;

import java.util.ArrayList;

public class FlightSearchPresenter {

    private Context context;
    private ApiServiceFlight apiServiceFlight;
    private ConstraintLayout constraintLayout;

    public FlightSearchPresenter(Context context, ConstraintLayout constraintLayout)
    {
        this.context = context;
        this.constraintLayout = constraintLayout;
        apiServiceFlight = new ApiServiceFlight(context);
    }

    public void getCitiesListFromServer(final Consumer<ArrayList<City>> onNewReslts){

        new CheckInternetConnection(context, constraintLayout, internetConnection -> {
            if (internetConnection)
            {
                apiServiceFlight.getCityList(cities -> {
                    if (cities != null)
                    {
                        onNewReslts.accept(cities);
                    }
                });
            }
        });
// generate fake data for cities list
//        ArrayList<SampleSearchModel> items = new ArrayList<>();
//        items.add(new SampleSearchModel("Tehran (THR)"));
//        items.add(new SampleSearchModel("Mashhad (MHD)"));
//        items.add(new SampleSearchModel("Najaf (NJF)"));
//        items.add(new SampleSearchModel("Kish (KIH)"));
//        items.add(new SampleSearchModel("Imam Khomeini (IKA)"));
    }


}
