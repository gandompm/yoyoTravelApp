package yoyo.app.android.com.yoyoapp.Trip.search;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import yoyo.app.android.com.yoyoapp.DataModels.Category;
import yoyo.app.android.com.yoyoapp.DataModels.Location;

import java.util.List;

public class TripSearchViewModel extends AndroidViewModel {

    private MutableLiveData<List<Location>> originsMutableLiveData;
    private MutableLiveData<List<Location>> destinationsMutableLiveData;
    private MutableLiveData<List<Category>> categoriesMutableLiveData;
    private TripSearchRepository tripSearchRepository;

    public TripSearchViewModel(@NonNull Application application) {
        super(application);
        tripSearchRepository = TripSearchRepository.getInstance(getApplication());
    }

    public void initOrigin() {
        originsMutableLiveData = new MutableLiveData<>();
        originsMutableLiveData = tripSearchRepository.getOrigins();
    }

    public LiveData<List<Location>> getOrigins() {
        return originsMutableLiveData;
    }

    public void initCategoryList() {
        categoriesMutableLiveData = new MutableLiveData<>();
        categoriesMutableLiveData = tripSearchRepository.getCategories();
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoriesMutableLiveData;
    }

    public void initDestination() {
        destinationsMutableLiveData = new MutableLiveData<>();
        destinationsMutableLiveData = tripSearchRepository.getDestination();
    }

    public LiveData<List<Location>> getDestinations() {
        return destinationsMutableLiveData;
    }
}
