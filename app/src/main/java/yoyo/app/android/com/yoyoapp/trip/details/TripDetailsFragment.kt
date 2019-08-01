package yoyo.app.android.com.yoyoapp.trip.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_trip_details.view.*
import kotlinx.android.synthetic.main.layout_trip_details.view.*
import yoyo.app.android.com.yoyoapp.Addapters.AdapterImageSlider
import yoyo.app.android.com.yoyoapp.GoogleMapActivity
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.adapter.DayPlanRecyclerviewAddaptor
import yoyo.app.android.com.yoyoapp.trip.schedule.ScheduleTourFragment


import java.util.ArrayList

class TripDetailsFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var layout_dots: LinearLayout
    private lateinit var tripId: String
    private lateinit var adapterImageSlider: AdapterImageSlider
    private lateinit var dayPlans: ArrayList<String>
    private lateinit var fromLatlng: LatLng
    private lateinit var toLatlng: LatLng
    private lateinit var tourImage: String
    private lateinit var tripTitle: String
    private lateinit var titleTextview: TextView
    private lateinit var dayPlanTextview: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_trip_details, container, false)

        init(res)
        setupViews(res)
        setupToolbar(res)
        res.fb_tripdetails.setOnClickListener { v -> sendToGoogleMap() }
        res.button_tripdetails_book.setOnClickListener { v -> sendToSchedulePage() }

        return res
    }

    private fun sendToSchedulePage() {
        val scheduleTripFragment = ScheduleTourFragment()
        val bundle = Bundle()
        bundle.putString("tourId", tripId)
        bundle.putString("tourImage", tourImage)
        bundle.putString("title", tripTitle)
        scheduleTripFragment.arguments = bundle
        (activity as MainActivity).showFragment(this, scheduleTripFragment, "", false)
    }

    private fun setupViews(res: View) {
        arguments?.apply {

            tripId = getString("tripId")
            res.tv_tripdetails_daynightnum.text = getInt("days").toString() + " Days " + getInt("nights") + " Nights"
            res.tv_tripdetails_name.text = getString("leaderName")
            titleTextview.text = getString("tourName") + " • " + getInt("passengersCount") + " purchased"
            res.tv_tripdetails_title2.text = getString("title")
            tripTitle = getString("title")
            res.tv_tripdetails_tourleader_language.text = getString("language")
            res.tv_tripdetails_location_from!!.text = getString("locationTitleFrom")
            res.tv_tripdetails_location_to.text = getString("locationTitleTo")
            fromLatlng = LatLng(getDouble("fromLat"), getDouble("fromLong"))
            toLatlng = LatLng(getDouble("toLat"), getDouble("toLong"))
            Picasso.with(context).load(getString("leaderPicture"))
                .placeholder(context!!.getDrawable(R.drawable.avatar)).into(res.iv_tripdetails_tourleader)
            // summary
            res.tv_tripdetails_desc.text = getString("summary")
            // transports
            val transports = StringBuilder()
            for (transport in getStringArrayList("transportation")!!) {
                transports.append(transport).append(" ")
            }
            res.tv_tripdetails_transport.text = transports.toString()
            // type
            val types = StringBuilder()
            for (type in getStringArrayList("categories")!!) {
                types.append(type).append("\n")
            }
            res.tv_tripdetails_type.text = types.toString()
            // attractions
            val attractions = StringBuilder()
            for (attraction in getStringArrayList("attractions")!!) {
                attractions.append(attraction).append("\n")
            }
            res.tv_tripdetails_attractions.text = attractions.toString()
            // rules
            val rules = StringBuilder()
            for (rule in getStringArrayList("rules")!!) {
                rules.append(rule).append("\n")
            }
            res.tv_tripdetails_rule.text = rules.toString()
            // meals
            val meals = StringBuilder()
            for (meal in getStringArrayList("meals")!!) {
                meals.append(meal).append("\n")
            }
            res.tv_tripdetails_meals.text = meals.toString()
            // images
            val images = ArrayList<String>()
            for (image in getStringArrayList("gallery")!!) {
                images.add(image)
            }
            tourImage = getStringArrayList("gallery")!![0]
            initComponent(res,images)
            // day plan
            dayPlans = getStringArrayList("itinerary")
            dayPlanTextview.text = getStringArrayList("itinerary")!![0]
            // route
            res.tv_tripdetails_route.text = getString("route")
            setupDayRecyclerview(res)
        }
    }

    private fun init(res: View) {
        dayPlans = ArrayList()
        dayPlanTextview = res.tv_tripdetails_day_plan
        titleTextview = res.tv_tripdetails_title
    }

    private fun sendToGoogleMap() {
        val bundle = Bundle()
        bundle.putDouble("lat1", fromLatlng.latitude)
        bundle.putDouble("lat2", toLatlng.latitude)
        bundle.putDouble("long1", fromLatlng.longitude)
        bundle.putDouble("long2", toLatlng.longitude)
        val intent = Intent(context, GoogleMapActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setupDayRecyclerview(res: View) {
        val recyclerView = res.rv_tripdetails_day_plan
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        val addaptor = DayPlanRecyclerviewAddaptor(context, dayPlans) { dayplan -> dayPlanTextview.text = dayplan }
        recyclerView.adapter = addaptor
    }

    private fun setupToolbar(res: View) {
        val toolbar = res.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { v -> activity!!.onBackPressed() }

        val collapsingToolbar = res.collapsing_toolbar

        val appBarLayout = res.appbar
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

    private fun initComponent(res: View, images: ArrayList<String>) {
        layout_dots = res.layout_dots
        viewPager = res.pager
        adapterImageSlider = AdapterImageSlider(activity, images)

        val items = ArrayList<String>()
        for (url in images) {
            items.add(url)
        }

        adapterImageSlider.setItems(items)
        viewPager.adapter = adapterImageSlider

        // displaying selected image first
        viewPager.currentItem = 0
        addBottomDots(layout_dots, adapterImageSlider.count, 0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(pos: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(pos: Int) {
                addBottomDots(layout_dots, adapterImageSlider.count, pos)
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
            dots[i]?.setLayoutParams(params)
            dots[i]?.setImageResource(R.drawable.shape_circle_outline)
            layout_dots.addView(dots[i])
        }

        if (dots.size > 0) {
            dots[current]?.setImageResource(R.drawable.shape_circle)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            activity!!.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }



}
