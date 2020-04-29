package iti.mobilenative.covid19monitoring.pojo

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("updated")
    val updated: Number,

    @SerializedName("country")
    val country: String,

    @SerializedName("countryInfo")
    val countryInfo: CountryInfo,

    @SerializedName("continent")
    val continent: String,

    @SerializedName("cases")
    val cases: Number,

    @SerializedName("todayCases")
    val todayCases: Number,

    @SerializedName("deaths")
    val deaths: Number,

    @SerializedName("todayDeaths")
    val todayDeaths: Number,

    @SerializedName("recovered")
    val recovered: Number,

    @SerializedName("active")
    val active: Number,

    @SerializedName("critical")
    val critical: Number,

    @SerializedName("casesPerOneMillion")
    val casesPerOneMillion: Number,

    @SerializedName("deathsPerOneMillion")
    val deathsPerOneMillion: Number,

    @SerializedName("tests")
    val tests: Number,

    @SerializedName("testsPerOneMillion")
    val testsPerOneMillion: Number


)

