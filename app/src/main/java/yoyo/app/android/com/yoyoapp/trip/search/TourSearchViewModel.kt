package yoyo.app.android.com.yoyoapp.trip.search

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.trip.roomDataBase.AppDatabase
import java.lang.ref.WeakReference
import android.provider.ContactsContract.CommonDataKinds.Note



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
        tourSearchRepository.requestDestinations {
            val locations = it?.locations?.map {
                Location().apply {
                    code = it.code
                    title = it.name
                }
            }
            destinations.value = locations

            it?.let {
                var i = 1
                for (location in it.locations!!) {

                    InsertTask(this, yoyo.app.android.com.yoyoapp.trip.roomDataBase.Location(
                        i
                        , location.name
                        , location.code
                    )
                    ).execute()
                    i++
                }
            }

        }
        RetrieveTask(this).execute()
    }

    private class RetrieveTask(val tourSearchViewModel: TourSearchViewModel) : AsyncTask<Void, Void, ArrayList<Location>>() {
        val locationList: ArrayList<Location> = ArrayList()

        override fun doInBackground(vararg voids: Void): ArrayList<Location>? {
            if (tourSearchViewModel.localDatabase.userDao().getAll().isNotEmpty())
            {
                for (location in tourSearchViewModel.localDatabase.userDao().getAll()) {
                    val newLocation = Location()
                    newLocation.code = location.code
                    newLocation.title = location.title
                    locationList.add(newLocation)
                }
                return locationList
            }
            return null
        }
        override fun onPostExecute(result: ArrayList<Location>?) {
            if (!result.isNullOrEmpty()) {
                tourSearchViewModel.destinations.value = result
            }
        }
    }

    class InsertTask(private val tourSearchViewModel: TourSearchViewModel,
                     private val location: yoyo.app.android.com.yoyoapp.trip.roomDataBase.Location) : AsyncTask<Void, Void, Boolean>() {

        // doInBackground methods runs on a worker thread
        override fun doInBackground(vararg objs: Void): Boolean? {
            tourSearchViewModel.localDatabase.userDao().update(location)
            return true
        }
    }
}