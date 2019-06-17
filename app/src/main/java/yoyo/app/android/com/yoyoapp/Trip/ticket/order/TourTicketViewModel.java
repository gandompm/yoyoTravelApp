package yoyo.app.android.com.yoyoapp.Trip.ticket.order;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.DataModels.TourRequest;
import yoyo.app.android.com.yoyoapp.DataModels.TourTicket;

import java.util.ArrayList;
import java.util.List;


public class TourTicketViewModel extends AndroidViewModel {

    private TourTicketRepository tourTicketRepository;
    private MutableLiveData<ArrayList<TourTicket>> tourTicketsMutableLiveData;
    private MutableLiveData<ArrayList<TourRequest>> tourRequestsMutableLiveData;


    public TourTicketViewModel(@NonNull Application application) {
        super(application);
        tourTicketRepository = TourTicketRepository.getInstance(getApplication());
    }


    public void initTours() {
        tourTicketsMutableLiveData = new MutableLiveData<>();
        tourTicketsMutableLiveData = tourTicketRepository.getTickets();
    }

    public LiveData<ArrayList<TourTicket>> getTourTickets() {
        return tourTicketsMutableLiveData;
    }

    public void initTourRequests() {
        tourRequestsMutableLiveData = new MutableLiveData<>();
        tourRequestsMutableLiveData = tourTicketRepository.getTourRequests();
    }


    public LiveData<ArrayList<TourRequest>> getTourRequests() {
        return tourRequestsMutableLiveData;
    }
}
