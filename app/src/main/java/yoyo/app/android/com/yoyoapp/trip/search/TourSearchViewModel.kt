package yoyo.app.android.com.yoyoapp.trip.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location

class TourSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val tourSearchRepository = TourSearchRepository(application)

    val origins = MutableLiveData<List<Location>>()
    val destinations = MutableLiveData<List<Location>>()
    val categories = MutableLiveData<List<Category>>()

    fun initOrigin() {
        tourSearchRepository.requestOrigins { origins.value = it }
    }

    fun initCategories() {
        tourSearchRepository.requestCategories { categories.value = it }
    }

    fun initDestination() {
        tourSearchRepository.requestDestinations { destinations.value = it }
    }
}