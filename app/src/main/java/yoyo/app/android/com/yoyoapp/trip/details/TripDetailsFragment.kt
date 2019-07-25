package yoyo.app.android.com.yoyoapp.trip.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import yoyo.app.android.com.yoyoapp.Addapters.AdapterImageSlider
import yoyo.app.android.com.yoyoapp.GoogleMapActivity
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.adapter.DayPlanRecyclerviewAddaptor
import yoyo.app.android.com.yoyoapp.trip.schedule.ScheduleTripFragment


import java.util.ArrayList

class TripDetailsFragment : Fragment() {
    private var viewPager: ViewPager? = null
    private var layout_dots: LinearLayout? = null
    private var tripId: String? = null
    private var adapterImageSlider: AdapterImageSlider? = null
    private var floatingActionButton: FloatingActionButton? = null
    private var dayPlans: ArrayList<String>? = null
    private var fromLatlng: LatLng? = null
    private var toLatlng: LatLng? = null
    private var tripDetailsButton: Button? = null
    private var tourLeaderImageview: CircleImageView? = null
    private var tourImage: String? = null
    private var tripTitle: String? = null
    private var dayNightNumTextview: TextView? = null
    private var priceTextview: TextView? = null
    private var titleTextview: TextView? = null
    private var title2Textview: TextView? = null
    private var nameTourLeader: TextView? = null
    private var familyNameLeader: TextView? = null
    private var locationFromTextview: TextView? = null
    private var locationToTextview: TextView? = null
    private var attractionsTextview: TextView? = null
    private var rulesTextview: TextView? = null
    private var transportTextview: TextView? = null
    private var tourLeaderLanguageTextview: TextView? = null
    private var typeTextview: TextView? = null
    private var dayPlanTextview: TextView? = null
    private var mealsTextview: TextView? = null
    private val passengerCountTextview: TextView? = null
    private var itineraryTextview: TextView? = null
    private var descriptionTextview: TextView? = null
    private var routeTextview: TextView? = null

