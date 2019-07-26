package yoyo.app.android.com.yoyoapp.trip.profile.editProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.User

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EditProfileRepository = EditProfileRepository(application)
    private var userMutableLiveData: MutableLiveData<User>? = null
    private var newUserMutableLiveData: MutableLiveData<User>? = null
    private var profilePicture: MutableLiveData<String>? = null

    val profile: LiveData<User>?
        get() = userMutableLiveData

    val editedProfile: LiveData<User>?
        get() = newUserMutableLiveData


    fun initGetProfile() {
        userMutableLiveData = MutableLiveData()
        repository.getProfile {
            userMutableLiveData?.value = it
        }
    }

    fun initEditProfile(jsonObject: JSONObject) {
        newUserMutableLiveData = MutableLiveData()
        repository.getEditedUser(jsonObject) {
            newUserMutableLiveData?.value = it
        }
    }

    fun sendImageProfile(jsonObject: JSONObject) {
        profilePicture = MutableLiveData()
        repository.getImageProfile(jsonObject){
            profilePicture?.value = it
        }
    }

    fun getProfilePicture(): LiveData<String>? {
        return profilePicture
    }

}