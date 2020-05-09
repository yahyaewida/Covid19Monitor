package iti.mobilenative.covid19monitoring.features.countries.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.model.pojo.Country


class CountriesAdapter(
    val fragmentReference: CommunicatorOfAdapterAndFragment?,
      countriesList: List<Country>,
    val context: Context?,
    val isFromSubscribedCountries : Boolean
) : RecyclerView.Adapter<CountriesAdapter.CountriesHolder>() {

    var countriesList : ArrayList<Country> = countriesList.toCollection(arrayListOf())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.countries_recycler_item, parent, false);
        return CountriesHolder(view)
    }

    override fun onBindViewHolder(holder: CountriesHolder, position: Int) {
        holder.countryNameTextView.text = countriesList[position].country
        holder.countryConfirmedCasesTextView.text = countriesList[position].cases.toString()
        holder.countryRecoveredCasesTextView.text = countriesList[position].recovered.toString()
        holder.countryDeathsCasesTextView.text = countriesList[position].deaths.toString()

        if(!isFromSubscribedCountries){ // if from countries fragment
            if (countriesList[position].isSubscribed) {
                holder.subscriptionImageView.setImageResource(R.drawable.ic_subscribed_icon)
            }else{
                holder.subscriptionImageView.setImageResource(R.drawable.ic_not_subscribed)
            }
        }else{  // if from subscription fragment
            holder.subscriptionImageView.visibility = INVISIBLE
        }

        if (countriesList[position].countryInfo.flag != "null") {
            Picasso.get()
                .load(countriesList[position].countryInfo.flag)
                .into(holder.countryFlagImageView)
        }
        if(!isFromSubscribedCountries){ // if from countries fragment
            holder.subscriptionImageView.setOnClickListener {
                if (countriesList[position].isSubscribed) {
                    holder.subscriptionImageView.setImageResource(R.drawable.ic_not_subscribed)
                    countriesList[position].isSubscribed = false
                }else{
                    holder.subscriptionImageView.setImageResource(R.drawable.ic_subscribed_icon)
                    countriesList[position].isSubscribed = true
                }
                fragmentReference?.updateCountryList(position,countriesList[position].isSubscribed)
            }
        }
    }


    override fun getItemCount(): Int {
        return countriesList.size
    }


    class CountriesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countryFlagImageView: ImageView = itemView.findViewById(R.id.contryFlagImageView)
        var countryNameTextView: TextView = itemView.findViewById(R.id.countryNameRecyclerItemTextView)
        var countryConfirmedCasesTextView: TextView = itemView.findViewById(R.id.confirmedCasesRecyclerItemTextView)
        var countryRecoveredCasesTextView: TextView = itemView.findViewById(R.id.recoveredCasesRecyclerItemTextView)
        var countryDeathsCasesTextView: TextView = itemView.findViewById(R.id.deathsCasesRecyclerItemTextView)

        var subscriptionImageView : ImageView = itemView.findViewById(R.id.subscriptionImageView)
    }


    fun getData(): List<Country> {
        return countriesList
    }

    fun removeItem(position: Int) {
        countriesList.removeAt(position)
        notifyItemRemoved(position)
        //notifyDataSetChanged()
    }

    fun restoreItem(country: Country, position: Int) {
        countriesList.add(position, country)
        notifyItemInserted(position)
        //notifyDataSetChanged()
    }

}