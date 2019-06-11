package yoyo.app.android.com.yoyoapp.Trip.search;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.Trip.ApiService;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.DataModels.Location;

import java.util.List;

public class TripSearchRepository {

    private Context context;
    private static TripSearchRepository instance;
    private MutableLiveData<List<Category>> categoriesMutableLiveData;
    private MutableLiveData<List<Location>> originsMutableLiveData;
    private MutableLiveData<List<Location>> destinationsMutableLiveData;
    private ApiService apiService;

    public TripSearchRepository(Context context) {
        this.context = context;
        apiService = new ApiService(context);
    }

    public static TripSearchRepository getInstance(Context context)
    {
        if (instance == null) {
            return new TripSearchRepository(context);
        }
        else
            return instance;
    }

    public MutableLiveData<List<Location>> getOrigins() {
        originsMutableLiveData = new MutableLiveData<>();
        setOrigins();
        return originsMutableLiveData;
    }

    private void setOrigins() {
        apiService.getOriginsRequest(locations -> {
            originsMutableLiveData.postValue(locations);
        });
    }

    public MutableLiveData<List<Category>> getCategories() {
        categoriesMutableLiveData = new MutableLiveData<>();
        setCategories();
        return categoriesMutableLiveData;
    }

    private void setCategories() {
        apiService.getCategoryRequest(categories ->{
            categoriesMutableLiveData.setValue(categories);
        });
    }

    public MutableLiveData<List<Location>> getDestination() {
        destinationsMutableLiveData = new MutableLiveData<>();
        setDestination();
        return destinationsMutableLiveData;
    }

    private void setDestination() {
        apiService.getDestinationsRequest(locations -> {
            destinationsMutableLiveData.postValue(locations);
        });
    }
}
