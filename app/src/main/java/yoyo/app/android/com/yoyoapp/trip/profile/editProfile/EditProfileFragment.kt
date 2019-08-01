package yoyo.app.android.com.yoyoapp.trip.profile.editProfile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_edit_profile_trip.view.*
import org.json.JSONException
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

class EditProfileFragment : Fragment() {

    private val editProfileViewModel by viewModels<EditProfileViewModel>()
    private lateinit var circleImageView: ImageView
    private lateinit var userSharedManager: UserSharedManager
    private lateinit var profilePictureProgressbar: ProgressBar
    private lateinit var encodedProfileImage: String

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_edit_profile_trip, container, false)

        init(res)
<<<<<<< HEAD

=======
        bindProfile(res)
>>>>>>> 62e30d0b7fceedbb11770b876676926512e08917
        getDataFromSharedPrefrence(res)
        res.iv_editprofile_back.setOnClickListener{activity?.onBackPressed()}
        res.button_edit_profile_save.setOnClickListener{if (checkingCompleteFields(res))saveUserData(res)}
        circleImageView.setOnClickListener{openImageFromGallery() }

        return res
    }

    private fun checkingCompleteFields(res: View): Boolean {
        when {
            res.et_edit_profile_firstname.text.toString().trim { it <= ' ' }.isEmpty() -> {
                res.et_edit_profile_firstname.error = getString(R.string.first_name_cannot_be_empty)
                Toast.makeText(context, getString(R.string.please_complete_firstname), Toast.LENGTH_SHORT).show()
            }
            res.et_edit_profile_lastname.text.toString().trim { it <= ' ' }.isEmpty() -> {
                res.et_edit_profile_lastname.error = getString(R.string.last_name_cannot_be_empty)
                Toast.makeText(context, getString(R.string.please_complete_lastname), Toast.LENGTH_SHORT).show()
            }
            res.tv_edit_profile_email.text.toString().trim { it <= ' ' }.isEmpty() -> {
                res.tv_edit_profile_email.error = getString(R.string.email_can_not_empty)
                Toast.makeText(context, "please enter your Email", Toast.LENGTH_SHORT).show()
            }
            else -> return true
        }
        return false
    }

    private fun openImageFromGallery() {
        Dexter.withActivity(activity).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        cropingFunc()
                    } else {
                        Toast.makeText(context, "Permissions are not granted!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }


    private fun cropingFunc() {
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)
            .setMinCropWindowSize(512, 512)
            .start(activity!!)
    }

    private fun saveImageProfile(imageUri: Uri) {

        circleImageView.setImageURI(imageUri)
        val imageStream: InputStream?
        try {
            imageStream = context!!.contentResolver.openInputStream(imageUri)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encodedProfileImage = encodeImage(selectedImage)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        saveImageRequest()
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    private fun saveImageRequest() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("profile_picture", encodedProfileImage)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        editProfileViewModel.sendImageProfile(jsonObject)
        editProfileViewModel.getProfilePicture()!!.observe(activity!!, Observer{ profilePicture ->
            if (profilePicture != null) {
                userSharedManager.saveProfilePhoto(encodedProfileImage)
            } else {
            }
            profilePictureProgressbar.visibility = View.GONE
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {
                val imageUri = result.uri
                profilePictureProgressbar.visibility = View.VISIBLE
                saveImageProfile(imageUri)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                val error = result.error
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // get user's data from shared preferences
    private fun getDataFromSharedPrefrence(res: View) {
         userSharedManager.user.apply {
             if (!token.isNullOrEmpty()) {
                 res.et_edit_profile_firstname.setText(firstName)
                 res.et_edit_profile_lastname.setText(lastName)
                 res.tv_edit_profile_email.text = email
                 res.et_edit_profile_phone_number.setText(phoneNumber)
                 if (!profilePicture.equals("", ignoreCase = true)) {
                     val b = Base64.decode(profilePicture, Base64.DEFAULT)
                     val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                     circleImageView.setImageBitmap(bitmap)
                 }
             }
         }
    }

    private fun retrieveData(res: View) {
        editProfileViewModel.initGetProfile()
        editProfileViewModel.userMutableLiveData.observe(activity!!, Observer { user ->
            res.et_edit_profile_firstname.setText(user.firstName)
            res.et_edit_profile_lastname.setText(user.lastName)
            res.tv_edit_profile_email.text = user.email
            res.et_edit_profile_phone_number.setText(user.phoneNumber)
        })
    }

    private fun init(res: View) {
        editProfileViewModel.initialize()
        circleImageView = res.iv_edit_profile_img
        profilePictureProgressbar = res.progressbar_edit_profile_image
        userSharedManager = UserSharedManager(context!!)
    }


    // save user's data in server and then save the data in shared pref
    private fun saveUserData(res: View) {
            val jsonObject = JSONObject()
            try {
                // todo fix user id
                jsonObject.put("user_id", userSharedManager.user.id)
                jsonObject.put("firstname", res.et_edit_profile_firstname.text.toString())
                jsonObject.put("lastname", res.et_edit_profile_lastname.text.toString())
                jsonObject.put("email", res.tv_edit_profile_email.text.toString())
                jsonObject.put("phone_number", res.et_edit_profile_phone_number.text.toString())

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            editProfileViewModel.initEditProfile(jsonObject)
            editProfileViewModel.editedProfile!!.observe(activity!!, Observer { user ->
                if (user != null) {
                    Toast.makeText(context, getString(R.string.saved), Toast.LENGTH_SHORT).show()
                    userSharedManager.saveUser(user)
                    activity!!.onBackPressed()
                } else {
                    Toast.makeText(context, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                }
            })
    }

}

