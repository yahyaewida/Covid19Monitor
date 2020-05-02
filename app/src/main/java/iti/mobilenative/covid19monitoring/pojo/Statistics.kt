package iti.mobilenative.covid19monitoring.pojo

import com.google.gson.annotations.SerializedName

data class Statistics (
    @SerializedName("updated")
    val updated: Long,

    @SerializedName("cases")
    val cases: Long,

    @SerializedName("todayCases")
    val todayCases: Long,

    @SerializedName("deaths")
    val deaths: Long,

    @SerializedName("todayDeaths")
    val todayDeaths: Long,

    @SerializedName("recovered")
    val recovered: Long,

    @SerializedName("active")
    val active: Long,

    @SerializedName("critical")
    val critical: Long,

    @SerializedName("affectedCountries")
    val affectedCountries: Int

    /*
    @SerializedName("casesPerOneMillion")
    val casesPerOneMillion: Double,

    @SerializedName("deathsPerOneMillion")
    val deathsPerOneMillion: Double,

    @SerializedName("tests")
    val tests: Number,

    @SerializedName("testsPerOneMillion")
    val testsPerOneMillion: Double*/


)
