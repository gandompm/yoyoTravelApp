package yoyo.app.android.com.yoyoapp.trip.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import uk.co.imallan.jellyrefresh.JellyRefreshLayout
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.DataModels.Trip
import yoyo.app.android.com.yoyoapp.DataModels.TripQuery
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.SharedDataViewModel
import yoyo.app.android.com.yoyoapp.trip.Utils.InfiniteScrollProvider
import yoyo.app.android.com.yoyoapp.trip.adapter.FoldingCellRecyclerviewAdapter
import yoyo.app.android.com.yoyoapp.trip.dialog.TripFilterDialogFragment
import yoyo.app.android.com.yoyoapp.Utils

import java.util.ArrayList

class TourResultFragment : Fragment(), View.OnClickListener {

    private var floatingActionButton: FloatingActionButton? = null
    private var recyclerView: RecyclerView? = null
    private var shimmerRecycler: ShimmerRecyclerView? = null
    private var adapter: FoldingCellRecyclerviewAdapter? = null
    private var tourResultViewModel: TourResultViewModel? = null
    private var tripQuery: TripQuery? = null
    private var page = 1
    private var view: View? = null
    private var isMoreDataAvailable = true
    private var tripTypeToggleSwitch: ToggleSwitch? = null
    private var jellyRefreshLayout: JellyRefreshLayout? = null
    private var backImageView: ImageView? = null
    private var backTextView: TextView? = null
    private var categoryCodes: ArrayList<String>? = null
    private var sharedDataViewModel: SharedDataViewModel? = null

