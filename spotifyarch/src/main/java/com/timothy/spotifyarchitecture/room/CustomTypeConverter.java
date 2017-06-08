package com.timothy.spotifyarchitecture.room;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timothy.spotifyarchitecture.entities.SpotifyUser;
import com.timothy.spotifyarchitecture.retrofit.models.Image;

import java.util.List;
import java.util.Map;

/**
 * Created by tim on 6/3/17.
 */

public class CustomTypeConverter {
    @TypeConverter
    public static Map<String, String> gsonToMap(String gson) {
        return new Gson().fromJson(gson, new TypeToken<Map<String, String>>(){}.getType());
    }

    @TypeConverter
    public static String mapToGson(Map<String, String> map) {
        return new Gson().toJson(map);
    }

    @TypeConverter
    public static List<Image> gsonToImageList(String gson) {
        return new Gson().fromJson(gson, new TypeToken<List<Image>>(){}.getType());
    }

    @TypeConverter
    public static String imageListToGson(List<Image> images) {
        return new Gson().toJson(images);
    }

}
