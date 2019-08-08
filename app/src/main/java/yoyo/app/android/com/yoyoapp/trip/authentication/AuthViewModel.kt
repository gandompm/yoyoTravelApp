package yoyo.app.android.com.yoyoapp.trip.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.trip.ApiService2.Companion.IP
import yoyo.app.android.com.yoyoapp.trip.api.SignInRequest
import yoyo.app.android.com.yoyoapp.trip.api.SignUpRequest

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val authRepository = AuthRepository(app)

    val singIn = MutableLiveData<User>()
    val signUp = MutableLiveData<User>()

    fun sendSignIn(username: String, password: String) {
        authRepository.sendSignInRequest(SignInRequest(username, password)) { response ->
            singIn.value =
                if (response != null) {
                 User().apply {
                    firstName = response.firstname
                    lastName = response.lastname
                    email = response.email
                    phoneNumber = response.phoneNumber
                    profilePicture = IP + response.profileThumbnailPicture
                    token = response.token
                }
            }
            else null
        }
    }

    fun sendSignUp(firstName: String, lastName: String, email: String, phoneNumber: String, password: String) {
        authRepository.sendSignUpRequest(SignUpRequest(firstName, lastName, email, phoneNumber, password)) {
            signUp.value =
                if (it != null)
                {
                    User().apply {
                        this.firstName = it.firstname
                        this.lastName = it.lastname
                        this.email = it.email
                        this.phoneNumber = it.phoneNumber
                        this.profilePicture = IP + it.profileThumbnailPicture
                        this.token = it.token
                    }
                }
                 else null
        }
    }
}