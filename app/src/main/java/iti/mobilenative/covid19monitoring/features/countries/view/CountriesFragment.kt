package iti.mobilenative.covid19monitoring.features.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import iti.mobilenative.covid19monitoring.R

/**
 * A simple [Fragment] subclass.
 */
class CountriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false)
    }

}
