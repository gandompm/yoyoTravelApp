package yoyo.app.android.com.yoyoapp.trip

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import es.dmoral.toasty.Toasty
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParserException
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.*

open class Api(val context: Context) {
    private val userSharedManager: UserSharedManager = UserSharedManager(context)
    private val JWT = userSharedManager.token
    private val apiKey = "ChapterLittleIngeniousFerrariMagic"

    protected fun sendPostRequest(shouldSendJWT: Boolean, url: String, jsonObject: JSONObject? = null, f: (JSONObject?) -> Unit) {
        sendHttpRequest(Request.Method.POST, shouldSendJWT, url, jsonObject, f)
    }

    protected fun sendGetRequest(shouldSendJWT: Boolean, url: String, f: (JSONObject?) -> Unit) {
        sendHttpRequest(Request.Method.GET, shouldSendJWT, url, null, f)
    }

    private fun sendHttpRequest(method: Int, shouldSendJWT: Boolean, url: String, jsonObject: JSONObject? = null, f: (JSONObject?) -> Unit) {
        val jsonObjectRequest = object: JsonObjectRequest(
            method,
            url,
            jsonObject,
            Response.Listener(f),
            Response.ErrorListener {
                f(null)
                errorHandling(it)
                it.printStackTrace()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["api-key"] = apiKey
                if (shouldSendJWT) {
                    params["Authorization"] = "JWT $JWT"
                }
                return params
            }
        }

        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(
                18000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }

    private fun errorHandling(error: VolleyError) {
        if (error is TimeoutError || error is NoConnectionError) {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetwork: NetworkInfo? = null
            if (cm != null) {
                activeNetwork = cm.activeNetworkInfo
            }
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
                Toasty.warning(context, "Server is not connected to internet.").show()
            } else {
                Toasty.warning(context, "Your device is not connected to internet.").show()
            }
        } else if (error is AuthFailureError) {
            Toasty.error(context, "server couldn't find the authenticated request.").show()
        } else if (error is ServerError) {
            Toasty.error(context, "Server is not responding.").show()
        } else if (error is NetworkError || error.cause is ConnectException
            || error.cause?.message?.contains("connection") == true
        ) {
            Toasty.warning(context, "Your device is not connected to internet.").show()
        } else if (error is ParseError || error.cause is IllegalStateException
            || error.cause is JSONException
            || error.cause is XmlPullParserException
        ) {
            Toasty.error(context, "Parse Error (because of invalid json or xml).").show()
        } else if (error is TimeoutError || error.cause is SocketTimeoutException
            || error.cause is ConnectTimeoutException
            || error.cause is SocketException
            || error.cause?.message?.contains("Connection timed out") == true
        ) {
            Toasty.error(context, "Connection timeout error").show()
        }
    }
}