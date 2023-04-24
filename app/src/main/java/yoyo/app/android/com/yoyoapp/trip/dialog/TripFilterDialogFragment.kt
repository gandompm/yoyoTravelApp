package yoyo.app.android.com.yoyoapp.trip.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.bottom_sheet_filter_trip.view.*
import yoyo.app.android.com.yoyoapp.DataModels.Category
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.SharedDataViewModel
import yoyo.app.android.com.yoyoapp.trip.adapter.CategoryRecyclerviewAddapter
import yoyo.app.android.com.yoyoapp.trip.search.TourSearchViewModel
import java.text.DecimalFormat
import java.util.ArrayList

class TripFilterDialogFragment(private val applyConsumer: Consumer<Boolean>) : BottomSheetDialogFragment() {

    private val tourSearchViewModel by viewModels<TourSearchViewModel>()
    private val sharedDataViewModel by activityViewModels<SharedDataViewModel>()
    private lateinit var categoryList: ArrayList<Category>
    private var adapter: CategoryRecyclerviewAddapter? = null
    private lateinit var selectedCategories: ArrayList<Category>
    private lateinit var minimum: String
    private lateinit var maximum: String
    private var duration = 6

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.bottom_sheet_filter_trip, container, false)

        init(res)
        setupNumberPicker(res)
        setupPriceRangeBar(res)
        getCategories(res)
        res.button_filtertrip_apply.setOnClickListener { setupApplyButton()}
        res.button_filtertrip_cancel.setOnClickListener { dismiss()}


        return res
    }


    private fun setupNumberPicker(res: View) {
        res.number_picker.orientation = NumberPicker.HORIZONTAL
        val data = arrayOf(
            "1 Days", "2 Days", "3 Days", "4 Days", "5 Days",
            "6 Days", "7 Days", "8 Days", "9 Days", "10 Days",
            "11 Days", "12 Days", "13 Days"
        )
        res.number_picker.minValue = 1
        res.number_picker.maxValue = data.size
        res.number_picker.displayedValues = data

        res.number_picker.setOnValueChangedListener { picker, oldVal, newVal ->
            sharedDataViewModel.selectMinDuration(duration)
            res.tv_filtertrip_duration_num.text = "$newVal Days"
            duration = newVal
            showApplyButton(res)
        }
    }

    private fun setupApplyButton() {
        dismiss()
        applyConsumer.accept(true)
    }

    private fun setupPriceRangeBar(res: View) {
        res.rangebar_filtertrip_price.setOnRangeBarChangeListener { rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue ->

            val decimalFormat = DecimalFormat("#,###,###")
            minimum = decimalFormat.format(Integer.valueOf(leftPinValue))
            maximum = decimalFormat.format(Integer.valueOf(rightPinValue))
            res.tv_filtertrip_price_num.text = "From $minimum$ to $maximum$"


            sharedDataViewModel.selectFromPrice(Integer.parseInt(leftPinValue))
            sharedDataViewModel.selectToPrice(Integer.parseInt(rightPinValue))

            showApplyButton(res)
        }
    }


    private fun init(res: View) {
        categoryList = ArrayList()
        res.rv_filtertour_types.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        if (sharedDataViewModel.minDuration.value != 1)
            res.number_picker.value = sharedDataViewModel.minDuration.value!!
        sharedDataViewModel.selectedCategories.observe(activity!!, Observer{ categories -> selectedCategories = categories })
//        selectedCategories = sharedDataViewModel.selectedCategories.value!!
    }


    private fun showApplyButton(res: View) {
        res.button_filtertrip_apply.setTextColor(ContextCompat.getColor(context!!, R.color.green))
    }


    private fun getCategories(res: View) {
        tourSearchViewModel.initCategories()
        tourSearchViewModel.categories.observe(activity!!, Observer { categories ->
            if (categories != null) {
                categoryList.clear()
                categoryList.addAll(categories)
                if (adapter == null) {
                    adapter = CategoryRecyclerviewAddapter(categoryList, context) { category ->
                        showApplyButton(res)
                        if (selectedCategories.contains(category)) {
                            selectedCategories.remove(category)
                        } else {
                            selectedCategories.add(category)
                        }
                        sharedDataViewModel.selectCategories(selectedCategories)
                    }
                    res.rv_filtertour_types.adapter = adapter
                } else
                    adapter?.notifyDataSetChanged()
            }
        })
    }


}
