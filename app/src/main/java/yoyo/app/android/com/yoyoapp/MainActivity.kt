package yoyo.app.android.com.yoyoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager
import yoyo.app.android.com.yoyoapp.trip.Utils.Versioning
import yoyo.app.android.com.yoyoapp.trip.authentication.AuthenticationActivity
import yoyo.app.android.com.yoyoapp.trip.profile.profile.ProfileFragment
import yoyo.app.android.com.yoyoapp.trip.ticket.OrdersFragment
import yoyo.app.android.com.yoyoapp.trip.ticket.order.TourTicketFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        var isSignedIn = false
        private const val MAIN_TAB_TAG = "home_tab"
        private const val PROFILE_TAB_TAG = "profile_tab"
        private const val ORDER_TAB_TAG = "order_tab"
        private const val AUTH_CALL_BACK_CODE = 1001
    }
    private lateinit var activityViewModel: MainActivityViewModel
    private var languageSetup: LanguageSetup = LanguageSetup(this)
    private var currentContainer = R.id.main_container
    private var currentTabTag = MAIN_TAB_TAG

    var isFirstFragment = true
    // Trip Search Query Parameters
    var fromTime: Long = 0
    var toTime: Long = 0
    var fromTimeString: String
    var toTimeString: String
    var minDuration = 1
    var categories: ArrayList<Category> = ArrayList()
    var origin: Location = Location()
    var destination: Location = Location()
    var diffDays = 7

    private val homeBackStack = Stack<Fragment>()
    private val profileBackStack = Stack<Fragment>()
    private val orderBackStack = Stack<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = this.intent.data
        if (data != null) {
            checkingIfItIsFromPayment()
        } else {
            languageSetup.loadLanguageFromSharedPref()
            checkingSignIn()
            val versioning = Versioning()
            versioning.checkingUpdates(this)
            setupBottomNavigation()
        }
        setupFragments()

        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        activityViewModel.isUserSignedIn.observe(this,androidx.lifecycle.Observer {
            if (it) {

            }
        })
    }

    private fun checkingIfItIsFromPayment() {
        val data = this.intent.data

        if (data?.path?.contains("orders") == true) {
            val tourTicketFragment = TourTicketFragment().apply {
                arguments = Bundle().apply { putBoolean("showTicket", true) }
            }
            currentContainer = R.id.order_container
            showFragment(OrdersFragment(), tourTicketFragment, false)
        } else {
            languageSetup.loadLanguageFromSharedPref()
            checkingSignIn()
            val versioning = Versioning()
            versioning.checkingUpdates(this)
            setupBottomNavigation()
        }
    }

    init {
        val calendar = Calendar.getInstance()
        fromTime = calendar.timeInMillis
        fromTimeString = getDayFormat(calendar)
        calendar.add(Calendar.DAY_OF_MONTH, 15)
        toTime = calendar.timeInMillis
        toTimeString = getDayFormat(calendar)
    }

    private fun setupFragments() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_container, HomeFragment(), MAIN_TAB_TAG)
            add(R.id.profile_container, ProfileFragment(), PROFILE_TAB_TAG)
            add(R.id.order_container, OrdersFragment(), ORDER_TAB_TAG)
        }.commit()

        setAndShowCurrentFragment(MAIN_TAB_TAG)
    }

    private fun setupBottomNavigation() {
        bottom_navigation_main.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bn_home -> {
                    setAndShowCurrentFragment(MAIN_TAB_TAG)
                    true
                }
                R.id.bn_profile -> {
                    setAndShowCurrentFragment(PROFILE_TAB_TAG)
                    if (!isSignedIn) popUpSignInSignUpActivity()
                    true
                }
                R.id.bn_orders -> {
                    setAndShowCurrentFragment(ORDER_TAB_TAG)
                    if (!isSignedIn) popUpSignInSignUpActivity()
                    true
                }
                else -> false
            }
        }
    }

    fun popUpSignInSignUpActivity() {
        startActivityForResult(Intent(this, AuthenticationActivity::class.java), AUTH_CALL_BACK_CODE)
        overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
    }

    private fun setAndShowCurrentFragment(tag: String) {
        if (!isFirstFragment && currentTabTag == tag)
            goToFirstPageInTab()
        else {
            hideAllFragments()
            currentTabTag = tag
            when (tag) {
                MAIN_TAB_TAG -> configForMainFragmentAndShow()
                PROFILE_TAB_TAG -> configForProfileFragmentAndShow()
                ORDER_TAB_TAG -> configForOrderFragmentAndShow()
            }
            isFirstFragment = false
        }
    }

    private fun goToFirstPageInTab() {
        var fragment: Fragment? = null
        when (currentTabTag) {
            MAIN_TAB_TAG -> {
                for (i in 0 until homeBackStack.size)
                    fragment = homeBackStack.pop()
            }
            PROFILE_TAB_TAG -> {
                for (i in 0 until profileBackStack.size)
                    fragment = profileBackStack.pop()
            }
            ORDER_TAB_TAG -> {
                for (i in 0 until orderBackStack.size)
                    fragment = orderBackStack.pop()
            }
        }
        supportFragmentManager.commit {
            fragment?.let {
                replace(currentContainer, fragment)
            }
        }
    }

    private fun hideAllFragments() {
        main_container.visibility = View.GONE
        order_container.visibility = View.GONE
        profile_container.visibility = View.GONE
    }

    private fun configForMainFragmentAndShow() {
        main_container.visibility = View.VISIBLE
        currentContainer = R.id.main_container
        bottom_navigation_main.menu.getItem(1).isChecked = true
    }

    private fun configForProfileFragmentAndShow() {
        profile_container.visibility = View.VISIBLE
        currentContainer = R.id.profile_container
        bottom_navigation_main.menu.getItem(2).isChecked = true
    }

    private fun configForOrderFragmentAndShow() {
        order_container.visibility = View.VISIBLE
        currentContainer = R.id.order_container
        bottom_navigation_main.menu.getItem(0).isChecked = true
    }


    override fun onBackPressed() {
        try {
            val fragment = when (currentContainer) {
                R.id.main_container -> homeBackStack.pop()
                R.id.profile_container -> profileBackStack.pop()
                R.id.order_container -> orderBackStack.pop()
                else -> null
            }
            supportFragmentManager.commit {
                fragment?.let {
                    replace(currentContainer, fragment)
                }
            }
        } catch (e: Exception) {
            finish()
        }
    }

    fun showFragment(currentFragment: Fragment, nextFragment: Fragment, hasAnimation: Boolean = false) {
        supportFragmentManager.commit {
            if (hasAnimation) setCustomAnimations(R.anim.slide_in_up, R.anim.slide_down)
            add(currentContainer, nextFragment)
            when (currentContainer) {
                R.id.main_container -> homeBackStack.push(currentFragment)
                R.id.profile_container -> profileBackStack.push(currentFragment)
                R.id.order_container -> orderBackStack.push(currentFragment)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTH_CALL_BACK_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                isSignedIn = true
                activityViewModel.setSignInOrNot(true)
            }
        }
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    // check if the user has before signed in or not
    private fun checkingSignIn() {
        val userSharedManager = UserSharedManager(this@MainActivity)
        if (userSharedManager.token != "") {
            isSignedIn = true
        }
    }

    private fun getDayFormat(calendar: Calendar): String {
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())
        val dayOfWeekNameFrom = dayFormat.format(calendar.time)
        val monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

        return "$dayOfWeekNameFrom, ${calendar.get(Calendar.DAY_OF_MONTH)} $monthNameFrom"
    }

    fun setMainFrameLayoutPadding(left: Int, top: Int, right: Int, bottom: Int) {
        main_container.setPadding(left, top, right, bottom)
    }

    fun showBottomNavigation() {
        bottom_navigation_main.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        bottom_navigation_main.visibility = View.GONE
    }
}

