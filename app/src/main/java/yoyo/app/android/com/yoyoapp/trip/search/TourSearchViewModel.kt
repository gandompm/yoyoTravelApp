package yoyo.app.android.com.yoyoapp.trip.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location

class TourSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val tourSearchRepository = TourSearchRepository(application)

    val destinations = MutableLiveData<List<Location>>()
    val categories = MutableLiveData<List<Category>>()

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
        tourSearchRepository.requestDestinations {
            val locations = it?.locations?.map {
                Location().apply {
                    code = it.code
                    title = it.name
                }
            }
            destinations.value = locations
        }
    }
}