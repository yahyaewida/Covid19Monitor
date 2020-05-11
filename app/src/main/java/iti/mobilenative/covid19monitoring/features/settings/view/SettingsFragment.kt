package iti.mobilenative.covid19monitoring.features.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import iti.mobilenative.covid19monitoring.R
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment(),View.OnClickListener{
    lateinit var selected_radio_btn: RadioButton;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        // Inflate the layout for this fragment
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notify_btn.setOnClickListener(this)
        bk.setOnClickListener(this)

    }
    override fun onStop() {
        super.onStop()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.notify_btn -> getNotificationInterval(v)
            R.id.bk -> requireActivity().onBackPressed()
        }
    }

    private fun getNotificationInterval(v: View?) {
        var id: Int = radio_group.checkedRadioButtonId
        if (id!=-1){
            val radio :RadioButton = requireView().findViewById(id);
            Toast.makeText(context,"${radio.text}",
                Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"nothing selected",
                Toast.LENGTH_SHORT).show()
        }
    }
}
