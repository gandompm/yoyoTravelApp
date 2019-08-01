package yoyo.app.android.com.yoyoapp.trip.profile.editProfile

import android.content.Context
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.trip.ApiService
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.ProfileResponse

class EditProfileRepository(val context: Context) {

    private val apiService: ApiService = ApiService(context)
    private val apiService2: ApiService2 = ApiService2(context)

    fun getProfile(f: (User?) -> Unit) {
        apiService2.getProfileRequest {
            run {
                val profile = User().apply {
                    firstName = it?.firstname
                    lastName = it?.lastname
                    email = it?.email
                    phoneNumber = it?.phoneNumber
                    profilePicture = it?.profileOriginalPicture
                }
                f(profile)
            }
        }
    }

    fun getEditedUser(jsonObject: JSONObject, f: (User?) -> Unit) {
        apiService.sendEditProfileRequest(jsonObject) { user -> f(user)}
    }

    fun getImageProfile(jsonObject: JSONObject, f: (String?) -> Unit){
        apiService.sendProfilePhotoRequest(jsonObject) {  f(it) }
    }


}
