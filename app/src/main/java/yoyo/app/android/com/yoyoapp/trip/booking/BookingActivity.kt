package yoyo.app.android.com.yoyoapp.trip.booking

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_booking2.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.Traveller
import yoyo.app.android.com.yoyoapp.Flight.Booking.BookingThirdFragment
import yoyo.app.android.com.yoyoapp.Flight.Utils.CheckInternetConnection
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.dialog.ExitDialogFragment

import java.util.ArrayList

class BookingActivity : AppCompatActivity() {
    lateinit var travellers: ArrayList<Traveller>
    private var whichFragment = 0
    var minCapacity = 1
    var continueButton: Button? = null
    private var price: Double = 0.toDouble()
    private var frameLayout: FrameLayout? = null
    private var jsonObject: JSONObject? = null
    private var scheduleId: String? = null
    private var url: String? = null
    private val bookingViewModel by viewModels<BookingViewModel>()
    var constraintLayout: ConstraintLayout? = null
    var mobileNumberString: String? = null
    var emailString: String? = null
    var countryCode: String? = null
    var mobileNumberCode: String? = null
    var fullNameString: String? = null
    var passengerNumLiveData: MutableLiveData<Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking2)

        init()
        getBundle()
        for (i in 0 until minCapacity) {
            val traveller = Traveller()
            travellers.add(traveller)
        }
        setupFirstFragment()
        button_booking_continue.setOnClickListener { setupContinueButton() }
        iv_sign_out_back.setOnClickListener { setupBackButton() }

        passengerNumLiveData?.observe(this, Observer { isNumberAdded ->
            if (isNumberAdded!!) {
                val traveller = Traveller()
                travellers.add(traveller)
            } else {
                travellers.removeAt(travellers.size - 1)
            }
        })
    }

    private fun getBundle() {
        val intent = intent
        scheduleId = intent.getStringExtra("scheduleId")
        price = intent.getDoubleExtra("price", 0.0)
        minCapacity = intent.getIntExtra("minCapacity", 1)
        Log.d(TAG, "getBundle: " + scheduleId!!)
    }

    private fun init() {
        travellers = ArrayList()
        passengerNumLiveData = MutableLiveData()
        continueButton = button_booking_continue
        constraintLayout = cl_booking
    }

    // setup first fragment and setup views for first fragment
    private fun setupFirstFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val bookingFirstFragment = BookingFirstFragment()
        transaction.replace(R.id.fl_booking, bookingFirstFragment, "firstFragment")
        transaction.commit()

        tv_booking_traveller_info.setTextColor(resources.getColor(R.color.blue))
        iv_booking_traveller_info.setImageDrawable(resources.getDrawable(R.drawable.ic_traveller_info_blue_24dp))
    }


    // setup continue button for first, second and third fragment
    private fun setupContinueButton() {

        progressbar_booking_book.visibility = View.VISIBLE
        when (whichFragment) {
            0 -> {
                val result = checkingEmptyItems(fullNameString, emailString, mobileNumberString, travellers)
                if (result) {
                    if (!emailString!!.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$".toRegex())) {
                        Toasty.error(this, "Your Email Address is incorrect").show()
                        progressbar_booking_book.visibility = View.GONE
                        return
                    }
                    CheckInternetConnection(this@BookingActivity, frameLayout) { result1 ->
                        if (result1) {
                            bookFlight()
                        }
                    }
                } else if (!result) {
                    Toasty.error(this@BookingActivity, getString(R.string.please_complete_all_fields)).show()
                } else {
                    Toasty.error(this, "Your Email Address is incorrect").show()
                }
                progressbar_booking_book.visibility = View.GONE
            }
            1 -> {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
                progressbar_booking_book.visibility = View.GONE
            }
            2 -> {
                startActivity(Intent(this@BookingActivity, MainActivity::class.java))
                finish()
                progressbar_booking_book.visibility = View.GONE
            }
        }
    }

    fun checkingEmptyItems(
        fullNameString: String?,
        emailString: String?,
        phoneNumberString: String?,
        travellers: ArrayList<Traveller>
    ): Boolean {
        if (fullNameString == null || fullNameString == "") {
            Toasty.error(this, "full name can not be empty").show()
            return false
        }
        if (emailString == null || emailString == "") {
            Toasty.error(this, resources.getString(R.string.email_cannot_be_empty)).show()
            return false
        }
        if (phoneNumberString == null || phoneNumberString == "") {
            Toasty.error(this, resources.getString(R.string.phone_number_cannot_be_empty)).show()
            return false
        }
        for (i in travellers.indices) {
            if (travellers[i].firstName == null) {
                Toasty.error(this, "Please complete all traveller's data").show()
                return false
            }
        }
        return true
    }


    // book flight reuest for first fragment
    private fun bookFlight() {
        setupJson()
        bookingViewModel!!.initBookingRequest(scheduleId, jsonObject)
        bookingViewModel!!.bookingId.observe(this@BookingActivity, Observer { newurl ->
            if (newurl != null) {
                val bundle = Bundle()
                url = newurl
                bundle.putString("name", fullNameString)
                bundle.putInt("travellerNumber", travellers.size)
                bundle.putDouble("price", price)

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val bookingSecondFragment = BookingSecondFragment()
                bookingSecondFragment.arguments = bundle
                fragmentTransaction.replace(R.id.fl_booking, bookingSecondFragment)
                fragmentTransaction.commit()
                setupSecondFragment()
            }
        })
    }

    // book flight request and generate a json object
    fun setupJson() {
        jsonObject = JSONObject()
        try {
            val leaderJsonObject = JSONObject()
            leaderJsonObject.put("fullname", fullNameString)
            leaderJsonObject.put("email", emailString)
            leaderJsonObject.put("phone_number", mobileNumberCode!! + mobileNumberString!!)

            jsonObject!!.put("leader_traveller", leaderJsonObject)
            val jsonArray = JSONArray()
            for (item in travellers) {
                val js = JSONObject()
                js.put("firstname", item.firstName)
                js.put("lastname", item.lastName)
                js.put("gender", item.gender)
                js.put("dob", item.dateOfBirthTimeStamp)
                js.put("nationality", item.nationality)
                js.put("national_code", item.nationalityCode)
                js.put("passport_number", item.passportNumber)

                jsonArray.put(js)
            }

            jsonObject!!.put("companion_travellers", jsonArray)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    // setup second fragment and setup views for second fragment
    private fun setupSecondFragment() {
        tv_booking_traveller_info.setTextColor(resources.getColor(R.color.green))
        iv_booking_traveller_info.setImageDrawable(resources.getDrawable(R.drawable.ic_traveller_info_green_24dp))
        iv_booking_green_check1.visibility = View.VISIBLE

        tv_booking_payment.setTextColor(resources.getColor(R.color.blue))
        iv_booking_payment.setImageDrawable(resources.getDrawable(R.drawable.ic_payment_blue_24dp))
        button_booking_continue.background = resources.getDrawable(R.drawable.green_rectangle)

        button_booking_continue.text = "Pay"
        whichFragment = 1
    }

    // setup third fragment and setup views for third fragment
    private fun setupThirdFragment(bundle: Bundle) {

        val bookingThirdFragment = BookingThirdFragment()
        bookingThirdFragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_booking, bookingThirdFragment, "suceessFragment")
        transaction.addToBackStack("third booking fragment")
        transaction.commit()

        tv_booking_traveller_info.setTextColor(resources.getColor(R.color.green))
        iv_booking_traveller_info.setImageDrawable(resources.getDrawable(R.drawable.ic_traveller_info_green_24dp))
        iv_booking_green_check1.visibility = View.VISIBLE

        tv_booking_payment.setTextColor(resources.getColor(R.color.green))
        iv_booking_payment.setImageDrawable(resources.getDrawable(R.drawable.ic_payment_green_24dp))
        iv_booking_green_check2.visibility = View.VISIBLE

        tv_booking_success.setTextColor(resources.getColor(R.color.blue))
        iv_booking_success.setImageDrawable(resources.getDrawable(R.drawable.ic_tick_primary_24dp))

        button_booking_continue.text = getString(R.string.back_to_main_page)
        button_booking_continue.background = resources.getDrawable(R.drawable.green_rectangle)
        whichFragment = 2
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            button_booking_continue.visibility = View.VISIBLE
            constraintLayout?.visibility = View.VISIBLE
            super.onBackPressed()
        } else
            setupExit()
    }

    private fun setupBackButton() {
        setupExit()
    }

    // exit from booking activity
    private fun setupExit() {
        val exitDialogFragment = ExitDialogFragment()
        exitDialogFragment.show(supportFragmentManager, "exit")
    }

    companion object {

        private val TAG = "BookingActivity"
    }

}
