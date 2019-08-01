package yoyo.app.android.com.yoyoapp.trip.search

import android.content.Context
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.TourCategories
import yoyo.app.android.com.yoyoapp.trip.api.TourDestinations
import kotlinx.coroutines.*
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.trip.roomDataBase.AppDatabase


class TourSearchRepository(context: Context) {
    private val apiService2 = ApiService2(context)
    val localDatabase: AppDatabase = AppDatabase.getInstance(context)


    fun requestCategories(f: (TourCategories?) -> Unit) {
        apiService2.getCategoriesRequest(f)
    }

    fun requestDestinations(f: (TourDestinations?) -> Unit) {
        apiService2.getDestinationsRequest(f)
    }

    fun requestLocalDestinations(scope: CoroutineScope,f: (List<Location>) -> Unit) {
        scope.launch(Dispatchers.Main) {
            f(
            async(Dispatchers.IO) {
                localDatabase.userDao().getAll()
            }.await())
        }
    }

    fun saveDestinationsInLocal(scope: CoroutineScope, tourDestinations: TourDestinations) {
        tourDestinations.locations?.let {
            var i = 1
            for (location in it) {
                val newLocation = Location(
                    i
                    , location.code
                    , location.name
                )
                scope.launch(Dispatchers.Main) {
                    async(Dispatchers.IO) {
                        localDatabase.userDao().delete(newLocation)
                        localDatabase.userDao().insertAll(newLocation)
                    }
                }
                i++
            }
        }
    }
}