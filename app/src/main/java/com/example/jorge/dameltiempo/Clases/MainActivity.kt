package com.example.jorge.dameltiempo.Clases

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.jorge.dameltiempo.API.JSONParser
import com.example.jorge.dameltiempo.Adapters.AdaptadorHoras
import com.example.jorge.dameltiempo.Adapters.AdaptadorDias
import com.example.jorge.dameltiempo.Models.CurrentWheather
import com.example.jorge.dameltiempo.Models.Day
import com.example.jorge.dameltiempo.Models.Hour
import com.example.jorge.dameltiempo.R
import com.example.jorge.eltiempo3.API.API_KEY
import com.example.jorge.eltiempo3.API.DARK_SKY_URL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_horas.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_dias.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(){

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    val jsonParser = JSONParser()
    var days:ArrayList<Day> = ArrayList()
    var hours:ArrayList<Hour> = ArrayList()
    companion object {
        val HOURLY_WEATHER_ACTIVITY = "HOURLY_WEATHER_ACTIVITY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        val latitud = 36.984261
        val longitud = -3.568558
        val languaje = getString(R.string.languaje)
        val units = getString(R.string.units)
        //Variable con la url de la API
        val url = "$DARK_SKY_URL/$API_KEY/$latitud,$longitud?lang=$languaje&units=$units"
        // Código para recuperar el archivo de la API
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
            val responseJSON = JSONObject(response)
            val currentWheather = jsonParser.getCurrentWeatherFromJson(responseJSON)
            days = jsonParser.getDailyWheather(responseJSON)
            hours = jsonParser.getHourlyWheatherFromJSON(responseJSON)
                    }, Response.ErrorListener {
            val snackbar = Snackbar.make(main,"Error de Red", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reintentar?") {
                    getWheather()
                }
            snackbar.show()
        })
        queue.add(stringRequest)
    }

     fun getWheather() {
        val latitud = 36.984261
        val longitud = -3.568558
        val languaje = getString(R.string.languaje)
        val units = getString(R.string.units)
        //Variable con la url de la API
        val url = "$DARK_SKY_URL/$API_KEY/$latitud,$longitud?lang=$languaje&units=$units"
        // Código para recuperar el archivo de la API
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
            val responseJSON = JSONObject(response)
            val currentWheather = jsonParser.getCurrentWeatherFromJson(responseJSON)
            days = jsonParser.getDailyWheather(responseJSON)
            hours = jsonParser.getHourlyWheatherFromJSON(responseJSON)
            buildCurrentWheatherUI(currentWheather)
        }, Response.ErrorListener {
            val snackbar = Snackbar.make(main,"Error de Red", Snackbar.LENGTH_INDEFINITE)
                .setAction("Reintentar?") {
                    getWheather()
                }
            snackbar.show()
        })
        queue.add(stringRequest)
    }

    fun buildCurrentWheatherUI(currentWheather: CurrentWheather) {
        textViewTempera.text = "${currentWheather.temp.toString()} Cº"
        textViewPrecip.text= "${currentWheather.precip.toString()} % "
        textViewDerscipcion.text = currentWheather.sumary
        textView4.text = " Hora de la ültima actualización ${currentWheather.getFormattedTime()}"
        imageViewIco.setImageDrawable(ResourcesCompat.getDrawable(resources,currentWheather.getIconResource(),null))

    }

    fun startDailyActivity(){
        recyclerViewDias.layoutManager = LinearLayoutManager(this)
        recyclerViewDias.adapter = AdaptadorDias(days,this)
    }
    fun startHourlyActivity(){
      // textViewDia.text = days.get(0).getFormattedTime()
        recyclerViewhoras.layoutManager = LinearLayoutManager(this)
        recyclerViewhoras.adapter = AdaptadorHoras(hours,this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return super.onOptionsItemSelected(item)
    }
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            if (position==0) {
                return PlaceholderFragmentMain.newInstance()
            }
            else if (position==1){
                return PlaceholderFragmentHoras.newInstance()
                }
            else if (position==2) {
                return PlaceholderFragmentDias.newInstance()
            }
            return PlaceholderFragmentDias.newInstance()
        }
        override fun getCount(): Int {
            return 3
        }
    }
    class PlaceholderFragmentMain : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            return rootView
        }
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            (activity as MainActivity).getWheather()

      }
        companion object {
            private val ARG_SECTION_NUMBER = "section_number"
            fun newInstance(): PlaceholderFragmentMain {
                val fragment = PlaceholderFragmentMain()
                val args = Bundle()
                fragment.arguments = args
                return fragment
            }
        }
    }
    class PlaceholderFragmentHoras() : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_horas, container, false)
            return rootView
        }

        override fun onContextItemSelected(item: MenuItem?): Boolean {
            return super.onContextItemSelected(item)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            (activity as MainActivity).startHourlyActivity()
        }
        companion object {
            fun newInstance(): PlaceholderFragmentHoras {
                val fragment = PlaceholderFragmentHoras()
                return fragment
            }
        }
    }
    class PlaceholderFragmentDias() : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_dias, container, false)
            return rootView
        }
        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            (activity as MainActivity).startDailyActivity()
        }
        companion object {
            fun newInstance(): PlaceholderFragmentDias {
                val fragment = PlaceholderFragmentDias()
                return fragment
            }
        }
    }
}
