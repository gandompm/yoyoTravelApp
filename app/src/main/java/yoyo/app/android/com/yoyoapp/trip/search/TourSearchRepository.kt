package yoyo.app.android.com.yoyoapp.trip.search

import android.content.Context
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.TourCategories
import yoyo.app.android.com.yoyoapp.trip.api.TourDestinations

class TourSearchRepository(context: Context) {
    private val apiService2 = ApiService2(context)

    fun requestCategories(f: (TourCategories?) -> Unit) {
        apiService2.sendCategoriesRequest(f)
    }

    fun requestDestinations(f: (TourDestinations?) -> Unit) {
        apiService2.sendDestinationsRequest(f)
    }
}