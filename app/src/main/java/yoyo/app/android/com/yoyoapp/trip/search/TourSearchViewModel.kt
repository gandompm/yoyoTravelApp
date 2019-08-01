package yoyo.app.android.com.yoyoapp.trip.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.trip.roomDataBase.AppDatabase
import androidx.lifecycle.viewModelScope


class TourSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val tourSearchRepository = TourSearchRepository(application)

    val destinations = MutableLiveData<List<Location>>()
    val categories = MutableLiveData<List<Category>>()
    val localDatabase: AppDatabase = AppDatabase.getInstance(context = application)


    fun initCategories() {
        tourSearchRepository.requestCategories {
            val categories = it?.categories?.map {
                Category().apply {
                    name = it.name
                    code = it.code
                }
            }
            this.categories.value = categories
        }
    }

    fun initDestination() {
        tourSearchRepository.requestLocalDestinations(viewModelScope) {
            destinations.value = it
        }
        tourSearchRepository.requestDestinations { it ->
            val locations = it?.locations?.map {
                Location().apply {
                    code = it.code
                    name = it.name
                }
            }
            destinations.value = locations

            it?.let { tourSearchRepository.saveDestinationsInLocal(viewModelScope, it) }
        }
    }


}