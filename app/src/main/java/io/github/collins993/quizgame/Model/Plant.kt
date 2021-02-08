package io.github.collins993.quizgame.Model

import android.util.Log

class Plant(var genus: String, var species: String, var cultivar: String, var common: String,
            var picture_name: String, var description: String, var difficulty: Int, var id: Int) {

    constructor(): this("", "", "", "",
        "", "", 0, 0)

    private var _plantName: String? = null

    var plantName: String?

        get() = _plantName

        set(value) {

            _plantName = value
        }

    override fun toString(): String {

        Log.i("PLANT", "$common - $species")

        return "$common $species"
    }
}