package yoyo.app.android.com.yoyoapp.Trip.booking;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import org.json.JSONObject;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;

public class BookingRepository {
    private ApiService apiService;
    private static BookingRepository instance;
    private MutableLiveData<String> bookingIdMutableLiveData;
    private Context context;

    public static BookingRepository getInstance(Context context)
    {
        if (instance == null) {
            instance = new BookingRepository(context);
        }
        return instance;
    }

    private BookingRepository(Context context)
    {
        this.context = context;
        apiService = new ApiService(context);
    }

    public MutableLiveData<String> getBookingId(String scheduleId, JSONObject jsonObject) {
        bookingIdMutableLiveData = new MutableLiveData<>();
        setBooking(scheduleId,jsonObject);
        return bookingIdMutableLiveData;
    }

    private void setBooking(String scheduleId, JSONObject jsonObject) {
        apiService.sendBookingRequest(scheduleId,jsonObject,bookingId ->{
            bookingIdMutableLiveData.postValue(bookingId);
        });
    }
}
