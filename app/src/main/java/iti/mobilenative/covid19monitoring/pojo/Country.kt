package iti.mobilenative.covid19monitoring.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "countryTable")
data class Country(

    @SerializedName("updated")
    var updated: Long = 0,
    @PrimaryKey
    @SerializedName("country")
    var country: String = "",

    @Embedded
    @SerializedName("countryInfo")
    var countryInfo: CountryInfo = CountryInfo(),

    @SerializedName("continent")
    var continent: String = "",

    @SerializedName("cases")
    var cases: Long = 0,

    @SerializedName("todayCases")
    var todayCases: Long = 0,

    @SerializedName("deaths")
    var deaths: Long = 0,

    @SerializedName("todayDeaths")
    var todayDeaths: Long,

    @SerializedName("recovered")
    var recovered: Long = 0,

    @SerializedName("active")
    var active: Long = 0,

    @SerializedName("critical")
    var critical: Long = 0,

    @SerializedName("casesPerOneMillion")
    var casesPerOneMillion: Double = 0.0,

    @SerializedName("deathsPerOneMillion")
    var deathsPerOneMillion: Double = 0.0,

    @SerializedName("tests")
    var tests: Long = 0,

    @SerializedName("testsPerOneMillion")
    var testsPerOneMillion: Double = 0.0


)
