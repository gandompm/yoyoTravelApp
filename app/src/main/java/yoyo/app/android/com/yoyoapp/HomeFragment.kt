package yoyo.app.android.com.yoyoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cpacm.library.transformers.CyclePageTransformer
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_home.view.*
import yoyo.app.android.com.yoyoapp.BannerSlider.BasicPagerAdapter
import yoyo.app.android.com.yoyoapp.BottomSheet.CitiesListBottomSheetDialogFragment
import yoyo.app.android.com.yoyoapp.trip.search.TourSearchFragment

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val res = inflater.inflate(R.layout.fragment_home, container, false)

        res.banner_slider_mainpage.apply {
            adapter = BasicPagerAdapter(context)
            startAutoScroll(true)
            setPageTransformer(CyclePageTransformer(this))
        }
        res.sv_mainpage_search.setOnClickListener {
            openSearchBottomSheet()
        }
        res.cv_mainpage_hotels.setOnClickListener {
            Toasty.info(context!!, "Coming soon...").show()
        }
        res.cv_mainpage_trips.setOnClickListener {
            openTourSearch()
        }
        res.cv_mainpage_flight.setOnClickListener {
            Toasty.info(context!!, "Coming soon...").show()
        }

        return res
    }

    private fun openSearchBottomSheet() {
        CitiesListBottomSheetDialogFragment().apply {
            show(this@HomeFragment.fragmentManager!!, "add_cities_list_dialog_fragment")
        }
    }

    private fun openTourSearch() {
        val tripSearchFragment = TourSearchFragment().apply {
            arguments = Bundle().apply { putString(Utils.KEY_BUNDLE_MAIN_PAGE_CODE, "trip") }
        }
        (activity as MainActivity).showFragment(this, tripSearchFragment, "searchTrip",false)
    }
}
