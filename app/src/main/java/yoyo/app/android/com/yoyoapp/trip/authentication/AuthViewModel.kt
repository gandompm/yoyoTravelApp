package yoyo.app.android.com.yoyoapp.trip.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.trip.api.SignInRequest
import yoyo.app.android.com.yoyoapp.trip.api.SignUpRequest

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val authRepository = AuthRepository(app)

    val singIn = MutableLiveData<User>()
    val signUp = MutableLiveData<User>()

    fun sendSignIn(username: String, password: String) {
        authRepository.sendSignInRequest(SignInRequest(username, password)) {
            val response = User().apply {
                firstName = it?.firstname
                lastName = it?.lastname
                email = it?.email
                phoneNumber = it?.phoneNumber
                profilePicture = it?.profileThumbnailPicture
                token = it?.token
            }

            this.singIn.value = response
        }
    }

    fun sendSingUp(firstName: String, lastName: String, email: String, phoneNumber: String, password: String) {
        authRepository.sendSignUpRequest(SignUpRequest(firstName, lastName, email, phoneNumber, password)) {
            val response = User().apply {
                this.firstName = it?.firstname
                this.lastName = it?.lastname
                this.email = it?.email
                this.phoneNumber = it?.phoneNumber
                this.profilePicture = it?.profileThumbnailPicture
                this.token = it?.token
            }

            this.signUp.value = response
        }
    }
}