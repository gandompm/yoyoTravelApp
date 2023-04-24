package yoyo.app.android.com.yoyoapp.DataModels

import yoyo.app.android.com.yoyoapp.Utils

import java.util.ArrayList

class TripQuery {

    var type: String? = null
    var fromTime: Long? = null
    var toTime: Long? = null
    var fromPrice: Int = 0
    var toPrice: Int = 0
    var minDuration: Int = 0
    var origin: String? = null
    var destination: String? = null
    var categories: MutableSet<String>? = null
}
