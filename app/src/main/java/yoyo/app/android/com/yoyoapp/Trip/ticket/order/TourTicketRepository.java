package yoyo.app.android.com.yoyoapp.Trip.ticket.order;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.DataModels.TourRequest;
import yoyo.app.android.com.yoyoapp.DataModels.TourTicket;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;

import java.util.ArrayList;

public class TourTicketRepository {

    private ApiService apiService;
    private static TourTicketRepository instance;
    private MutableLiveData<ArrayList<TourTicket>> tourTicketsMutableLiveData;
    private MutableLiveData<ArrayList<TourRequest>> tourRequestsMutableLiveData;
    private Context context;

    public static TourTicketRepository getInstance(Context context)
    {
        if (instance == null) {
            instance = new TourTicketRepository(context);
        }
        return instance;
    }

    private TourTicketRepository(Context context)
    {
        this.context = context;
        apiService = new ApiService(context);
    }


    public MutableLiveData<ArrayList<TourTicket>> getTickets() {
        tourTicketsMutableLiveData = new MutableLiveData<>();
        setTours();
        return tourTicketsMutableLiveData;
    }

    private void setTours() {
        apiService.getTourTicketRequest(tourTickets -> tourTicketsMutableLiveData.postValue(tourTickets));
    }

    public MutableLiveData<ArrayList<TourRequest>> getTourRequests() {
        tourRequestsMutableLiveData = new MutableLiveData<>();
        setTourRequest();
        return tourRequestsMutableLiveData;
    }

    private void setTourRequest() {
        apiService.getTourRequestRequest(tourRequests -> {
            tourRequestsMutableLiveData.postValue(tourRequests);
        });
    }
}
