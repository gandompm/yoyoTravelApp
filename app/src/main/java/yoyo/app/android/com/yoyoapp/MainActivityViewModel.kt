package yoyo.app.android.com.yoyoapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel(){

    val isUserSignedIn = MutableLiveData<Boolean>()

    fun setSignInOrNot(isSignedIn: Boolean) {
        isUserSignedIn.value = isSignedIn
    }

}