    private var view: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_trip_details, container, false)

        init()
        setupViews()
        setupToolbar()
        floatingActionButton!!.setOnClickListener { v -> sendToGoogleMap() }
        tripDetailsButton!!.setOnClickListener { v -> sendToSchedulePage() }



        return view
    }

    private fun sendToSchedulePage() {
        val scheduleTripFragment = ScheduleTripFragment()
        val bundle = Bundle()
        bundle.putString("tripId", tripId)
        bundle.putString("tourImage", tourImage)
        bundle.putString("title", tripTitle)
        scheduleTripFragment.arguments = bundle
        (activity as MainActivity).showFragment(this, scheduleTripFragment, "", false)
    }

    private fun setupViews() {
        val bundle = arguments

        tripId = bundle!!.getString("tripId")
        dayNightNumTextview!!.text = bundle.getInt("days").toString() + " Days " + bundle.getInt("nights") + " Nights"
        nameTourLeader!!.text = bundle.getString("leaderName")
        titleTextview!!.text = bundle.getString("tourName") + " • " + bundle.getInt("passengersCount") + " purchased"
        title2Textview!!.text = bundle.getString("title")
        tripTitle = bundle.getString("title")
        tourLeaderLanguageTextview!!.text = bundle.getString("language")
        locationFromTextview!!.text = bundle.getString("locationTitleFrom")
        locationToTextview!!.text = bundle.getString("locationTitleTo")
        fromLatlng = LatLng(bundle.getDouble("fromLat"), bundle.getDouble("fromLong"))
        toLatlng = LatLng(bundle.getDouble("toLat"), bundle.getDouble("toLong"))
        Picasso.with(context).load(bundle.getString("leaderPicture"))
            .placeholder(context!!.getDrawable(R.drawable.avatar)).into(tourLeaderImageview)
        // summary
        descriptionTextview!!.text = bundle.getString("summary")
        // transports
        val transports = StringBuilder()
        for (transport in bundle.getStringArrayList("transportation")!!) {
            transports.append(transport).append(" ")
        }
        transportTextview!!.text = transports.toString()
        // type
        val types = StringBuilder()
        for (type in bundle.getStringArrayList("categories")!!) {
            types.append(type).append("\n")
        }
        typeTextview!!.text = types.toString()
        // attractions
        val attractions = StringBuilder()
        for (attraction in bundle.getStringArrayList("attractions")!!) {
            attractions.append(attraction).append("\n")
        }
        attractionsTextview!!.text = attractions.toString()
        // rules
        val rules = StringBuilder()
        for (rule in bundle.getStringArrayList("rules")!!) {
            rules.append(rule).append("\n")
        }
        rulesTextview!!.text = rules.toString()
        // meals
        val meals = StringBuilder()
        for (meal in bundle.getStringArrayList("meals")!!) {
            meals.append(meal).append("\n")
        }
        mealsTextview!!.text = meals.toString()
        // images
        val images = ArrayList<String>()
        for (image in bundle.getStringArrayList("gallery")!!) {
            images.add(image)
        }
        tourImage = bundle.getStringArrayList("gallery")!![0]
        initComponent(images)
        // day plan
        dayPlans = bundle.getStringArrayList("itinerary")
        dayPlanTextview!!.text = bundle.getStringArrayList("itinerary")!![0]
        // route
        routeTextview!!.text = bundle.getString("route")
        setupDayRecyclerview()
    }

    private fun init() {
        dayPlans = ArrayList()
        tripDetailsButton = view!!.findViewById(R.id.button_tripdetails_book)
        dayPlanTextview = view!!.findViewById(R.id.tv_tripdetails_day_plan)
        floatingActionButton = view!!.findViewById(R.id.fb_tripdetails)
        dayNightNumTextview = view!!.findViewById(R.id.tv_tripdetails_daynightnum)
        priceTextview = view!!.findViewById(R.id.tv_tripdetails_price)
        titleTextview = view!!.findViewById(R.id.tv_tripdetails_title)
        title2Textview = view!!.findViewById(R.id.tv_tripdetails_title2)
        nameTourLeader = view!!.findViewById(R.id.tv_tripdetails_name)
        familyNameLeader = view!!.findViewById(R.id.tv_tripdetails_tourleader_familyname)
        locationFromTextview = view!!.findViewById(R.id.tv_tripdetails_location_from)
        locationToTextview = view!!.findViewById(R.id.tv_tripdetails_location_to)
        routeTextview = view!!.findViewById(R.id.tv_tripdetails_route)
        attractionsTextview = view!!.findViewById(R.id.tv_tripdetails_attractions)
        transportTextview = view!!.findViewById(R.id.tv_tripdetails_transport)
        rulesTextview = view!!.findViewById(R.id.tv_tripdetails_rule)
        tourLeaderImageview = view!!.findViewById(R.id.iv_tripdetails_tourleader)
        tourLeaderLanguageTextview = view!!.findViewById(R.id.tv_tripdetails_tourleader_language)
        typeTextview = view!!.findViewById(R.id.tv_tripdetails_type)
        mealsTextview = view!!.findViewById(R.id.tv_tripdetails_meals)
        //        passengerCountTextview = view.findViewById(R.id.tv_tripdetails_people_num);
        itineraryTextview = view!!.findViewById(R.id.tv_tripdetails_itnarary)
        descriptionTextview = view!!.findViewById(R.id.tv_tripdetails_desc)
    }

    private fun sendToGoogleMap() {
        val bundle = Bundle()
        bundle.putDouble("lat1", fromLatlng!!.latitude)
        bundle.putDouble("lat2", toLatlng!!.latitude)
        bundle.putDouble("long1", fromLatlng!!.longitude)
        bundle.putDouble("long2", toLatlng!!.longitude)
        val intent = Intent(context, GoogleMapActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setupDayRecyclerview() {
        val recyclerView = view!!.findViewById<RecyclerView>(R.id.rv_tripdetails_day_plan)
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val addaptor = DayPlanRecyclerviewAddaptor(context, dayPlans) { dayplan -> dayPlanTextview!!.text = dayplan }
        recyclerView.adapter = addaptor
    }

    private fun setupToolbar() {
        val toolbar = view!!.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { v -> activity!!.onBackPressed() }

        val collapsingToolbar = view!!.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)

        val appBarLayout = view!!.findViewById<AppBarLayout>(R.id.appbar)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = titleTextview!!.text.toString()
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title =
                        " "//careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }
        })
    }

    private fun initComponent(images: ArrayList<String>) {
        layout_dots = view!!.findViewById(R.id.layout_dots)
        viewPager = view!!.findViewById(R.id.pager)
        adapterImageSlider = AdapterImageSlider(activity, images)

        val items = ArrayList<String>()
        for (url in images) {
            items.add(url)
        }

        adapterImageSlider!!.setItems(items)
        viewPager!!.adapter = adapterImageSlider

        // displaying selected image first
        viewPager!!.currentItem = 0
        addBottomDots(layout_dots!!, adapterImageSlider!!.count, 0)

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(pos: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(pos: Int) {
                addBottomDots(layout_dots!!, adapterImageSlider!!.count, pos)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun addBottomDots(layout_dots: LinearLayout, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)

        layout_dots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(context)
            val width_height = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i].setLayoutParams(params)
            dots[i].setImageResource(R.drawable.shape_circle_outline)
            layout_dots.addView(dots[i])
        }

        if (dots.size > 0) {
            dots[current].setImageResource(R.drawable.shape_circle)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            activity!!.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val TAG = "TripDetailsFragment"
        val EXTRA_NAME = "cheese_name"
    }

}
