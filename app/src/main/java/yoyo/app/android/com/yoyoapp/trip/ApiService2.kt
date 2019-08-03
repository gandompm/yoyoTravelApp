package yoyo.app.android.com.yoyoapp.trip

import android.content.Context
import com.google.gson.Gson
import io.michaelrocks.paranoid.Obfuscate
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.trip.api.*

@Obfuscate
class ApiService2(context: Context) : Api(context) {
    companion object {
        const val IP = "http://www.yoyo.travel"
    }

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
        sendGetRequest(false, "$IP/api/trips/categories") {
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
}