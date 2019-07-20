package yoyo.app.android.com.yoyoapp.trip.authentication

import android.content.Context
import yoyo.app.android.com.yoyoapp.trip.ApiService2
import yoyo.app.android.com.yoyoapp.trip.api.SignInRequest
import yoyo.app.android.com.yoyoapp.trip.api.SignInResponse
import yoyo.app.android.com.yoyoapp.trip.api.SignUpRequest
import yoyo.app.android.com.yoyoapp.trip.api.SignUpResponse

class AuthRepository(context: Context) {
    private val apiService2 = ApiService2(context)

    fun sendSignInRequest(request: SignInRequest, f: (SignInResponse?) -> Unit) {
        apiService2.sendSignInRequest(request, f)
    }

    fun sendSignUpRequest(request: SignUpRequest, f: (SignUpResponse?) -> Unit) {
        apiService2.sendSignUpRequest(request, f)
    }

}