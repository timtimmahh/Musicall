package com.timothy.musicall

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.entities.Token
import com.timothy.spotifyarchitecture.room.CustomTypeConverter
import com.timothy.spotifyarchitecture.room.SpotifyDao

/**
 * Room database class
 */
@Database(entities = arrayOf(Token::class, SpotifyUser::class), version = 1)
@TypeConverters(CustomTypeConverter::class)
abstract class ServiceDatabase : RoomDatabase() {
    abstract fun spotifyDao(): SpotifyDao
}
