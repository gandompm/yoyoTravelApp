package yoyo.app.android.com.yoyoapp.trip

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import es.dmoral.toasty.Toasty
import io.michaelrocks.paranoid.Obfuscate
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParserException
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager
import yoyo.app.android.com.yoyoapp.trip.api.*
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.*

@Obfuscate
class ApiService2(context: Context) : Api(context) {
    private val IMAGEIP = "http://www.yoyo.travel"
    private val IP = "http://www.yoyo.travel"

    fun sendSignUpRequest(request: SignUpRequest, f: (SignUpResponse?) -> Unit) {
        sendPostRequest(false, "$IP/api/user/register", JSONObject(request.toJson())) {
            f(SignUpResponse.fromJson(it.toString()))
        }
    }

    fun sendSignInRequest(request: SignInRequest, f: (SignInResponse?) -> Unit) {
        sendPostRequest(false, "$IP/api/user/login", JSONObject("{username: msh, password: mshpassword}")) {
            f(SignInResponse.fromJson(it.toString()))
        }
    }


    fun getCategoriesRequest(f: (TourCategories?) -> Unit) {
        sendGetRequest(false, "$IP/api/trips/categories") {
            f(TourCategories.fromJson(it.toString()))
        }
    }


    fun getDestinationsRequest(f: (TourDestinations?) -> Unit) {
        sendGetRequest(false, "$IP/api/trips/destinations") {
                f(TourDestinations.fromJson(it.toString()))
            }
        }


    fun getProfileRequest(f: (ProfileResponse?) -> Unit) {
            sendGetRequest(true, "$IP/api/user/profile") {
                f(ProfileResponse.fromJson(it.toString()))
            }
        }


}