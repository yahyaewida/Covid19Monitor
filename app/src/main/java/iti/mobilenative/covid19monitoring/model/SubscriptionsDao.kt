package iti.mobilenative.covid19monitoring.model

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country

@Dao
interface SubscriptionsDao {

    @Query("SELECT * FROM subscriptionsTable")
    suspend fun getAllSubscriptions() : List<Subscriptions>

    @Query("SELECT * FROM countryTable where country in (select countryName from subscriptionsTable)")
    fun getAllSubscribedCountries(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewSubscription(subscriptions: Subscriptions)

    @Query("DELETE FROM subscriptionsTable WHERE countryName = :countryName")
    suspend fun deleteSubscription(countryName :String)

    @Query("DELETE FROM subscriptionsTable")
    suspend fun deleteAllSubscriptions()

    @Update
    suspend fun updateSubscription(subscriptions: Subscriptions)
}