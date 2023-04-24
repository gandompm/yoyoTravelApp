package yoyo.app.android.com.yoyoapp.trip.booking.firstPage

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_booking_first2.view.*
import kotlinx.android.synthetic.main.fragment_booking_first2.view.iv_sign_out_back
import kotlinx.android.synthetic.main.fragment_booking_first2.view.progressbar_booking_book
import yoyo.app.android.com.yoyoapp.DataModels.Traveller
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.SharedDataViewModel
import yoyo.app.android.com.yoyoapp.trip.adapter.TravellerRecyclerviewAddapter
import yoyo.app.android.com.yoyoapp.trip.api.BookScheduleRequest
import yoyo.app.android.com.yoyoapp.trip.booking.BookingSecondFragment
import yoyo.app.android.com.yoyoapp.trip.booking.TravellerInfoFragment
import yoyo.app.android.com.yoyoapp.trip.dialog.ExitDialogFragment
import java.util.ArrayList

class BookingFirstFragment : Fragment() {

    var travellers: ArrayList<Traveller> = ArrayList()
    private var travellerRecyclerAdapter: TravellerRecyclerviewAddapter? = null
    private var passengerNum = 2
    private var minCapacity = 1
    private val bookingViewModel by viewModels<BookingViewModel>()
    private val sharedDataViewModel by activityViewModels<SharedDataViewModel>()
    private var price: Double = 0.0
    private lateinit var scheduleId: String
    private var mobileNumberCode: String? = null
    private lateinit var bookScheduleRequest: BookScheduleRequest

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_booking_first2, container, false)

        init(res)
        observeTravellers()
        setupTravellersArray()
        res.iv_bookingfirst_plus.setOnClickListener { addTravellersNumber()}
        res.iv_bookingfirst_minus.setOnClickListener { reduceTravellersNumber()}
        res.button_booking_continue.setOnClickListener{ setupContinueButton()}
        res.iv_sign_out_back.setOnClickListener{ setupExit()}
        setupRecycler(res)
        setupMobileNumber(res)

        return res
    }

    private fun observeTravellers() {
        sharedDataViewModel.travellersList.observe(context as LifecycleOwner, Observer {
            travellers = it
            travellerRecyclerAdapter?.notifyDataSetChanged()
        })
    }

    private fun setupTravellersArray() {
        for (i in 0 until minCapacity) {
            val traveller = Traveller()
            sharedDataViewModel.addTraveller(traveller)
        }
    }

    private fun setupContinueButton() {
        val fieldsAreNotNull = checkingEmptyItems(view?.et_booking_contact_name?.text.toString(),
            view?.et_traveller_details_email?.text.toString(),
            view?.et_traveller_details_mobile?.text.toString(), travellers)
        if (fieldsAreNotNull) {
            bookFlight()
        }
    }

    private fun bookFlight() {
        view?.progressbar_booking_book?.visibility = View.VISIBLE
        setupBookingRequestObject()
        bookingViewModel.initBookingRequest(scheduleId, bookScheduleRequest)
        bookingViewModel.paymentUrlMutableLiveData.observe(activity!!, Observer { newurl ->
            if (newurl != null) {
                val bundle = Bundle()
                bundle.putString("name", view?.et_booking_contact_name?.text.toString())
                bundle.putInt("travellerNumber", travellers.size)
                bundle.putDouble("price", price)
                bundle.putString("url", newurl)

                val fragmentTransaction = fragmentManager?.beginTransaction()
                val bookingSecondFragment = BookingSecondFragment()
                bookingSecondFragment.arguments = bundle
                fragmentTransaction?.replace(R.id.main_container, bookingSecondFragment)
                fragmentTransaction?.commit()
            }
            view?.progressbar_booking_book?.visibility = View.GONE
        })
    }

    private fun setupBookingRequestObject() {
        val leaderTraveller = BookScheduleRequest.LeaderTraveller(
            view?.et_traveller_details_email?.text.toString()
            , view?.et_booking_contact_name?.text.toString()
            , mobileNumberCode + view?.et_traveller_details_mobile?.text.toString())

        val travellerCompanions = ArrayList<BookScheduleRequest.CompanionTraveller>()
        for (traveller in travellers) {
            traveller.apply {
                travellerCompanions.add(
                    BookScheduleRequest.CompanionTraveller(
                        dateOfBirthTimeStamp.toInt(),
                        firstName,
                        gender,
                        lastName,
                        nationalityCode,
                        nationality,
                        passportNumber
                    )
                )
            }
        }
        bookScheduleRequest = BookScheduleRequest(travellerCompanions,leaderTraveller)
    }

    private fun checkingEmptyItems(
        fullNameString: String?,
        emailString: String?,
        phoneNumberString: String?,
        travellers: ArrayList<Traveller>
    ): Boolean {
        if (fullNameString.isNullOrEmpty()) {
            Toasty.error(activity!!, "full name can not be empty").show()
            return false
        }
        if (emailString.isNullOrEmpty()) {
            Toasty.error(activity!!, resources.getString(R.string.email_cannot_be_empty)).show()
            return false
        }
        if (phoneNumberString.isNullOrEmpty()) {
            Toasty.error(activity!!, resources.getString(R.string.phone_number_cannot_be_empty)).show()
            return false
        }
        for (i in travellers.indices) {
            if (travellers[i].firstName == null) {
                Toasty.error(activity!!, "Please complete all traveller's data").show()
                return false
            }
        }
        if (! view?.et_traveller_details_email?.text.toString()
                .matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$".toRegex())) {
            Toasty.error(activity!!, "Your Email Address is incorrect").show()
            return false
        }
        return true
    }


    private fun reduceTravellersNumber() {
        if (passengerNum > minCapacity) {
            passengerNum --
            travellers.removeAt(travellers.size - 1)
            view?.tv_bookingfirst_num?.text = passengerNum.toString()
            travellerRecyclerAdapter?.notifyDataSetChanged()
        }
        if (passengerNum == minCapacity) {
            view?.iv_bookingfirst_minus?.setImageDrawable(resources.getDrawable(R.drawable.ic_remove_circle_outline_light_24dp))
        }
    }

    private fun addTravellersNumber() {
        if (passengerNum < 9) {
            passengerNum++
            val traveller = Traveller()
            travellers.add(traveller)
            view?.tv_bookingfirst_num?.text = passengerNum.toString()
            travellerRecyclerAdapter?.notifyDataSetChanged()
        }
        if (passengerNum >= minCapacity + 1) {
            appearMinusView(view?.iv_bookingfirst_minus, view?.tv_bookingfirst_num)
        }
    }

    private fun init(res: View) {
        minCapacity = arguments?.getInt("minCapacity") ?: 1
        price = arguments?.getDouble("price") ?: 0.0
        scheduleId = arguments?.getString("scheduleId") ?: ""
        passengerNum = travellers.size
        res.tv_bookingfirst_num.text = passengerNum.toString()
    }

    // get country code for mobile number
    private fun setupMobileNumber(res: View) {
        mobileNumberCode = res.ccp_traveller_details.defaultCountryCodeWithPlus
        res.ccp_traveller_details.setOnCountryChangeListener {
            mobileNumberCode = res.ccp_traveller_details.selectedCountryCodeWithPlus
        }
    }

    private fun setupRecycler(res: View) {
        travellerRecyclerAdapter = TravellerRecyclerviewAddapter(travellers, activity,
            TravellerRecyclerviewAddapter.OnItemSelected { traveller, position ->
                sendToTravellerInfoPage(position)
            })
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        res.rv_booking.layoutManager = linearLayoutManager
        res.rv_booking.adapter = travellerRecyclerAdapter
    }

    private fun sendToTravellerInfoPage(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)

        val travellerInfoFragment = TravellerInfoFragment()
        travellerInfoFragment.arguments = bundle
        (context as MainActivity).showFragment(this, travellerInfoFragment, "traveller_Info", false)

    }

    private fun appearMinusView(imageView: ImageView?, textView: TextView?) {
        imageView?.setImageDrawable(resources.getDrawable(R.drawable.ic_remove_circle_outline_black_24dp))
        textView?.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun setupExit() {
        val exitDialogFragment = ExitDialogFragment()
        fragmentManager?.let { exitDialogFragment.show(it, "exit") }
    }

    override fun onPause() {
        sharedDataViewModel.resetBookingTravellers()
        super.onPause()
    }
}
