package yoyo.app.android.com.yoyoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.DataModels.Traveller
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SharedDataViewModel : ViewModel() {
    val fromPrice = MutableLiveData<Int>()
    val toPrice = MutableLiveData<Int>()
    val selectedCategories = MutableLiveData<ArrayList<Category>>()
    var fromTime = MutableLiveData<Long>()
    var toTime = MutableLiveData<Long>()
    var fromTimeString = MutableLiveData<String>()
    var toTimeString = MutableLiveData<String>()
    var minDuration = MutableLiveData<Int>()
    var destination = MutableLiveData<Location>()
    var diffDays = MutableLiveData<Int>()
    var travellersList = MutableLiveData<ArrayList<Traveller>>()

    init {
        val calendar = Calendar.getInstance()
        fromTime.value = calendar.timeInMillis / 1000
        fromTimeString.value = getDayFormat(calendar)
        calendar.add(Calendar.DAY_OF_MONTH, 15)
        toTime.value = calendar.timeInMillis / 1000
        toTimeString.value = getDayFormat(calendar)
        selectedCategories.value = ArrayList()
        destination.value = Location()
        minDuration.value = 1
        diffDays.value = 15
        toPrice.value = 20000000
        fromPrice.value = 0
        travellersList.value = ArrayList()
    }

    fun selectFromPrice(fromPrice: Int) {
        this.fromPrice.value = fromPrice
    }

    fun selectToPrice(toPrice: Int) {
        this.toPrice.value = toPrice
    }

    fun selectCategories(categories: ArrayList<Category>){
        this.selectedCategories.postValue(categories)
    }

    fun resetFilters() {
        this.selectedCategories.value?.clear()
        toPrice.value = 20000000
        fromPrice.value = 0
        minDuration.value = 1
    }

    fun selectFromTime(fromTime: Long) {
        this.fromTime.value = fromTime
    }

    fun selectToTime(toTime: Long) {
        this.toTime.value = toTime
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

    fun setTraveller(index: Int, traveller: Traveller) {
        this.travellersList.value?.set(index,traveller)
        this.travellersList.value = this.travellersList.value
    }

    fun addTraveller(traveller: Traveller) {
        this.travellersList.value?.add(traveller)
    }

    fun getTraveller(index: Int)
            = this.travellersList.value?.get(index)

    private fun getDayFormat(calendar: Calendar): String {
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())
        val dayOfWeekNameFrom = dayFormat.format(calendar.time)
        val monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

        return "$dayOfWeekNameFrom, ${calendar.get(Calendar.DAY_OF_MONTH)} $monthNameFrom"
    }

    fun resetBookingTravellers() {
        this.travellersList.value?.clear()
    }
}