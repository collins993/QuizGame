package io.github.collins993.quizgame.Model

import org.json.JSONArray
import org.json.JSONObject

class ParsePlantUtility {

    fun parsePlantObjectFromJSONData() : List<Plant>? {

        var allPlantObjects: ArrayList<Plant> = ArrayList()

        var downloadingObject = DownloadingObject()

        var topLevelPlantJSONData = downloadingObject.downloadJSONDataFromLink("http://plantplaces.com/perl/mobile/flashcard.pl")

        var topLevelJSONObject: JSONObject = JSONObject(topLevelPlantJSONData)

        var plantObjectArray: JSONArray = topLevelJSONObject.getJSONArray("values")

        var index: Int = 0

        while (index < plantObjectArray.length()) {

            /**
            genus: String, species: String, cultivar: String, common: String,
            picture_name: String, description: String, difficulty: Int, id: Int
             **/

            var plantObject: Plant = Plant()

            var jsonObject = plantObjectArray.getJSONObject(index)

            with(jsonObject) {

                plantObject.genus = getString("genus")
                plantObject.species = getString("species")
                plantObject.cultivar = getString("cultivar")
                plantObject.common = getString("common")
                plantObject.picture_name = getString("picture_name")
                plantObject.description = getString("description")
                plantObject.difficulty = jsonObject.getInt("difficulty")
                plantObject.id = getInt("id")

            }

            allPlantObjects.add(plantObject)

            index++

        }

        return allPlantObjects
    }
}