package yoyo.app.android.com.yoyoapp.trip.schedule.request


import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_request.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import yoyo.app.android.com.yoyoapp.DataModels.DateCalender
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.adapter.DateCalenderRecyclerViewAdapter

import java.util.ArrayList


class RequestFragment : Fragment() {

    private val requestViewModel by viewModels<RequstViewModel>()
    private var dateCalenders: ArrayList<DateCalender> = ArrayList()
    private lateinit var adapter: DateCalenderRecyclerViewAdapter
    private var passengerNum = 1
    private var calenderNum = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_request, container, false)

        setupRecyclerView(res)
        res.iv_request_back.setOnClickListener { activity?.onBackPressed() }
        return res
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.iv_request_calender_plus.setOnClickListener { addCalenderNumber() }
        view.iv_request_calender_minus.setOnClickListener { reduceCalenderNumber() }
        view.iv_request_plus.setOnClickListener { addNumber() }
        view.iv_request_minus.setOnClickListener { reduceNumber() }
        val tourId = arguments?.getString("tripId")
        view.button_request_send.setOnClickListener { if (checkingFields()) tourId?.let { it1 -> sendRequest(getJson(), it1) } }
    }

    private fun reduceNumber() {
        if (passengerNum > 1) {
            passengerNum--
            view?.tv_request_num?.text = passengerNum.toString()
        }
        if (passengerNum == 1) {
            view?.iv_request_minus?.setImageDrawable(resources.getDrawable(R.drawable.ic_remove_circle_outline_light_24dp))
        }
    }

    private fun addNumber() {
        if (passengerNum < 9) {
            passengerNum++
            view?.tv_request_num?.text = passengerNum.toString()
        }
        if (passengerNum == 2) {
            appearView(view?.iv_request_minus, view?.tv_request_num)
        }
    }

    private fun setupRecyclerView(res: View) {
        dateCalenders.add(DateCalender())
        adapter = DateCalenderRecyclerViewAdapter(dateCalenders, context!!){ timestamp, position, standardDate ->
            dateCalenders[position].timeStamp = timestamp
            dateCalenders[position].standardDate = standardDate
        }
        res.rv_request_date_calender.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        res.rv_request_date_calender.layoutManager = linearLayoutManager
    }

    private fun reduceCalenderNumber() {
        if (calenderNum > 1) {
            calenderNum--
            view?.tv_request_calender_num?.text = calenderNum.toString()
            dateCalenders.removeAt(dateCalenders.size - 1)
            adapter.notifyDataSetChanged()
        }
        if (calenderNum == 1) {
            view?.iv_request_calender_minus?.setImageDrawable(resources.getDrawable(R.drawable.ic_remove_circle_outline_light_24dp))
        }
    }

    private fun addCalenderNumber() {
        if (calenderNum < 9) {
            calenderNum++
            view?.tv_request_calender_num?.text = calenderNum.toString()
            val dateCalender = DateCalender()
            dateCalenders.add(dateCalender)
            adapter.notifyDataSetChanged()
        }
        if (calenderNum >= 2) {
            appearView(view?.iv_request_calender_minus, view?.tv_request_calender_num)
        }
    }

    private fun checkingFields(): Boolean {
        if (TextUtils.isEmpty(view?.et_request_firstname?.text)) {
            Toasty.error(context!!, getString(R.string.first_name_not_empty)).show()
            return false
        } else if (TextUtils.isEmpty(view?.et_request_lastname?.text)) {
            Toasty.error(context!!, getString(R.string.lastname_not_empty)).show()
            return false
        } else if (TextUtils.isEmpty(view?.et_request_email!!.text)) {
            Toasty.error(context!!, getString(R.string.email_cannot_be_empty)).show()
            return false
        } else if (TextUtils.isEmpty(view?.et_request_phone_number?.text)) {
            Toasty.error(context!!, getString(R.string.phone_number_cannot_be_empty)).show()
            return false
        } else if (!view!!.et_request_email.text.toString().matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$".toRegex())) {
            Toasty.error(context!!, "Your Email Address is incorrect").show()
            return false
        }
        for (i in dateCalenders.indices) {
            if (dateCalenders[i].timeStamp == null) {
                Toasty.error(context!!, "Please select your desire dates").show()
                return false
            }
        }
        return true
    }


    private fun sendRequest(jsonObject: JSONObject, tourId: String) {
        view?.progressBar3?.visibility = View.VISIBLE
        view?.isClickable = false
        requestViewModel.initRequest(tourId, jsonObject)
        requestViewModel.isSuccessful.observe(activity!!, Observer { result ->
            if (result!!) {
                Toasty.normal(context!!, "your Request has sent successfully").show()
                activity!!.onBackPressed()
            } else {
                Toasty.normal(context!!, "failed to send Request").show()
            }
            view?.isClickable = true
            view?.progressBar3?.visibility = View.GONE
        })
    }

    private fun appearView(imageView: ImageView?, textView: TextView?) {
        imageView?.setImageDrawable(resources.getDrawable(R.drawable.ic_remove_circle_outline_black_24dp))
        textView?.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun getJson(): JSONObject {
            val jsonArray = JSONArray()
            for (i in dateCalenders.indices) {
                jsonArray.put(dateCalenders[i].timeStamp)
            }
            val jsonObject = JSONObject()
            try {
                jsonObject.put(
                    "full_name",
                    view?.et_request_firstname?.text.toString() + " " + view?.et_request_lastname?.text.toString()
                )
                jsonObject.put("email", view?.et_request_email?.text.toString())
                jsonObject.put("phone_number", view?.et_request_phone_number?.text.toString())
                jsonObject.put("passengers_count", passengerNum)
                jsonObject.put("dates", jsonArray)

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return jsonObject
    }
}
