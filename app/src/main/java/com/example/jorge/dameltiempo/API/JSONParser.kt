package com.example.jorge.dameltiempo.API

import com.example.jorge.dameltiempo.Models.CurrentWheather
import com.example.jorge.dameltiempo.Models.Day
import org.json.JSONObject
import com.example.jorge.dameltiempo.Extensions.iterator
import com.example.jorge.dameltiempo.Models.Hour

class JSONParser {
    fun getCurrentWeatherFromJson(response: JSONObject):CurrentWheather{
       val currenttJSON=response.getJSONObject(currently)
        with(currenttJSON){
            val currentWheather = CurrentWheather(getString(icon),
                                                  getString(summary),
                                                  getDouble(temperature),
                                                  getDouble(precipProbability),
                                                  getLong(time))

            return currentWheather
        }
    }
    fun getDailyWheather(response: JSONObject): ArrayList<Day>{
        val dailyJSON = response.getJSONObject(daily)
        val timeZone = response.getString(timezone)
        val dayJSONArray = dailyJSON.getJSONArray(data)
        val days = ArrayList<Day>()
            for (jsonDay in dayJSONArray){

                val minTemp = jsonDay.getDouble(temperatureMin)
                val maxTem = jsonDay.getDouble(temperatureMax)
                val time = jsonDay.getLong(time)
                days.add(Day(time,minTemp,maxTem, timeZone))
        }
           return days
    }

    fun getHourlyWheatherFromJSON(response: JSONObject): ArrayList<Hour>{
        val hourlyJSON = response.getJSONObject(hourly)
        val timeZone = response.getString(timezone)
        val hourJSONArray = hourlyJSON.getJSONArray(data)
        val hours = ArrayList<Hour>()
        for (jsonHour in hourJSONArray){

            val time = jsonHour.getLong(time)
            val temperature = jsonHour.getDouble(temperature)
            val precipProrob =jsonHour.getDouble(precipProbability)
            hours.add(Hour(time,temperature, precipProrob,timeZone))
        }
        return hours
    }
}