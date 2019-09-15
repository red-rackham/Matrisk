package ch.j2mb.matrisk.gameplay.helper

import android.content.Context
import ch.j2mb.matrisk.gameplay.model.ContinentList
import com.google.gson.Gson
import java.io.InputStream

class JsonHandler {

    fun getCountriesFromGson(jsonString: String, context:Context): ContinentList? {
        var continentList: ContinentList? = null
        var json: String? = null
        try {
            val inputStream: InputStream = context.applicationContext.assets.open(jsonString)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return continentList
        }
        val gson = Gson()
        continentList = gson.fromJson(json, ContinentList::class.java)
        return continentList
    }
}