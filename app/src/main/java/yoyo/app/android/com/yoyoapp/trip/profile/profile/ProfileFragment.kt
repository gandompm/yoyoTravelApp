package yoyo.app.android.com.yoyoapp.trip.profile.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*
import yoyo.app.android.com.yoyoapp.DataModels.User
import yoyo.app.android.com.yoyoapp.Flight.Dialog.LanguageDialogFragment
import yoyo.app.android.com.yoyoapp.Flight.Profile.AboutFragment
import yoyo.app.android.com.yoyoapp.Flight.Profile.RuleFragment
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.MainActivityViewModel
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager
import yoyo.app.android.com.yoyoapp.trip.profile.SignOutFragment
import yoyo.app.android.com.yoyoapp.trip.profile.editProfile.EditProfileFragment
import yoyo.app.android.com.yoyoapp.trip.profile.travellerCompanion.TravellerCompanionFragment

class ProfileFragment : Fragment() {
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    private lateinit var userSharedManager: UserSharedManager
    private var user: User? = null

    private var isSignedIn = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_profile_trip, container, false)

        init()
        observe(res)
        checkSignIn()
        loadViewsIfSignedIn(res)
        setupClickListeners(res)

        return res
    }

    private fun observe(res: View) {
        observeUser(res)
    }

    private fun observeUser(res: View) {
        mainActivityViewModel.isUserSignedIn.observe(this, Observer {
            isSignedIn = it

            if (it) {
                user = userSharedManager.user
                res.tv_profile_name.text = "${user?.firstName} ${user?.lastName}"
            }
        })
    }

    private fun init() {
        userSharedManager = UserSharedManager(context)
        user = userSharedManager.user
    }

    private fun checkSignIn() {
        isSignedIn = userSharedManager.token != ""
    }

    private fun loadViewsIfSignedIn(res: View) {
        if (isSignedIn) {
            res.tv_profile_name.text = "${user?.firstName} ${user?.lastName}"
            if (!user?.profilePicture.equals("", ignoreCase = true)) {
                Picasso.with(context)
                    .load(user?.profilePicture)
                    .into(res.iv_profile)
            }
        }
    }

    private fun setupClickListeners(res: View) {
        setupTravellerCompanionClickListener(res)
        setupRulesClickListener(res)
        setupEditProfileClickListener(res)
        setupSignOutClickListener(res)
        setupAboutClickListener(res)
        setupLanguageClickListener(res)
    }

    private fun setupTravellerCompanionClickListener(res: View) {
        res.iv_profile_traveller_companion.setOnClickListener {
            if (isSignedIn)
                showTravellerCompanionPage()
            else
                popUpSignInSignUpActivity()
        }
    }

    private fun showTravellerCompanionPage() {
        (activity as MainActivity).showFragment(this, TravellerCompanionFragment(), "", false)
    }

    private fun popUpSignInSignUpActivity() {
        (activity as MainActivity).popUpSignInSignUpActivity()
    }

    private fun setupRulesClickListener(res: View) {
        res.iv_profile_rules.setOnClickListener { showRulesPage() }
    }

    private fun showRulesPage() {
        (activity as MainActivity).showFragment(this, RuleFragment(), "", false)
    }

    private fun setupEditProfileClickListener(res: View) {
        res.iv_profile_edit_profile.setOnClickListener {
            if (isSignedIn)
                showEditProfilePage()
            else
                popUpSignInSignUpActivity()
        }
    }

    private fun showEditProfilePage() {
        (activity as MainActivity).showFragment(this, EditProfileFragment(), "", false)
    }

    private fun setupSignOutClickListener(res: View) {
        res.iv_profile_signout.setOnClickListener {
            if (isSignedIn)
                showSignOutPage()
            else
                popUpSignInSignUpActivity()
        }
    }

    private fun showSignOutPage() {
        (activity as MainActivity).showFragment(this, SignOutFragment(), "", false)
    }

    private fun setupAboutClickListener(res: View) {
        res.iv_profile_aboout.setOnClickListener { showAboutPage() }
    }

    private fun showAboutPage() {
        (activity as MainActivity).showFragment(this, AboutFragment(), "", false)
    }

    private fun setupLanguageClickListener(res: View) {
        res.iv_profile_language.setOnClickListener {
            showLanguagePage()
        }
    }

    private fun showLanguagePage() {
        fragmentManager?.let {
            LanguageDialogFragment().show(it, "setting language")
        }
    }
}
