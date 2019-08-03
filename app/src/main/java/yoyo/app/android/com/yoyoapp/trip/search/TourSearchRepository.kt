package yoyo.app.android.com.yoyoapp.trip.search

import android.content.Context
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.trip.ApiService2

import yoyo.app.android.com.yoyoapp.trip.api.TourCategoriesResponse
import yoyo.app.android.com.yoyoapp.trip.api.TourDestinationsResponse
import yoyo.app.android.com.yoyoapp.trip.roomDataBase.AppDatabase


class TourSearchRepository(context: Context) {
    private val apiService2 = ApiService2(context)
    val localDatabase: AppDatabase = AppDatabase.getInstance(context)


    fun requestCategories(f: (TourCategoriesResponse?) -> Unit) {
        apiService2.getCategoriesRequest(f)
    }

    fun requestDestinations(f: (TourDestinationsResponse?) -> Unit) {
        apiService2.getDestinationsRequest(f)
    }

    fun saveDestinationsInLocal(newLocation: Location) {
        localDatabase.userDao().delete(newLocation)
        localDatabase.userDao().insertAll(newLocation)
    }

    fun requestLocalDestinations(): List<Location> {
        return localDatabase.userDao().getAll()
    }


}