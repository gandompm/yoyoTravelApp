package yoyo.app.android.com.yoyoapp.trip.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_my_traveller_dialog.view.*
import yoyo.app.android.com.yoyoapp.DataModels.Traveller
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.adapter.TravellerCompanionRecyclerviewAddapter
import yoyo.app.android.com.yoyoapp.trip.booking.firstPage.BookingViewModel

import java.util.ArrayList


class MyTravellerDialogFragment(private val f: (Traveller) -> Unit) : DialogFragment() {

    private var travellerRecyclerViewAdapter: TravellerCompanionRecyclerviewAddapter? = null
    private lateinit var travellers: ArrayList<Traveller>
    private val bookingViewModel by viewModels<BookingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_my_traveller_dialog, container, false)

        travellers = ArrayList()
        getTravellersCompanions()
        setupRecyclerView(res)

        return res
    }

    // get traveller companions if the user has them before added
    private fun getTravellersCompanions() {

        bookingViewModel.initGetTravellers()
        bookingViewModel.travellerCompanionsMutableList.observe(activity!!, Observer { travellerList ->
            if (! travellerList.isNullOrEmpty())
            {
                travellers.addAll(travellerList)
                travellerRecyclerViewAdapter?.notifyDataSetChanged()
            }
                view?.tv_travellerdialog_message?.visibility = View.GONE
        })

    }

    private fun setupRecyclerView(res: View) {
        travellerRecyclerViewAdapter = TravellerCompanionRecyclerviewAddapter(travellers, activity,
            TravellerCompanionRecyclerviewAddapter.OnItemSelected { traveller ->
                f(traveller)
                dismiss()
            })
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        res.rv_mytraveller.layoutManager = linearLayoutManager
        res.rv_mytraveller.adapter = travellerRecyclerViewAdapter
    }
}
