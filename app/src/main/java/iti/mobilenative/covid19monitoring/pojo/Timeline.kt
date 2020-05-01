package iti.mobilenative.covid19monitoring.pojo

import com.google.gson.annotations.SerializedName

data class Timeline(
    @SerializedName("cases")
    val cases: Map<String, Long>,

    @SerializedName("deaths")
    val deaths: Map<String, Long>,

    @SerializedName("recovered")
    val recovered: Map<String, Long>
)
