package com.example.jorge.dameltiempo.Models

import com.example.jorge.dameltiempo.R
import java.text.SimpleDateFormat
import java.util.*

class CurrentWheather (var icon:String, var sumary:String,var temp:Double, var precip:Double, var time: Long){
    fun getIconResource():Int{
        when(icon){
            "clear-night"-> return R.mipmap.clear_night
            "clear-day"-> return R.mipmap.clear_day
            "cloudy"-> return R.mipmap.cloudy
            "cloudy-night"-> return R.mipmap.cloudy_night
            "fog"-> return R.mipmap.fog
            "partly-cloudy"-> return R.mipmap.partly_cloudy
            "partly-cloudy-night"-> return R.mipmap.cloudy_night
            "rain"-> return R.mipmap.rain
            "sleet"-> return R.mipmap.sleet
            "snow"-> return R.mipmap.snow
            "sunny"-> return R.mipmap.sunny
            "wind"-> return R.mipmap.wind
            "partly-cloudy-day"-> return R.mipmap.cloudy
            "hail"-> return R.mipmap.rain
            "thunderstorm"-> return R.mipmap.rain
            "tornado"-> return R.mipmap.rain
            else-> return R.mipmap.na

        }
    }
    fun getFormattedTime():String{
        val formatter = SimpleDateFormat("hh:mm a", Locale.US)
       // formatter.timeZone = TimeZone.getTimeZone(timeZone)
        val date = Date(time * 1000)
        val dayOfTheWeek = formatter.format(date)
        return dayOfTheWeek
    }
}