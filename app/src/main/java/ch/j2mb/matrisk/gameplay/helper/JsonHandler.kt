package ch.j2mb.matrisk.gameplay.helper

import android.content.Context
import android.util.Log
import ch.j2mb.matrisk.gameplay.helper.ai_machine.*
import ch.j2mb.matrisk.gameplay.model.ContinentList
import ch.j2mb.matrisk.gameplay.model.CountryList
import com.google.gson.Gson
import java.io.InputStream
import java.util.ArrayList

object JsonHandler {

    fun getJsonFromFile(fileName: String, context: Context): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = context.applicationContext.assets.open(fileName)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return json
        }
        Log.d("JSON:", json)
        return json
    }

    fun getContinentListFromJson(jsonString: String): ContinentList {
        Log.d("JSON:", "getContinentListFromJson")
        Log.d("JSON:", jsonString)

        val gson = Gson()
        var continentList = gson.fromJson(jsonString, ContinentList::class.java)?: ContinentList()
        continentList.continents.sortBy { it.name }

        //DEBUG
        for (continent in continentList.continents) {
            continent.countries.sortBy { it.name }
            Log.d("JSON:", "continent: ${continent.name}")
            for (country in continent.countries) {
                Log.d(
                    "JSON:",
                    "name: ${country.name},\t player:${country.player},\t count:${country.count},\t modified:${country.modified}"
                )
            }
        }

        return continentList
    }

    fun getCountryListFromJson(jsonString: String) : CountryList {
        val gson = Gson()
        return gson.fromJson(jsonString, CountryList::class.java)?: CountryList()
    }

    fun getBidirectionalLinkfromJson(jsonString: String): BiDirectionalLinkList {
        val gson = Gson()
        Log.d("JSON:", "BiDirectionalLinkList from JSON")

        val biDirectionalLinkList = gson.fromJson(jsonString, BiDirectionalLinkList::class.java)
        for (link in biDirectionalLinkList.bidirectionalLinks)
            Log.d("Links:", "from: ${link.fromCountry},\t to:${link.toCountry},")
        return biDirectionalLinkList
    }

    fun getJsonFromContinentList(continentList: ContinentList): String {
        val gson = Gson()
        return gson.toJson(continentList)
    }

    fun gsonTemplateConverter(
        continentList: ContinentList,
        bidirectionalLinks: ArrayList<GsonTemplateBidirectionalLink>
    ): GsonTemplateCountryGraph {
        val gsonContinentList = ArrayList<GsonTemplateContinent>()
        for (continent in continentList.continents) {
            val countryList = ArrayList<GsonTemplateCountry>()
            Log.d("gsonTemplateConverter:", "continent:${continent.name}")

            for (country in continent.countries) {
                countryList.add(
                    GsonTemplateCountry(
                        country.name,
                        country.player,
                        country.count,
                        false
                    )
                )
                Log.d(
                    "gsonTemplateConverter:",
                    "name: ${country.name},\t player:${country.player},\t count:${country.count},\t modified:${country.modified}"
                )
            }
            val gsonContinent = GsonTemplateContinent(continent.name, countryList)
            gsonContinentList.add(gsonContinent)
        }

        val gsonTemplateCountryGraph = GsonTemplateCountryGraph()
        gsonTemplateCountryGraph.continents = gsonContinentList
        gsonTemplateCountryGraph.bidirectionalLinks = bidirectionalLinks
        return gsonTemplateCountryGraph
    }

    fun getCountryGraphJson(
        continentList: ContinentList,
        bidirectionalLinks: ArrayList<GsonTemplateBidirectionalLink>
    ): String {
        var jsonString = getJsonFromCountryGraph(gsonTemplateConverter(continentList, bidirectionalLinks))
        Log.d("getCountryGraphJson:", jsonString)
        return jsonString
    }

    fun getJsonFromCountryGraph(countryGraphObject: GsonTemplateCountryGraph): String {
        val gson = Gson()
        return gson.toJson(countryGraphObject)
    }
}