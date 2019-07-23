package yoyo.app.android.com.yoyoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SharedDataViewModel : ViewModel() {
    val fromPrice = MutableLiveData<Int>()
    val toPrice = MutableLiveData<Int>()
    val categories = MutableLiveData<ArrayList<Category>>()
    var fromTime = MutableLiveData<Long>()
    var toTime = MutableLiveData<Long>()
    var fromTimeString = MutableLiveData<String>()
    var toTimeString = MutableLiveData<String>()
    var minDuration = MutableLiveData<Int>()
    var destination = MutableLiveData<Location>()
    var diffDays = MutableLiveData<Int>()
    var hasFiltersChanged = MutableLiveData<Boolean>()

    init {
        val calendar = Calendar.getInstance()
        fromTime.value = calendar.timeInMillis
        fromTimeString.value = getDayFormat(calendar)
        calendar.add(Calendar.DAY_OF_MONTH, 15)
        toTime.value = calendar.timeInMillis
        toTimeString.value = getDayFormat(calendar)
        categories.value = ArrayList()
        destination.value = Location()
        minDuration.value = 1
        diffDays.value = 15
        toPrice.value = 20000000
        fromPrice.value = 0
        hasFiltersChanged.value = false
    }


    fun selectFromPrice(fromPrice: Int) {
//        this.fromPrice.value = fromPrice
        this.fromPrice.postValue(fromPrice)
    }

    fun selectToPrice(toPrice: Int) {
//        this.toPrice.value = toPrice
        this.toPrice.postValue(toPrice)
    }

    fun selectCategories(categories: ArrayList<Category>){
        this.categories.postValue(categories)
    }

    fun resetFilters() {
        this.categories.value?.clear()
        toPrice.value = 20000000
        fromPrice.value = 0
        minDuration.value = 1
        hasFiltersChanged = MutableLiveData()
    }

    fun selectFromTime(fromTime: Long) {
        this.fromTime.value = fromTime
    }

    fun selectToTime(toTime: Long) {
        this.toTime.value = toTime
    }

    fun selectFromTimeString(fromTime: String) {
        this.fromTimeString.value = fromTime
    }

    fun selectToTimeString(toTime: String) {
        this.toTimeString.value = toTime
    }

    fun selectMinDuration(minDuration: Int) {
        this.minDuration.value = minDuration
    }

    fun selectDestination(destination: Location) {
        this.destination.value = destination
    }

    fun selectDiffDays(diffDays: Int) {
        this.diffDays.value = diffDays
    }

    fun hasFilterChanged(isChanged: Boolean) {
        this.hasFiltersChanged.value = isChanged
    }

    private fun getDayFormat(calendar: Calendar): String {
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())
        val dayOfWeekNameFrom = dayFormat.format(calendar.time)
        val monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

        return "$dayOfWeekNameFrom, ${calendar.get(Calendar.DAY_OF_MONTH)} $monthNameFrom"
    }



}