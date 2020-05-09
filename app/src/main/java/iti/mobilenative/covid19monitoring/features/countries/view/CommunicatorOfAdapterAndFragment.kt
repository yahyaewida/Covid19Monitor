package iti.mobilenative.covid19monitoring.features.countries.view

interface CommunicatorOfAdapterAndFragment {
    fun updateCountryList(indexOfCountry : Int,subscriptionValue : Boolean)
}