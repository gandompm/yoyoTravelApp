package yoyo.app.android.com.yoyoapp.trip.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.trip.api.TourDestinationsResponse


class TourSearchViewModel(application: Application) : AndroidViewModel(application) {
    private val tourSearchRepository = TourSearchRepository(application)

    val destinations = MutableLiveData<List<Location>>()
    val categories = MutableLiveData<List<Category>>()

    fun initCategories() {
        tourSearchRepository.requestCategories {
            val categories = it?.categories?.map {
                Category().apply {
                    name = it?.name
                    code = it?.code
                }
            }
            this.categories.value = categories
        }
    }

    fun initDestination() {
        requestLocalDestinations {
            destinations.value = it
        }
        tourSearchRepository.requestDestinations { it ->
            val locations = it?.locations?.map {
                Location(it?.code ?: "0", it?.name)
            }
            destinations.value = locations

            it?.let { saveDestinationsInLocal( it) }
        }
    }

    private fun saveDestinationsInLocal( tourDestinations: TourDestinationsResponse) {
        tourDestinations.locations?.let {
            for (location in it) {
                val newLocation = Location(
                     location?.code!!
                    ,name = location.name
                )
                viewModelScope.launch(Dispatchers.IO) {
                    tourSearchRepository.saveDestinationsInLocal(newLocation)
                }
            }
        }
    }

    private fun requestLocalDestinations( f: (List<Location>) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            f(
                async(Dispatchers.IO) {
                    tourSearchRepository.requestLocalDestinations()
                }.await())
        }
    }


}