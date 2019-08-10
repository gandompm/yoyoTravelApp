package yoyo.app.android.com.yoyoapp.trip.profile.editProfile

import android.content.Context
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.Flight.ApiServiceFlight.IP
import yoyo.app.android.com.yoyoapp.trip.ApiService
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.RequestSetProfilePicture

class EditProfileRepository(val context: Context) {

    private val apiService: ApiService = ApiService(context)
    private val apiService2: ApiService2 = ApiService2(context)

    fun getProfile(f: (User?) -> Unit) {
        apiService2.getProfileRequest {
            it?.let {
                val profile = User().apply {
                    firstName = it.firstname
                    lastName = it.lastname
                    email = it.email
                    phoneNumber = it.phoneNumber
                    profilePicture = IP + it.profileOriginalPicture
                }
                f(profile)
            }
            f(null)
        }
    }

    fun editProfile(jsonObject: JSONObject, f: (User?) -> Unit) {
        apiService.sendEditProfileRequest(jsonObject) { user -> f(user) }
    }

    fun setProfilePicture(request: RequestSetProfilePicture, f: (String?) -> Unit) {
        apiService2.setProfilePictureRequest(request) {
            f(IP + it?.profileThumbnailPicture)
        }
    }
}
