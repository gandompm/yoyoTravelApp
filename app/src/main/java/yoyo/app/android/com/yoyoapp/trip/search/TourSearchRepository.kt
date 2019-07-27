package yoyo.app.android.com.yoyoapp.trip.search

import android.content.Context
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.TourCategories
import yoyo.app.android.com.yoyoapp.trip.api.TourDestinations
import android.provider.ContactsContract.CommonDataKinds.Note
import android.os.AsyncTask
import android.view.View
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.trip.roomDataBase.AppDatabase
import java.lang.ref.WeakReference


class TourSearchRepository(context: Context) {
    private val apiService2 = ApiService2(context)

    fun requestCategories(f: (TourCategories?) -> Unit) {
        apiService2.sendCategoriesRequest(f)
    }

    fun requestDestinations(f: (TourDestinations?) -> Unit) {
        apiService2.sendDestinationsRequest(f)
    }

}