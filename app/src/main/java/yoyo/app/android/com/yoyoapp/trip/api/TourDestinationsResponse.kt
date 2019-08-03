package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class TourDestinationsResponse(
    @SerializedName("locations")
    val locations: List<Location?>? = listOf()
)