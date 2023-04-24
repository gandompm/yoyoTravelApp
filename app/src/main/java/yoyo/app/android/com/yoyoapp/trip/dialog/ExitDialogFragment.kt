package yoyo.app.android.com.yoyoapp.trip.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_exit_dialog.view.*
import yoyo.app.android.com.yoyoapp.R
import yoyo.app.android.com.yoyoapp.SharedDataViewModel

class ExitDialogFragment: DialogFragment() {

    private val sharedDataViewModel by activityViewModels<SharedDataViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_exit_dialog, container, false)

        res.button_exitdialog_no.setOnClickListener { dismiss() }
        res.button_exitdialog_yes.setOnClickListener {
            dismiss()
            activity?.onBackPressed()
            sharedDataViewModel.resetBookingTravellers()
        }


        return res
    }

}
