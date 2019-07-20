package yoyo.app.android.com.yoyoapp.trip.search

import android.content.Context
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.trip.ApiService

class TourSearchRepository(context: Context) {
    private val apiService: ApiService = ApiService(context)

    fun requestOrigins(f: (ArrayList<Location>) -> Unit) {
        apiService.getOriginsRequest(f)
    }

    fun requestCategories(f: (ArrayList<Category>) -> Unit) {
        apiService.getCategoryRequest(f)
    }

    fun requestDestinations(f: (ArrayList<Location>) -> Unit) {
        apiService.getDestinationsRequest(f)
    }
}