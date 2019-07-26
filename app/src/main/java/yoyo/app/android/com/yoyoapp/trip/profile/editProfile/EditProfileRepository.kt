package yoyo.app.android.com.yoyoapp.trip.profile.editProfile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.trip.ApiService

class EditProfileRepository(val context: Context) {

    private val apiService: ApiService = ApiService(context)

    fun getProfile(f: (User?) -> (Unit)) {
        apiService.getProfileRequest { user -> f(user) }
    }

    fun getEditedUser(jsonObject: JSONObject, f: (User?) -> (Unit)) {
        apiService.sendEditProfileRequest(jsonObject) { user -> f(user)}
    }

    fun getImageProfile(jsonObject: JSONObject, f: (String?) -> (Unit)){
        apiService.sendProfilePhotoRequest(jsonObject) {  f(it) }
    }


}
