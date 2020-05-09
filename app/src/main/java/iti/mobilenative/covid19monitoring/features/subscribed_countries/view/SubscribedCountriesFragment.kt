package iti.mobilenative.covid19monitoring.features.subscribed_countries.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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


}
