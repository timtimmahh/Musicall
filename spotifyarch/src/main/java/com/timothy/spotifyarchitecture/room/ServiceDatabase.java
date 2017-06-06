package com.timothy.spotifyarchitecture.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.timothy.spotifyarchitecture.entities.SpotifyUser;
import com.timothy.spotifyarchitecture.entities.Token;

/**
 * Created by tim on 5/30/17.
 */
@Database(entities = {Token.class, SpotifyUser.class}, version = 2)
@TypeConverters({CustomTypeConverter.class})
public abstract class ServiceDatabase extends RoomDatabase {
    public abstract SpotifyDao serviceDao();
}
