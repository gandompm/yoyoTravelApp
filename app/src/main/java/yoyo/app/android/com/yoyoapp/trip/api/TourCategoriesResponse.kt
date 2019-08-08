package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class TourCategoriesResponse(
    @SerializedName("selectedCategories")
    val categories: List<Category?>? = listOf()
) {
    data class Category(
        @SerializedName("code")
        val code: String? = null,
        @SerializedName("name")
        val name: String? = null
    )
}