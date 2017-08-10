package com.timmahh.spotifyweb.room

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.timmahh.spotifyweb.retrofit.models.Copyright
import com.timmahh.spotifyweb.retrofit.models.Image

/**
 * SpotifyDatabase methods including TypeConverters for Entities.
 */
interface SpotifyDatabase {
    fun spotifyDao(): SpotifyDao
}

class SpotifyConverters {
	@TypeConverter
	fun gsonToMap(gson: String): Map<String, String> = Gson().fromJson<Map<String, String>>(gson, object : TypeToken<Map<String, String>>() {}.type)
	
	@TypeConverter
	fun mapToGson(map: Map<String, String>): String = Gson().toJson(map)
	
	@TypeConverter
	fun gsonToImageList(gson: String): List<Image> = Gson().fromJson<List<Image>>(gson, object : TypeToken<List<Image>>() {}.type)
	
	@TypeConverter
	fun imageListToGson(images: List<Image>): String = Gson().toJson(images)
	
	@TypeConverter
	fun stringsToGson(strings: List<String>): String = Gson().toJson(strings)
	
	@TypeConverter
	fun gsonToStrings(gson: String): List<String> = Gson().fromJson(gson, object : TypeToken<List<String>>() {}.type)
	
	@TypeConverter
	fun copyrightToGson(copyrights: List<Copyright>): String = Gson().toJson(copyrights)
	
	@TypeConverter
	fun gsonToCopyright(gson: String): List<Copyright> = Gson().fromJson(gson, object : TypeToken<List<Copyright>>() {}.type)
}