package iti.mobilenative.covid19monitoring.model.pojo

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("_id")
    var id: Long = 0,

    @SerializedName("lat")
    var lat: Double = 0.0,

    @SerializedName("long")
    var long: Double = 0.0,

    @SerializedName("flag")
    var flag: String = ""
)