package yoyo.app.android.com.yoyoapp.trip

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import io.michaelrocks.paranoid.Obfuscate
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.api.*

@Obfuscate
class ApiService2(context: Context) : Api(context) {
    private val IP = context.getString(R.string.endpoint)
    private val gson = Gson()

    fun sendSignUpRequest(request: SignUpRequest, f: (SignUpResponse?) -> Unit) {
        sendPostRequest(false, "$IP/api/user/register", JSONObject(gson.toJson(request))) {
            f(gson.fromJson(it.toString(), SignUpResponse::class.java))
        }
    }

    fun sendSignInRequest(request: SignInRequest, f: (SignInResponse?) -> Unit) {
        sendPostRequest(false, "$IP/api/user/login", JSONObject(gson.toJson(request))) {
            f(gson.fromJson(it.toString(), SignInResponse::class.java))
        }
    }


    fun getCategoriesRequest(f: (TourCategoriesResponse?) -> Unit) {
        sendGetRequest(false, "$IP/api/trips/selectedCategories") {
            f(gson.fromJson(it.toString(), TourCategoriesResponse::class.java))
        }
    }


    fun getDestinationsRequest(f: (TourDestinationsResponse?) -> Unit) {
        sendGetRequest(false, "$IP/api/trips/destinations") {
            f(gson.fromJson(it.toString(), TourDestinationsResponse::class.java))
        }
    }


    fun getProfileRequest(f: (ResponseGetProfile?) -> Unit) {
        sendGetRequest(true, "$IP/api/user/profile") {
            f(gson.fromJson(it.toString(), ResponseGetProfile::class.java))
        }
    }

    fun setProfilePictureRequest(request: RequestSetProfilePicture, f: (ResponseSetProfilePicture?) -> Unit) {
        sendPostRequest(true, "$IP/api/user/profile/picture", JSONObject(gson.toJson(request))) {
            f(gson.fromJson(it.toString(), ResponseSetProfilePicture::class.java))
        }
    }

    fun sendBookingRequest(scheduleId: String, request: BookScheduleRequest, f: (BookScheduleResponse) -> Unit) {
        sendPostRequest(true, "$IP/api/schedules/$scheduleId", JSONObject(gson.toJson(request))){
            f(gson.fromJson(it.toString(),BookScheduleResponse::class.java))
        }
    }
}