package yoyo.app.android.com.yoyoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedDataViewModel : ViewModel() {
    val fromPrice = MutableLiveData<Int>()
    val toPrice = MutableLiveData<Int>()

    fun selectFromPrice(fromPrice: Int) {
        this.fromPrice.value = fromPrice
    }

    fun selectToPrice(toPrice: Int) {
        this.toPrice.value = toPrice
    }
}