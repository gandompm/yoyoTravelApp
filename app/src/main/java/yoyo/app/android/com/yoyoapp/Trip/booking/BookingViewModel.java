package yoyo.app.android.com.yoyoapp.Trip.booking;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;

public class BookingViewModel extends AndroidViewModel
{
    private BookingRepository bookingRepository;
    private MutableLiveData<String> bookingIdMutableLiveData;

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
}
