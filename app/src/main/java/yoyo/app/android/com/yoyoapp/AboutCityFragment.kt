package yoyo.app.android.com.yoyoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.florent37.materialviewpager.MaterialViewPagerHelper
import kotlinx.android.synthetic.main.fragment_about_city.view.*


class AboutCityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about_city, container, false)

        MaterialViewPagerHelper.registerScrollView(activity, view.scrollView)
        arguments.also {
            view.tv_city_about.text = it?.getString(Utils.KEY_BUNDLE_CITY_ABOUT)
            view.tv_city_natural_attractions.text = it?.getString(Utils.KEY_BUNDLE_CITY_NATURAL_ATT)
            view.tv_city_historical_attractions.text = it?.getString(Utils.KEY_BUNDLE_CITY_HISTORICAL_ATT)
            view.tv_city_man_made_attractions.text = it?.getString(Utils.KEY_BUNDLE_CITY_MAN_MADE_ATT)
            view.tv_city_food_attractions.text = it?.getString(Utils.KEY_BUNDLE_CITY_FOOD_SOUVENIR)
            view.tv_city_climate.text = it?.getString(Utils.KEY_BUNDLE_CITY_CLIMATE)
            view.tv_city_top_experience.text = it?.getString(Utils.KEY_BUNDLE_CITY_FOOD_TOPEXPERIENCE)
        }

        return view
    }
}
