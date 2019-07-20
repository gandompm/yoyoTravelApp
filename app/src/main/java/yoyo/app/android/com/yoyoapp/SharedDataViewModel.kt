package yoyo.app.android.com.yoyoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import yoyo.app.android.com.yoyoapp.DataModels.Category

class SharedDataViewModel : ViewModel() {
    val fromPrice = MutableLiveData<Int>()
    val toPrice = MutableLiveData<Int>()
    var categories: ArrayList<Category> = ArrayList()


    fun selectFromPrice(fromPrice: Int) {
        this.fromPrice.value = fromPrice
    }

    fun selectToPrice(toPrice: Int) {
        this.toPrice.value = toPrice
    }
}