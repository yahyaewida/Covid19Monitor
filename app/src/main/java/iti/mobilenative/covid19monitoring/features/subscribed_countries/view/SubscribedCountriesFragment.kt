package iti.mobilenative.covid19monitoring.features.subscribed_countries.view

import android.app.SearchManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.android.material.snackbar.Snackbar
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.countries.view.CountriesAdapter
import iti.mobilenative.covid19monitoring.features.subscribed_countries.viewmodel.SubscribedCountriesViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SubscribedCountriesFragment : Fragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory
    private var isUndoActionClicked = false
    lateinit var subscribedCountriesViewModel: SubscribedCountriesViewModel

    lateinit var noSubscriptionsLayout: LinearLayout
    private lateinit var subscriptionRecyclerView: RecyclerView

    lateinit var countriesAdapter: CountriesAdapter
    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity?.application as App).appComponent.provideActivity(
            ActivityModule(
                FragmentActivity(
                    this.id
                )
            )
        ).inject(this)
        subscribedCountriesViewModel = ViewModelProvider(
            this,
            viewModelProvidersFactory
        )[SubscribedCountriesViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_subscribed_countries, container, false)


        subscriptionRecyclerView = view.findViewById(R.id.subscriptionRecyclerView)
        subscriptionRecyclerView.layoutManager = LinearLayoutManager(context)
        noSubscriptionsLayout = view.findViewById(R.id.noSubscriptionsLayout)
        noSubscriptionsLayout.visibility = INVISIBLE
        subscribedCountriesViewModel.getAllSubscribedCountriesObservable()
            .observe(requireActivity(),
                Observer {
                    if (it.size > 0) {
                        countriesAdapter =
                            CountriesAdapter(null, it, context, isFromSubscribedCountries = true)
                        subscriptionRecyclerView.adapter = countriesAdapter
                        subscriptionRecyclerView.visibility = VISIBLE
                        noSubscriptionsLayout.visibility = INVISIBLE
                    } else {
                        subscriptionRecyclerView.visibility = INVISIBLE
                        noSubscriptionsLayout.visibility = VISIBLE
                    }

                })

        return view
    }

    override fun onResume() {
        super.onResume()
        enableSwipeToDeleteAndUndo()
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallBack =
            object : SwipeToDeleteCallBack(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    val position = viewHolder.adapterPosition
                    val item: Country = countriesAdapter.getData().get(position)
                    subscribedCountriesViewModel.unsubscribeFromCountry(item.country)
                    countriesAdapter.removeItem(position)

                    val snackbar = Snackbar
                        .make(view!!, "Item was removed from the list.", Snackbar.LENGTH_LONG)
                    snackbar.setAction("UNDO") {
                        isUndoActionClicked = true
                    }
                    snackbar.addCallback(object : BaseCallback<Snackbar?>() {
                        override fun onDismissed(
                            transientBottomBar: Snackbar?,
                            event: Int
                        ) {
                            super.onDismissed(transientBottomBar, event)
                            if (isUndoActionClicked) {
                                subscribedCountriesViewModel.subscribeToCountry(item.country)
                                countriesAdapter.restoreItem(item, position)
                                subscriptionRecyclerView.scrollToPosition(position)
                                isUndoActionClicked = false
                            }
                        }
                    })
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.show()
                }
            }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(subscriptionRecyclerView)
    }


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.app_bar_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search)
        if (searchItem != null) {
            val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
            val searchManager =
                ContextCompat.getSystemService(requireContext(), SearchManager::class.java)
            searchView.setSearchableInfo(searchManager!!.getSearchableInfo(requireActivity().componentName))

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //countriesAdapter.filter.filter(query)
                    Toast.makeText(context, "Seach Query: " + query, Toast.LENGTH_SHORT).show()
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    //countriesAdapter.filter.filter(newText)
                    Toast.makeText(context, "Seach Query: " + newText, Toast.LENGTH_SHORT).show()
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            return true
        }
        if (item.itemId == R.id.settings) {
            navController.navigate(R.id.settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
