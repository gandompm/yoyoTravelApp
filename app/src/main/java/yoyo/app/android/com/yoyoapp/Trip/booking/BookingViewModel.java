package yoyo.app.android.com.yoyoapp.Trip.booking;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.DataModels.Traveller;

import java.util.List;

public class BookingViewModel extends AndroidViewModel
{
    private BookingRepository bookingRepository;
    private MutableLiveData<String> bookingIdMutableLiveData;
    private MutableLiveData<List<Traveller>> travellerMutableList;

    public BookingViewModel(@NonNull Application application) {
        super(application);
        bookingRepository = BookingRepository.getInstance(getApplication());
    }


    public void initBookingRequest(String scheduleId, JSONObject jsonObject) {
        bookingIdMutableLiveData = new MutableLiveData<>();
        bookingIdMutableLiveData = bookingRepository.getBookingId(scheduleId,jsonObject);
    }

    public LiveData<String> getBookingId() {
        return bookingIdMutableLiveData;
    }

    public void initGetTravellers() {
        travellerMutableList = new MutableLiveData<>();
        travellerMutableList = bookingRepository.getTravellers();
    }

    public LiveData<List<Traveller>> getTravellers() {
        return travellerMutableList;
    }


}
