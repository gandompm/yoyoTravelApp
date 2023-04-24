package yoyo.app.android.com.yoyoapp.trip.booking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_booking_second2.view.*

import yoyo.app.android.com.yoyoapp.FragmentTransaction.BaseFragment
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.trip.dialog.ExitDialogFragment

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BookingSecondFragment : BaseFragment() {

    private var travellerNum: Int = 0
    private var price: Double = 0.toDouble()
    private var url: String? = null
    private var name: String? = null
    private var date: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_booking_second2, container, false)

        getBundle()
        calculatePrice()
        setupViews(res)
        res.button_booking_pay.setOnClickListener{ setupPay()}
        res.iv_sign_out_back.setOnClickListener{setupExit()}

        return res
    }

    private fun setupExit() {
        val exitDialogFragment = ExitDialogFragment()
        fragmentManager?.let { exitDialogFragment.show(it, "exit") }
    }

    private fun setupPay() {
        view?.progressbar_booking_book?.visibility = View.VISIBLE
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
        view?.progressbar_booking_book?.visibility = View.GONE
    }

    private fun setupViews(res: View) {
        res.tv_booking_second_total_price.text = price.toString()
        res.tv_booking_second_customer_name.text = name
        res.tv_booking_second_quantity.text = travellerNum.toString()
        res.tv_booking_second_order_date.text = date
    }

    private fun calculatePrice() {
        price *= travellerNum
        date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun getBundle() {
        arguments?.apply {
            travellerNum = getInt("travellerNumber")
            price = getDouble("price")
            url =  getString("url")
            name = getString("name")
        }

    }

}