    private var fromPrice = 0
    private var toPrice = 20000000
    private var minDuration: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_trip_result, container, false)

        init()
        setupRecyclerview()
        setupQuery("FLEXIBLE")
        setupFloatingActionButton()
        getTrips()
        setupToggleButton()
        setupOnclickListener()
        refresh()

        return view
    }

    private fun refresh() {
        jellyRefreshLayout!!.setPullToRefreshListener { view ->
            jellyRefreshLayout!!.isRefreshing = true
            getTrips()
        }
        val loadingView = LayoutInflater.from(context).inflate(R.layout.view_loading, null)
        jellyRefreshLayout!!.setLoadingView(loadingView)
        jellyRefreshLayout!!.setOnClickListener { jellyRefreshLayout!!.isRefreshing = false }
    }


    private fun setupToggleButton() {
        tripTypeToggleSwitch!!.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(i: Int) {
                page = 1
                isMoreDataAvailable = true
                adapter = FoldingCellRecyclerviewAdapter(context, this@TourResultFragment)
                recyclerView!!.adapter = adapter
                if (i == 0) {
                    setupQuery("FIXED")
                    shimmerRecycler!!.showShimmerAdapter()
                    getTrips()
                } else {
                    setupQuery("FLEXIBLE")
                    shimmerRecycler!!.showShimmerAdapter()
                    getTrips()
                }
            }
        }
    }

    private fun setupRecyclerview() {
        recyclerView = view!!.findViewById(R.id.mainrv)
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView!!.layoutManager = linearLayoutManager
        adapter = FoldingCellRecyclerviewAdapter(context, this)
        recyclerView!!.adapter = adapter
        val infiniteScrollProvider = InfiniteScrollProvider()
        infiniteScrollProvider.attach(recyclerView) {
            if (isMoreDataAvailable) {
                tourResultViewModel!!.initTripList(page, tripQuery!!)
                tourResultViewModel!!.getTripList()!!.observe(activity!!, { trips ->
                    if (trips != null && trips!!.size > 0) {
                        Toast.makeText(context, "load more", Toast.LENGTH_SHORT).show()
                        adapter!!.addTrips(trips)
                        page++
                    } else
                        isMoreDataAvailable = false
                })
            }
        }
    }


    private fun getTrips() {
        tourResultViewModel = ViewModelProviders.of(activity!!).get(TourResultViewModel::class.java)
        tourResultViewModel!!.initTripList(page, tripQuery!!)
        tourResultViewModel!!.getTripList()!!.observe(activity!!, Observer { trips ->
            if (trips != null) {
                if (trips.size == 0) {
                    Toast.makeText(context, "We couldn't find any Tour", Toast.LENGTH_SHORT).show()
                } else {
                    setupSnackBar(trips[0].resultsSize)
                    adapter!!.addTrips(trips)
                    page++
                }
                shimmerRecycler!!.hideShimmerAdapter()
                jellyRefreshLayout!!.isRefreshing = false
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
        val textView = sbView.findViewById(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(activity!!, R.color.white))
        snackbar.show()
    }

    private fun setupQuery(reserveType: String) {
        tripQuery = TripQuery()
        val bundle = arguments
        if (bundle != null) {
            val searchText = bundle.getString(Utils.KEY_BUNDLE_SEARCH_STRING_CODE)
            val startDate = bundle.getString(Utils.KEY_BUNDLE_FROM_DATE_CODE)
            val endDate = bundle.getString(Utils.KEY_BUNDLE_TO_DATE_CODE)
            val duration = bundle.getString(Utils.KEY_BUNDLE_NIGHT_NUM_CODE)
        }
        tripQuery!!.type = reserveType
        tripQuery!!.fromTime = sharedDataViewModel!!.fromTime.value
        tripQuery!!.toTime = sharedDataViewModel!!.toTime.value
        tripQuery!!.minDuration = minDuration
        tripQuery!!.fromPrice = fromPrice
        tripQuery!!.toPrice = toPrice
        tripQuery!!.destination = sharedDataViewModel!!.destination.value!!.code
        tripQuery!!.categories = categoryCodes
    }

    private fun init() {
        jellyRefreshLayout = view!!.findViewById(R.id.jelly_refresh_home)
        floatingActionButton = view!!.findViewById(R.id.fbutton_hotellistsearchresult)
        shimmerRecycler = view!!.findViewById(R.id.shimmer_recycler_view)
        backImageView = view!!.findViewById(R.id.iv_trip_search_back)
        backTextView = view!!.findViewById(R.id.tv_trip_search_back)
        tripTypeToggleSwitch = view!!.findViewById(R.id.toggleSwitch_trip_search)
        tripTypeToggleSwitch!!.setCheckedPosition(1)
        sharedDataViewModel = ViewModelProviders.of(activity!!).get(SharedDataViewModel::class.java)
        categoryCodes = ArrayList()

        sharedDataViewModel!!.fromPrice.observe(this, { price -> fromPrice = price!! })
        sharedDataViewModel!!.toPrice.observe(this, { price -> toPrice = price!! })
        sharedDataViewModel!!.categories.observe(this, { categories ->
            for (category in categories) {
                categoryCodes!!.add(category.code)
            }
        })
        sharedDataViewModel!!.minDuration.observe(this, { minDuration -> this.minDuration = minDuration!! })
        sharedDataViewModel!!.hasFiltersChanged.observe(this, { isChanged ->
            if (isChanged!!) {
                isMoreDataAvailable = true
                page = 1
                adapter = FoldingCellRecyclerviewAdapter(context, this@TourResultFragment)
                recyclerView!!.adapter = adapter
                shimmerRecycler!!.showShimmerAdapter()
                setupQuery("FLEXIBLE")
                getTrips()
            }
        }
        )
    }

    private fun setupFloatingActionButton() {

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && floatingActionButton!!.visibility == View.VISIBLE) {
                    floatingActionButton!!.hide()
                } else if (dy < 0 && floatingActionButton!!.visibility != View.VISIBLE) {
                    floatingActionButton!!.show()
                }
            }
        })

        floatingActionButton!!.setOnClickListener {
            val tripFilterDialogBottomSheetDialogFragment = TripFilterDialogFragment.newInstance()
            tripFilterDialogBottomSheetDialogFragment.show(fragmentManager!!, "add_price_filter_dialog_fragment")
        }
    }

    private fun setupOnclickListener() {
        backTextView!!.setOnClickListener(this)
        backImageView!!.setOnClickListener(this)
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

