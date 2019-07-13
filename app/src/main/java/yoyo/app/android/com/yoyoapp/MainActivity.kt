package yoyo.app.android.com.yoyoapp

import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.Flight.Utils.LanguageSetup
import yoyo.app.android.com.yoyoapp.Flight.Utils.UserSharedManagerFlight
import yoyo.app.android.com.yoyoapp.Trip.ticket.OrdersFragment
import yoyo.app.android.com.yoyoapp.Trip.Utils.Versioning
import yoyo.app.android.com.yoyoapp.Trip.profile.profile.ProfileFragment
import yoyo.app.android.com.yoyoapp.Trip.ticket.order.TourTicketFragment

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    var languageSetup: LanguageSetup
    var currentContainer = R.id.main_container
    private var currentFragmentTag = MAIN_FRAGMENT_TAG
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var mainFrameLayout: FrameLayout
    // Trip Search Query Parameters
    var fromPrice = 0
    var toPrice = 20000000
    var fromTime: Long = 0
    var toTime: Long = 0
    var fromTimeString: String
    var toTimeString: String
    var minDuration = 1
    var categories: ArrayList<Category>
    var origin: Location
    var destination: Location
    var diffDays = 7


    companion object {
        var isSingnedIn = false
        private const val MAIN_FRAGMENT_TAG = "home_fragment"
        private const val PROFILE_FRAGMENT_TAG = "profile_fragment"
        private const val ORDER_FRAGMENT_TAG = "order_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation_main)
        mainFrameLayout = findViewById(R.id.main_container)

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
    }

    private fun checkingIfItIsFromPayment() {
        val data = this.intent.data

        if (data!!.path!!.contains("orders")) {
            val tourTicketFragment = TourTicketFragment()
            val bundle = Bundle()
            bundle.putBoolean("showTicket", true)
            tourTicketFragment.arguments = bundle
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_container, tourTicketFragment).addToBackStack("ticket")
            fragmentTransaction.commit()
        } else {
            languageSetup.loadLanguageFromSharedPref()
            checkingSignIn()
            val versioning = Versioning()
            versioning.checkingUpdates(this)
            setupBottomNavigation()
        }
    }

   init{
        languageSetup = LanguageSetup(this)
        // initializing Trip Query
        categories = ArrayList()
        origin = Location()
        destination = Location()
        val calendar = Calendar.getInstance()
        fromTime = calendar.timeInMillis
        fromTimeString = getDayFormat(calendar)
        calendar.add(Calendar.DAY_OF_MONTH, 15)
        toTime = calendar.timeInMillis
        toTimeString = getDayFormat(calendar)
    }

    private fun setupFragments() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_container, MainPageFragment(), MAIN_FRAGMENT_TAG)
            add(R.id.profile_container, ProfileFragment(), PROFILE_FRAGMENT_TAG)
            add(R.id.order_container, OrdersFragment(), ORDER_FRAGMENT_TAG)
        }.commit()

        setAndShowCurrentFragment(MAIN_FRAGMENT_TAG)
    }

    private fun setupBottomNavigation() {

        bottom_navigation_main.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bn_home -> {
                    setAndShowCurrentFragment(MAIN_FRAGMENT_TAG)
                    true
                }
                R.id.bn_profile -> {
                    setAndShowCurrentFragment(PROFILE_FRAGMENT_TAG)
                    true
                }
                R.id.bn_orders -> {
                    setAndShowCurrentFragment(ORDER_FRAGMENT_TAG)
                    true
                }
                else -> false
            }
        }
    }

    private fun setAndShowCurrentFragment(tag: String) {
        hideAllFragments()
        currentFragmentTag = tag
        when (tag) {
            MAIN_FRAGMENT_TAG -> configForMainFragmentAndShow()
            PROFILE_FRAGMENT_TAG -> configForProfileFragmentAndShow()
            ORDER_FRAGMENT_TAG -> configForOrderFragmentAndShow()
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

    // setup fragment
    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.popBackStack()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main_container, fragment, tag)
        ft.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            handlePopBackstack()
        } else {
            super.onBackPressed()
        }
    }

    private fun handlePopBackstack() {
        val state = supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name?.toInt()

        state?.let {
            if (state != currentContainer) {
                when (state) {
                    R.id.order_container -> setAndShowCurrentFragment(ORDER_FRAGMENT_TAG)
                    R.id.main_container -> setAndShowCurrentFragment(MAIN_FRAGMENT_TAG)
                    R.id.profile_container -> setAndShowCurrentFragment(PROFILE_FRAGMENT_TAG)
                    else -> super.onBackPressed()
                }
            } else {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    // check if the user has before signed in or not
    private fun checkingSignIn() {
        val userSharedManager = UserSharedManagerFlight(this@MainActivity)
        userSharedManager.client
        if (userSharedManager.token != "") {
            isSingnedIn = true
        }
    }

    private fun getDayFormat(calendar: Calendar): String {
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())
        val dayOfWeekNameFrom = dayFormat.format(calendar.time)
        val monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

        return "$dayOfWeekNameFrom, ${calendar.get(Calendar.DAY_OF_MONTH)} $monthNameFrom"
    }

}

