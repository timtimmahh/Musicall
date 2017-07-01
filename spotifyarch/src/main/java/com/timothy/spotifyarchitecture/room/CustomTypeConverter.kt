package com.timothy.spotifyarchitecture.room

import android.arch.persistence.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.retrofit.models.Image

/**
 * Created by tim on 6/3/17.
 */

class CustomTypeConverter {
    @TypeConverter
    fun gsonToMap(gson: String): Map<String, String> {
        return Gson().fromJson<Map<String, String>>(gson, object : TypeToken<Map<String, String>>() {

        }.type)
    }

    @TypeConverter
    fun mapToGson(map: Map<String, String>): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun gsonToImageList(gson: String): List<Image> {
        return Gson().fromJson<List<Image>>(gson, object : TypeToken<List<Image>>() {

        }.type)
    }

    @TypeConverter
    fun imageListToGson(images: List<Image>): String {
        return Gson().toJson(images)
    }

}
