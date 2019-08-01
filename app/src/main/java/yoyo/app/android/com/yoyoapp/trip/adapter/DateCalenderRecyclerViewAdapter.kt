package yoyo.app.android.com.yoyoapp.trip.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_date_calender.view.*
import yoyo.app.android.com.yoyoapp.DataModels.DateCalender
import yoyo.app.android.com.yoyoapp.trip.Utils.DateCalenderSetup

class DateCalenderRecyclerViewAdapter(var timeStamps: ArrayList<DateCalender>, val context: Context,val f: (Long,Int,String) -> Unit )
    : RecyclerView.Adapter<DateCalenderRecyclerViewAdapter.DateCalenderViewHolder>() {

    private val dateOfBirthListner: DatePickerDialog.OnDateSetListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateCalenderViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(yoyo.app.android.com.yoyoapp.R.layout.item_date_calender,parent,false)
        return DateCalenderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timeStamps.size
    }

    override fun onBindViewHolder(holder: DateCalenderViewHolder, position: Int) {
        holder.bindView(timeStamps[position])
        holder.itemView.setOnClickListener{
            DateCalenderSetup(context, dateOfBirthListner, DateCalenderSetup.Function { timestamp, standardDate ->
                run {
                    holder.itemView.tv_date_calender.text = standardDate
                    f(timestamp, position, standardDate)
                }
            })
        }
    }


    class DateCalenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(dateCalender: DateCalender) {
            itemView.tv_date_calender.text = dateCalender.standardDate
        }
    }
}