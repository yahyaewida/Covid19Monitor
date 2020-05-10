package iti.mobilenative.covid19monitoring.features

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import iti.mobilenative.covid19monitoring.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViews()
    }

    private fun setupViews() {
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { navController: NavController, navDestination: NavDestination, bundle: Bundle? ->
            if (navDestination.id == R.id.settingsFragment ||
                 navDestination.id == R.id.currentStatisticsFragment
                || navDestination.id == R.id.historicalStatisticsFragment  ) {
                bottomNavigationView.visibility = INVISIBLE
                supportActionBar?.hide();
            } else {
                bottomNavigationView.visibility = VISIBLE
                supportActionBar?.show();
            }
        }
        bottomNavigationView.setOnNavigationItemReselectedListener {
            // do nothing
        }

    }
}
