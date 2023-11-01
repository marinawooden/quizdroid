package edu.uw.ischool.mwoode.quizdroid

import android.content.Context
import org.json.JSONObject

fun loadCategories(context: Context, fileName: String): JSONObject {
    // Change the path to access "data.json" from the "assets" folder
    val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
    return JSONObject(json)
}