package yoyo.app.android.com.yoyoapp.trip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import java.text.SimpleDateFormat
import java.util.*

class TripViewModel(application: Application) : AndroidViewModel(application) {

    var fromPrice = 0
    var toPrice = 20000000
    var fromTime: Long = 0
    var toTime:Long = 0
    var fromTimeString: String
    var toTimeString:String
    var minDuration = 1
    var categories: ArrayList<Category>
    var origin: Location
    var destination: Location
    var diffDays = 7


    init {
        categories = ArrayList()
        origin = Location()
        destination = Location()
        val calendar = Calendar.getInstance()
        fromTime = calendar.timeInMillis
        fromTimeString = getDayFormat(calendar)
        calendar.add(Calendar.DAY_OF_MONTH, 15)
        toTime = calendar.timeInMillis
        toTimeString = getDayFormat(calendar)
    }

    private fun getDayFormat(calendar: Calendar): String {
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())
        val dayOfWeekNameFrom = dayFormat.format(calendar.time)
        val monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

        return "$dayOfWeekNameFrom, ${calendar.get(Calendar.DAY_OF_MONTH)} $monthNameFrom"
    }
}