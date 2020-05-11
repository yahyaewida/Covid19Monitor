package iti.mobilenative.covid19monitoring.features.countries.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.model.pojo.Case
import kotlinx.android.synthetic.main.cases_numbers_card.view.*

class CaseAdapter(casesList: List<Case>,val context: Context) : BaseAdapter(){
    var casesList: ArrayList<Case> = casesList.toCollection(arrayListOf())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val case = this.casesList[position]
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var card= inflator.inflate(R.layout.cases_numbers_card,null)
        var container :ConstraintLayout = card.findViewById(R.id.bg)
        container.setBackgroundColor(case.color)
        card.titleTv.text = case.title
        card.numTv.text = case.numbers
        return card
    }

    override fun getItem(position: Int): Any {
        return casesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return casesList.size
    }

}