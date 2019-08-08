package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class BookScheduleRequest(
    @SerializedName("companion_travellers")
    val companionTravellers: List<CompanionTraveller?>?,
    @SerializedName("leader_traveller")
    val leaderTraveller: LeaderTraveller?
)
{
    data class CompanionTraveller(
        @SerializedName("dob")
        val dob: Int?,
        @SerializedName("firstname")
        val firstname: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("lastname")
        val lastname: String?,
        @SerializedName("national_code")
        val nationalCode: String?,
        @SerializedName("nationality")
        val nationality: String?,
        @SerializedName("passport_number")
        val passportNumber: String?
    )

    data class LeaderTraveller(
        @SerializedName("email")
        val email: String?,
        @SerializedName("fullname")
        val fullname: String?,
        @SerializedName("phone_number")
        val phoneNumber: String?
    )
}