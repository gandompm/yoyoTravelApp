package yoyo.app.android.com.yoyoapp.trip.search

import android.content.Context
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.TourCategories
import yoyo.app.android.com.yoyoapp.trip.api.TourDestinations
import android.os.AsyncTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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

    fun requestLocalDestinations(f: (List<Location>) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            f(
            async(Dispatchers.IO) {
                localDatabase.userDao().getAll()
            }.await())
        }
    }

    fun saveDestinationsInLocal(tourDestinations: TourDestinations) {
        tourDestinations.locations?.let {
            var i = 1
            for (location in it) {
                val newLocation = Location(
                    i
                    , location.code
                    , location.name
                )
                GlobalScope.launch(Dispatchers.Main) {
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