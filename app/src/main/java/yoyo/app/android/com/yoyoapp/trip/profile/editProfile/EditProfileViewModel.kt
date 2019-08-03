package yoyo.app.android.com.yoyoapp.trip.profile.editProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.trip.api.RequestSetProfilePicture

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EditProfileRepository = EditProfileRepository(application)
    val userMutableLiveData: MutableLiveData<User> = MutableLiveData()
    private var newUserMutableLiveData: MutableLiveData<User>? = null
    private var profilePicture: MutableLiveData<String> = MutableLiveData()


    val editedProfile: LiveData<User>?
        get() = newUserMutableLiveData


    fun initGetProfile() {
        repository.getProfile {
            userMutableLiveData.value = it
        }
    }


    fun initEditProfile(jsonObject: JSONObject) {
        newUserMutableLiveData = MutableLiveData()
        repository.getEditedUser(jsonObject) {
            newUserMutableLiveData?.value = it
        }
    }

    fun sendImageProfile(encodedPicture: String) {
        repository.setProfilePicture(RequestSetProfilePicture(encodedPicture)){
            profilePicture.value = it
        }
    }

    fun getProfilePicture(): LiveData<String>? {
        return profilePicture
    }

}
