package yoyo.app.android.com.yoyoapp.trip.profile.editProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager
import yoyo.app.android.com.yoyoapp.trip.api.RequestSetProfilePicture

class EditProfileViewModel(val app: Application) : AndroidViewModel(app) {

    private val repository: EditProfileRepository = EditProfileRepository(app)
    val loggedInUser: MutableLiveData<User> = MutableLiveData()
    private var newUserMutableLiveData: MutableLiveData<User>? = null
    private var profilePicture: MutableLiveData<String> = MutableLiveData()

    val editedProfile: LiveData<User>?
        get() = newUserMutableLiveData


    fun loadProfile() {
        viewModelScope.launch {
            val userFromDb = loadProfileFromDatabase()
            if (!userFromDb?.token.isNullOrEmpty()) {
                loggedInUser.postValue(userFromDb)
            }
            repository.getProfile {
                it?.let { user -> loggedInUser.postValue(user) }
            }
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


    private suspend fun loadProfileFromDatabase() = withContext(Dispatchers.IO) {
        val userSharedManager = UserSharedManager(app.applicationContext)
        userSharedManager.user
    }
}
