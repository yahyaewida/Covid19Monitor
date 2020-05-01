package iti.mobilenative.covid19monitoring.pojo

import com.google.gson.annotations.SerializedName

data class HistoricalStatistics(
    @SerializedName("country")
    val country: String,

    @SerializedName("timeline")
    val timeline: Timeline
)