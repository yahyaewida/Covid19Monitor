package iti.mobilenative.covid19monitoring.pojo

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("_id")
    var _id: Long = 0,

    @SerializedName("iso2")
    var iso2: String  = "",

    @SerializedName("lat")
    var lat: Double = 0.0,

    @SerializedName("long")
    var long: Double = 0.0,

    @SerializedName("flag")
    var flag: String = ""
)