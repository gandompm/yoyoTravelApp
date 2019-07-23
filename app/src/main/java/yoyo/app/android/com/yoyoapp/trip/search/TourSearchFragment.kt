package yoyo.app.android.com.yoyoapp.trip.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import es.dmoral.toasty.Toasty
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.fragment_search_trip.view.*
import yoyo.app.android.com.yoyoapp.DataModels.Location
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.SharedDataViewModel
import yoyo.app.android.com.yoyoapp.Utils
import yoyo.app.android.com.yoyoapp.trip.Utils.DatePickerFragment
import yoyo.app.android.com.yoyoapp.trip.dialog.PriceFilterBottomSheetDialogFragment
import yoyo.app.android.com.yoyoapp.trip.result.TripResultFragment
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class TourSearchFragment : Fragment(), View.OnClickListener {
    private var priceFilterBottomSheetFragment: PriceFilterBottomSheetDialogFragment? = null
    private var destinationsList: ArrayList<Location> = ArrayList()
    private var diffDays = "7"
    private var startDateString = "From..."
    private var endDateString = "To..."
    private val sharedDataViewModel by activityViewModels<SharedDataViewModel>()
    private val tourSearchViewModel by activityViewModels<TourSearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_search_trip, container, false)


        sharedDataViewModel.fromTimeString.observe(activity!!, Observer { res.tv_search_check_in.text = it})
        sharedDataViewModel.toTimeString.observe(activity!!, Observer { res.tv_search_check_out.text = it})
        res.tv_search_check_in.text = sharedDataViewModel.fromTimeString.value
        res.tv_search_check_out.text = sharedDataViewModel.toTimeString.value

        setupOnclickListener(res)
        setupSearchButton(res)
        res.iv_search_back.setOnClickListener { activity!!.onBackPressed() }
        getDestination()

        tourSearchViewModel.destinations.observe(activity!!,
            Observer { locations ->
                destinationsList.clear()
                if (locations != null) {
                    destinationsList.addAll(locations)
                }
            })

        return res
    }

    private fun getDestination() {
        tourSearchViewModel.initDestination()
    }

    private fun setupSearchDestination() {
        val simpleSearchDialogCompat = SimpleSearchDialogCompat(
            context, "Search...",
            "Where do you like to go?", null, destinationsList,
            SearchResultListener { dialog, item, _ ->
                view?.et_search_bar_destination?.text = item.title
                sharedDataViewModel.selectDestination(item)
                dialog.dismiss()
            })
        if (destinationsList.isEmpty())
            simpleSearchDialogCompat.setLoading(true)
        else
            simpleSearchDialogCompat.setLoading(false)

        simpleSearchDialogCompat.show()
    }

    private fun setupFilterPriceBottomSheet() {
        priceFilterBottomSheetFragment = PriceFilterBottomSheetDialogFragment { min, max ->
            val filterPriceTextView = view?.tv_search_price_filter
            val decimalFormat = DecimalFormat("#,###,###")
            val minimum = decimalFormat.format(Integer.valueOf(min))
            val maximum = decimalFormat.format(Integer.valueOf(max))
            filterPriceTextView?.text = "From $minimum to $maximum$"
            if (min != "10" || max != "5500") {
                sharedDataViewModel.selectFromPrice(Integer.parseInt(min))
                sharedDataViewModel.selectToPrice(Integer.parseInt(max))
            } else
                filterPriceTextView?.text = resources.getString(R.string.filter_by_price_optional)
        }
        priceFilterBottomSheetFragment!!.show(fragmentManager!!, "add_price_filter_dialog_fragment")
    }

    private fun setupOnclickListener(res: View) {
        res.tv_search_check_in.setOnClickListener(this)
        res.tv_search_check_out.setOnClickListener(this)
        res.tv_search_check_in_txt.setOnClickListener(this)
        res.tv_search_check_out_txt.setOnClickListener(this)
        res.iv_search_calender_logo1.setOnClickListener(this)
        res.iv_search_calender_logo2.setOnClickListener(this)
        res.iv_search_filter.setOnClickListener(this)
        res.tv_search_price_filter.setOnClickListener(this)
        res.et_search_bar_destination.setOnClickListener(this)
    }

    private fun setupSearchButton(res: View) {
        res.button_search_search.setOnClickListener {
            if (res.et_search_bar_destination.text == "Destination") {
                Toasty.normal(context!!, "Destination can not be empty.").show()
            } else {
                val tripListSearchResultFragment = TripResultFragment().apply {
                    arguments = Bundle().apply {
                        putString(Utils.KEY_BUNDLE_FROM_DATE_CODE, startDateString)
                        putString(Utils.KEY_BUNDLE_TO_DATE_CODE, endDateString)
                        putString(Utils.KEY_BUNDLE_NIGHT_NUM_CODE, diffDays)
                    }
                }
                (activity as MainActivity).showFragment(this@TourSearchFragment, tripListSearchResultFragment, "",false)
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.tv_search_check_in_txt
            || v.id == R.id.tv_search_check_out_txt
            || v.id == R.id.tv_search_check_in
            || v.id == R.id.tv_search_check_out
            || v.id == R.id.iv_search_calender_logo1
            || v.id == R.id.iv_search_calender_logo2
        ) {
            (activity as MainActivity).showFragment(this, DatePickerFragment { arrayList ->
                view?.tv_search_check_in?.text = arrayList[0]
                view?.tv_search_check_out?.text = arrayList[1]
                view?.tv_search_night_num?.text = arrayList[2]
                diffDays = arrayList[2]
                startDateString = arrayList[3]
                endDateString = arrayList[4]
            }, "",true)
        }

        if (v.id == R.id.iv_search_search_city3 || v.id == R.id.et_search_bar_destination) {
            setupSearchDestination()
        }

        if (v.id == R.id.iv_search_filter || v.id == R.id.tv_search_price_filter) {
            setupFilterPriceBottomSheet()
        }
    }

    private fun getDayFormat(calendar: Calendar): String {
        val dayFormat = SimpleDateFormat("E", Locale.getDefault())
        val dayOfWeekNameFrom = dayFormat.format(calendar.time)
        val monthNameFrom = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

        return "$dayOfWeekNameFrom, ${calendar.get(Calendar.DAY_OF_MONTH)} $monthNameFrom"
    }
}