package yoyo.app.android.com.yoyoapp.trip.schedule


import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_schedule_trip.view.*
import yoyo.app.android.com.yoyoapp.DataModels.Schedule
import yoyo.app.android.com.yoyoapp.DataModels.ScheduleCalender
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.Utils.UserSharedManager
import yoyo.app.android.com.yoyoapp.trip.adapter.ScheduleCalenderRecyclerviewAddapter
import yoyo.app.android.com.yoyoapp.trip.adapter.ScheduleRecyclerViewAdapter
import yoyo.app.android.com.yoyoapp.trip.schedule.request.RequestFragment

import java.util.ArrayList


class ScheduleTourFragment : Fragment() {
    private val scheduleViewModel by viewModels<ScheduleTourViewModel>()
    private var myCalenders: ArrayList<ScheduleCalender> = ArrayList()
    private var scheduleArrayList: ArrayList<Schedule> = ArrayList()
    private var adapter: ScheduleRecyclerViewAdapter? = null
    private var position = 0
    private lateinit var tourId: String
    private lateinit var tourTitle: String
    private lateinit var tourImage: String
    private lateinit var userSharedManager: UserSharedManager
    private lateinit var toolbar: Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_schedule_trip, container, false)

        receiveBundle()
        init(res)
        Picasso.with(context).load(tourImage).into(res.iv_schedule_trip_image)
        toolbar.background = res.iv_schedule_trip_image.drawable
        res.button_schedule_request_date.setOnClickListener {sendToRequestPage() }
        res.iv_schedule_back.setOnClickListener {activity?.onBackPressed() }
        setupCalenderRecyclerView(res)

        return res
    }

    private fun receiveBundle() {
        arguments?.apply {
            tourId = getString("tourId") ?: ""
            tourTitle = getString("title") ?: ""
            tourImage = getString("tourImage") ?: ""
        }
    }

    private fun init(res: View) {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        res.rv_schedule_result?.layoutManager = linearLayoutManager
        toolbar = res.tb_schedule
        userSharedManager = UserSharedManager(context)
    }

    private fun sendToRequestPage() {
        if (userSharedManager.token.isEmpty()) {
            (activity as MainActivity).popUpSignInSignUpActivity()
        } else {
            val bundle = Bundle()
            bundle.putString("tourId", tourId)
            val requestFragment = RequestFragment()
            requestFragment.arguments = bundle
            (activity as MainActivity).showFragment(this, requestFragment, "", false)
        }
    }

    private fun getSchedules(res: View,startDate: Long, endDate: Long) {
        scheduleViewModel.initSchedule(tourId, startDate, endDate)
        scheduleViewModel.scheduleMutableLiveData.observe(activity!!, Observer { schedules ->
            if (schedules != null) {
                scheduleArrayList.clear()
                scheduleArrayList.addAll(schedules)

                if (adapter == null) {
                    adapter = ScheduleRecyclerViewAdapter(
                        scheduleArrayList,
                        tourTitle,
                        this,
                        context,
                        ScheduleRecyclerViewAdapter.OnItemSelected { })
                    res.rv_schedule_result?.adapter = adapter
                } else
                    adapter?.notifyDataSetChanged()

                res.shimmer_recycler_view?.hideShimmerAdapter()
            }
        })
    }

    private fun setupCalenderRecyclerView(res: View) {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        // get a list of dates
        myCalenders =
            scheduleViewModel.getCalenderData { startDate, endDate -> getSchedules(res,startDate, endDate) }

        val adapter = ScheduleCalenderRecyclerviewAddapter(
            myCalenders,
            context,
            position,
            ScheduleCalenderRecyclerviewAddapter.OnItemClicked { startDate, endDate, newPosition ->
                scheduleViewModel.initSchedule(tourId, startDate, endDate)
                if (newPosition > position)
                    linearLayoutManager.scrollToPosition(newPosition + 2)
                else
                    linearLayoutManager.scrollToPosition(newPosition - 2)
                position = newPosition
            })

        res.rv_schedule_calender?.layoutManager = linearLayoutManager
        res.rv_schedule_calender?.adapter = adapter
    }


}
