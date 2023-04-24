package yoyo.app.android.com.yoyoapp.trip.booking

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import es.dmoral.toasty.Toasty
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import jp.gr.java_conf.androtaku.countrylist.CountryList
import kotlinx.android.synthetic.main.bottom_sheet_traveller_details.view.*
import yoyo.app.android.com.yoyoapp.DataModels.Traveller
import yoyo.app.android.com.yoyoapp.Flight.Enum.Gender
import yoyo.app.android.com.yoyoapp.Flight.SearchDialog.SampleSearchModel
import yoyo.app.android.com.yoyoapp.Flight.Utils.NationalCodeUtil
import yoyo.app.android.com.yoyoapp.MainActivity
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.SharedDataViewModel
import yoyo.app.android.com.yoyoapp.trip.Utils.DateCalenderSetup
import java.util.*

class TravellerInfoFragment : Fragment() {

    private val sharedDataViewModel by activityViewModels<SharedDataViewModel>()
    private val dateOfBirthListner: DatePickerDialog.OnDateSetListener? = null
    private var countryList: ArrayList<SampleSearchModel>? = null
    private lateinit var traveller: Traveller
    private var gender: Gender? = null
    private var isIranian: Boolean = false
    private var position: Int = 0
    private var dateOfBirthTimestamp: Long = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_traveller_info, container, false)

        init(res)
        getTraveller()
        setupView(res)
        setupToggleButtons(res)
        res.button_traveller_details_save.setOnClickListener { saveTravellerInfo()}
        setupSelectTravellerButton(res)
        getCountryList()
        res.tv_traveller_details_nationality.setOnClickListener { setupCountryList()}
        res.iv_travellers_details_close.setOnClickListener { (context as MainActivity).onBackPressed()}
        res.et_traveller_details_dateofbirth.setOnClickListener { setupCalender()}

        return res
    }

    private fun setupCalender() {
        DateCalenderSetup(context, dateOfBirthListner, DateCalenderSetup.Function{ timestamp, standardDate ->
            dateOfBirthTimestamp = timestamp
            view?.et_traveller_details_dateofbirth?.text = standardDate
        })
    }

    private fun checkEnglishChar(): Boolean {
        return if (isStringOnlyAlphabet(view?.et_traveller_details_firstname?.text.toString()) &&
            isStringOnlyAlphabet(view?.et_traveller_details_lastname?.text.toString())) {
            true
        } else {
            Toast.makeText(context, "Please use latin characters", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun isStringOnlyAlphabet(str: String): Boolean {
        return (!str.isNullOrEmpty() && str.matches("^[a-zA-Z]*$".toRegex()))
    }


    private fun getTraveller() {
            position = arguments?.getInt("position") ?: 0
            traveller = sharedDataViewModel.getTraveller(position)!!
    }

    private fun init(res: View) {
        res.toggleSwitch_traveller_details.setCheckedPosition(0)
        gender = Gender.MALE
        isIranian = false
        countryList = ArrayList()
    }

    // setup toggle button for gender
    private fun setupToggleButtons(res: View) {
        res.toggleSwitch_traveller_details.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(i: Int) {
                gender = if (i == 0) {
                    Gender.MALE
                } else {
                    Gender.FEMALE
                }
            }
        }
    }


    private fun saveTravellerInfo() {
        if (checkEnglishChar()) {
            if(checkingEmptyFields())
            {
                traveller.firstName = view?.et_traveller_details_firstname?.text.toString()
                traveller.lastName = view?.et_traveller_details_lastname?.text.toString()
                traveller.gender = gender.toString()
                traveller.isIranian = isIranian

                traveller.dateOfBirth = view?.et_traveller_details_dateofbirth?.text.toString()
                traveller.dateOfBirthTimeStamp = dateOfBirthTimestamp

                val code = CountryList.convertNameToCode(context, view?.tv_traveller_details_nationality?.text.toString())
                traveller.nationality = code

                sharedDataViewModel.setTraveller(position, traveller)
                activity?.onBackPressed()
            }
        }
    }

    private fun checkingEmptyFields(): Boolean {
        if (view?.et_traveller_details_firstname?.text.toString().isEmpty()) {
            showError( resources.getString(R.string.first_name_cannot_be_empty))
            return false
        }
        if (view?.et_traveller_details_lastname?.text.toString().isEmpty()) {
            showError( resources.getString(R.string.last_name_cannot_be_empty))
            return false
        }
        if (traveller.isIranian) {
            traveller.iranianNationalCode = view?.et_traveller_details_iranian_nationality_code?.text.toString()
            if (view?.et_traveller_details_iranian_nationality_code?.text.toString().isEmpty()) {
                showError(getString(R.string.iranian_code_not_empty))
                return false
            }
            val nationalCodeUtil = NationalCodeUtil()
            if (!nationalCodeUtil.checkNationalCode(view?.et_traveller_details_iranian_nationality_code?.text.toString())) {
                showError(getString(R.string.iranina_national_code_not_valid))
                return false
            }
        } else {
            traveller.passportNumber = view?.et_traveller_details_passport_number?.text.toString()
            if (view?.et_traveller_details_passport_number?.text.toString().isEmpty()) {
                showError(getString(R.string.passport_not_empty))
                return false
            }
        }
        if (view?.et_traveller_details_dateofbirth?.text.toString().isEmpty()) {
            showError(getString(R.string.date_birth_can_not_empty))
            return false
        }
        if (TextUtils.isEmpty(view?.tv_traveller_details_nationality?.text)) {
            showError("Nationality Field is empty")
            return false
        }
        return true
    }

    private fun setupSelectTravellerButton(res: View) {
        res.button_traveller_details_select_travellers.setOnClickListener {
            val myTravellerDialogFragment =
                MyTravellerDialogFragment { selectedTraveller ->
                    run {
                        traveller = selectedTraveller
                        setupView(res)
                    }
                }
            myTravellerDialogFragment.show(fragmentManager!!, "select your traveller")
        }
    }

    private fun setupView(res: View) {
        traveller.apply {
            res.et_traveller_details_firstname.setText(firstName)
            res.et_traveller_details_lastname.setText(lastName)
            res.et_traveller_details_iranian_nationality_code.setText(iranianNationalCode)
            res.et_traveller_details_passport_number?.setText(passportNumber)
            res.tv_traveller_details_nationality.text = nationality
            res.et_traveller_details_dateofbirth.text = dateOfBirth
            dateOfBirthTimestamp = dateOfBirthTimeStamp
        }

        if (traveller.gender != null) {
            when (traveller.gender) {
                "MALE" -> {
                    res.toggleSwitch_traveller_details.setCheckedPosition(0)
                    gender = Gender.MALE
                }
                "FEMALE" -> {
                    res.toggleSwitch_traveller_details.setCheckedPosition(1)
                    gender = Gender.FEMALE
                }
            }
        }
        // if traveller is iranian, visible nationality code field
        if (traveller.isIranian) {
            res.et_traveller_details_iranian_nationality_code.setText(traveller.iranianNationalCode)
            res.tv_traveller_details_passport_number.visibility = View.GONE
            res.et_traveller_details_passport_number?.visibility = View.GONE
            res.et_traveller_details_iranian_nationality_code.visibility = View.VISIBLE
            res.tv_traveller_details_iranian_code.visibility = View.VISIBLE
            isIranian = true
        } else {
            res.et_traveller_details_passport_number?.setText(traveller.passportNumber)
            res.tv_traveller_details_passport_number.visibility = View.VISIBLE
            res.et_traveller_details_passport_number?.visibility = View.VISIBLE
            res.et_traveller_details_iranian_nationality_code.visibility = View.GONE
            res.tv_traveller_details_iranian_code.visibility = View.GONE
            isIranian = false
        }// if traveller is not iranian, visible passport field
        res.tv_traveller_details_title.text = "Traveller ${position+1}"
    }

    // get country list from server
    private fun getCountryList() {
        val countryNames = CountryList.getCountryNames(context)
        for (j in countryNames.indices) {
            val name = countryNames[j]
            val sampleSearchModel = SampleSearchModel(name)
            countryList?.add(sampleSearchModel)
        }
    }


    // show passport or national code for iranian and foreigner
    private fun setupCountryList() {
        val simpleSearchDialogCompat = SimpleSearchDialogCompat(context, "Search...",
            "What are you looking for...?", null, countryList,
            SearchResultListener { dialog, item, position ->
                if (item.title.toUpperCase().contains("IRAN") || item.title.contains("ایران") ||
                    item.title.toUpperCase().contains("IR") && !item.title.toUpperCase().contains("IRAQ")
                ) {
                    isIranian = true
                    view?.tv_traveller_details_passport_number?.visibility = View.INVISIBLE
                    view?.et_traveller_details_passport_number?.visibility = View.INVISIBLE
                    view?.et_traveller_details_iranian_nationality_code?.visibility = View.VISIBLE
                    view?.tv_traveller_details_iranian_code?.visibility = View.VISIBLE
                } else {
                    isIranian = false
                    view?.et_traveller_details_passport_number?.visibility = View.VISIBLE
                    view?.tv_traveller_details_passport_number?.visibility = View.VISIBLE
                    view?.et_traveller_details_iranian_nationality_code?.visibility = View.INVISIBLE
                    view?.tv_traveller_details_iranian_code?.visibility = View.INVISIBLE
                }
                view?.tv_traveller_details_nationality?.text = item.title
                dialog.dismiss()
            })
        simpleSearchDialogCompat.show()
    }

    private fun showError(error: String) {
        Toasty.error(context as Context,error).show()
    }
}

