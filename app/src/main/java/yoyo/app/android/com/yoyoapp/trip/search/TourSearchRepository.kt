package yoyo.app.android.com.yoyoapp.trip.search

import android.content.Context
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.TourCategories
import yoyo.app.android.com.yoyoapp.trip.api.TourDestinations
import android.os.AsyncTask
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.trip.roomDataBase.AppDatabase


class TourSearchRepository(context: Context) {
    private val apiService2 = ApiService2(context)
    val localDatabase: AppDatabase = AppDatabase.getInstance(context)


    fun requestCategories(f: (TourCategories?) -> Unit) {
        apiService2.sendCategoriesRequest(f)
    }

    fun requestDestinations(f: (TourDestinations?) -> Unit) {
        apiService2.sendDestinationsRequest(f)
    }

    fun requestLocalDestinations(f:(List<Location>) -> Unit) {
        RetrieveTask(this,f).execute()
    }

    fun saveDestinationsInLocal(tourDestinations: TourDestinations) {
        tourDestinations.locations?.let {
            var i = 1
            for (location in it) {
                InsertTask(
                    this, Location(
                        i
                        , location.code
                        , location.name
                    )
                ).execute()
                i++
            }
        }
    }

    private class RetrieveTask(
        val tourSearchRepository: TourSearchRepository,
        val f: (List<Location>) -> Unit
    ) : AsyncTask<Void, Void, List<Location>>() {

        override fun doInBackground(vararg voids: Void): List<Location>? {
            if (tourSearchRepository.localDatabase.userDao().getAll().isNotEmpty())
            {
                return tourSearchRepository.localDatabase.userDao().getAll()
            }
            return null
        }
        override fun onPostExecute(result: List<Location>?) {
            if (!result.isNullOrEmpty()) {
                f(result)
            }
        }
    }

    class InsertTask( val tourSearchRepository: TourSearchRepository,
                     private val location: Location) : AsyncTask<Void, Void, Boolean>() {

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): Boolean? {
            tourSearchRepository.localDatabase.userDao().delete(location)
            tourSearchRepository.localDatabase.userDao().insertAll(location)
            return true
        }
    }


}