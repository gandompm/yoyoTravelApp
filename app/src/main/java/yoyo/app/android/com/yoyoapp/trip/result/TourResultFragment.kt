package yoyo.app.android.com.yoyoapp.trip.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import kotlinx.android.synthetic.main.fragment_trip_result.view.*
import uk.co.imallan.jellyrefresh.JellyRefreshLayout
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.SharedDataViewModel
import yoyo.app.android.com.yoyoapp.trip.Utils.InfiniteScrollProvider
import yoyo.app.android.com.yoyoapp.trip.adapter.FoldingCellRecyclerviewAdapter
import yoyo.app.android.com.yoyoapp.trip.dialog.TripFilterDialogFragment
import yoyo.app.android.com.yoyoapp.Utils

import java.util.ArrayList

class TourResultFragment : Fragment(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerRecycler: ShimmerRecyclerView
    private lateinit var adapter: FoldingCellRecyclerviewAdapter
    private lateinit var tourResultViewModel: TourResultViewModel
    private lateinit var tripQuery: TripQuery
    private var page = 1
    private var isMoreDataAvailable = true
    private lateinit var jellyRefreshLayout: JellyRefreshLayout
    private lateinit var categoryCodes: ArrayList<String>
    private var sharedDataViewModel: SharedDataViewModel? = null
    private var reserveType: String = "FLEXIBLE"
    private var fromPrice = 0
    private var toPrice = 20000000
    private var minDuration: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_trip_result, container, false)

        init(res)
        observingSharedData()
        setupRecyclerview()
        setupQuery()
        setupFloatingActionButton(res.fbutton_hotellistsearchresult)
        getTrips()
        setupToggleButton(res.toggleSwitch_trip_search)
        refresh()

        return res
    }

    private fun observingSharedData() {
        sharedDataViewModel!!.fromPrice.observe(this, Observer{ price -> fromPrice = price!! })
        sharedDataViewModel!!.toPrice.observe(this, Observer{ price -> toPrice = price!! })
        sharedDataViewModel!!.categories.observe(this, Observer{ categories ->
            for (category in categories) {
                category.code?.let { categoryCodes.add(it) }
            }
        })
        sharedDataViewModel!!.minDuration.observe(this, Observer{ minDuration -> this.minDuration = minDuration!! })
    }

    private fun refresh() {
        jellyRefreshLayout.setPullToRefreshListener {
            jellyRefreshLayout.isRefreshing = true
            isMoreDataAvailable = true
            page = 1
            adapter = FoldingCellRecyclerviewAdapter(context, this@TourResultFragment)
            recyclerView.adapter = adapter
            setupQuery()
            getTrips()
        }
        val loadingView = LayoutInflater.from(context).inflate(R.layout.view_loading, null)
        jellyRefreshLayout.setLoadingView(loadingView)
        jellyRefreshLayout.setOnClickListener { jellyRefreshLayout.isRefreshing = false }
    }


    private fun setupToggleButton(toggleSwitch: ToggleSwitch) {
        toggleSwitch.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(i: Int) {
                page = 1
                isMoreDataAvailable = true
                adapter = FoldingCellRecyclerviewAdapter(context, this@TourResultFragment)
                recyclerView.adapter = adapter
                if (i == 0) {
                    reserveType = "FIXED"
                    setupQuery()
                    shimmerRecycler.showShimmerAdapter()
                    getTrips()
                } else {
                    reserveType = "FLEXIBLE"
                    setupQuery()
                    shimmerRecycler.showShimmerAdapter()
                    getTrips()
                }
            }
        }
    }

    private fun setupRecyclerview() {
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = FoldingCellRecyclerviewAdapter(activity, this)
        recyclerView.adapter = adapter
        val infiniteScrollProvider = InfiniteScrollProvider()
        infiniteScrollProvider.attach(recyclerView) {
            if (isMoreDataAvailable) {
                tourResultViewModel.initTripList(page, tripQuery)
                tourResultViewModel.getTripList()!!.observe(activity!!, Observer{ trips ->
                    if (trips != null && trips.isNotEmpty()) {
                        Toast.makeText(activity, "load more", Toast.LENGTH_SHORT).show()
                        adapter.addTrips(trips)
                        page++
                    } else
                        isMoreDataAvailable = false
                })
            }
        }
    }


    private fun getTrips() {
        tourResultViewModel = ViewModelProviders.of(activity!!).get(TourResultViewModel::class.java)
        tourResultViewModel.initTripList(page, tripQuery)
        tourResultViewModel.getTripList()!!.observe(activity!!, Observer { trips ->
            if (trips != null) {
                if (trips.isEmpty()) {
                    Toast.makeText(context, "We couldn't find any Tour", Toast.LENGTH_SHORT).show()
                } else {
                    setupSnackBar(trips[0].resultsSize)
                    adapter.addTrips(trips)
                    page++
                }
                shimmerRecycler.hideShimmerAdapter()
                jellyRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun setupSnackBar(resultsSize: Int) {
        // TODO: 5/30/2019 fixing showing sum of results
        val snackbar =
                Snackbar.make(view!!, resultsSize.toString() + " " + getString(R.string.results), Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
        val sbView = snackbar.view

        sbView.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        val textView: TextView = sbView.findViewById(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
        snackbar.show()
    }

    private fun setupQuery() {
        tripQuery = TripQuery()
        val bundle = arguments
        if (bundle != null) {
            val searchText = bundle.getString(Utils.KEY_BUNDLE_SEARCH_STRING_CODE)
            val startDate = bundle.getString(Utils.KEY_BUNDLE_FROM_DATE_CODE)
            val endDate = bundle.getString(Utils.KEY_BUNDLE_TO_DATE_CODE)
            val duration = bundle.getString(Utils.KEY_BUNDLE_NIGHT_NUM_CODE)
        }
        tripQuery.type = reserveType
        tripQuery.fromTime = sharedDataViewModel!!.fromTime.value
        tripQuery.toTime = sharedDataViewModel!!.toTime.value
        tripQuery.minDuration = minDuration
        tripQuery.fromPrice = fromPrice
        tripQuery.toPrice = toPrice
        tripQuery.destination = sharedDataViewModel!!.destination.value!!.code
        tripQuery.categories = categoryCodes
    }

    private fun init(res: View) {
        jellyRefreshLayout = res.jelly_refresh_home
        shimmerRecycler = res.shimmer_recycler_view
        recyclerView = res.mainrv
        res.toggleSwitch_trip_search.setCheckedPosition(1)
        res.tv_trip_search_back.setOnClickListener(this)
        res.iv_trip_search_back.setOnClickListener(this)
        sharedDataViewModel = ViewModelProviders.of(activity!!).get(SharedDataViewModel::class.java)
        categoryCodes = ArrayList()
    }

    private fun setupFloatingActionButton(floatingActionButton: FloatingActionButton) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && floatingActionButton.visibility == View.VISIBLE) {
                    floatingActionButton.hide()
                } else if (dy < 0 && floatingActionButton.visibility != View.VISIBLE) {
                    floatingActionButton.show()
                }
            }
        })
        floatingActionButton.setOnClickListener {
            val tripFilterDialogBottomSheetDialogFragment = TripFilterDialogFragment(Consumer {
                if (it == true) {
                    isMoreDataAvailable = true
                    page = 1
                    adapter = FoldingCellRecyclerviewAdapter(context, this@TourResultFragment)
                    recyclerView.adapter = adapter
                    shimmerRecycler.showShimmerAdapter()
                    setupQuery()
                    getTrips()
                }
            })
            tripFilterDialogBottomSheetDialogFragment.show(fragmentManager!!, "add_price_filter_dialog_fragment")
        }
    }



    override fun onClick(v: View) {

        if (v.id == R.id.iv_trip_search_back || v.id == R.id.tv_trip_search_back) {
            setupBackButton()
        }
    }

    private fun setupBackButton() {
        sharedDataViewModel!!.resetFilters()
        activity!!.onBackPressed()
    }


}

