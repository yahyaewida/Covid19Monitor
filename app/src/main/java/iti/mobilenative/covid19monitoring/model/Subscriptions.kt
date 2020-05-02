package iti.mobilenative.covid19monitoring.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import iti.mobilenative.covid19monitoring.utils.SUBSCRIPTIONS_TABLE_NAME

@Entity(tableName = SUBSCRIPTIONS_TABLE_NAME)
data class Subscriptions(
    @PrimaryKey
    var countryName :String = ""
)