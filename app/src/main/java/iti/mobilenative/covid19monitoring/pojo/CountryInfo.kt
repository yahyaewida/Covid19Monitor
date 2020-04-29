package iti.mobilenative.covid19monitoring.pojo

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("_id")
    val _id: Number,

    @SerializedName("iso2")
    val iso2: String,

    @SerializedName("lat")
    val lat: Number,

    @SerializedName("long")
    val long: Number,

    @SerializedName("flag")
    val flag: String
)