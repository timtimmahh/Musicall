package com.timothy.musicall

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.timothy.spotifyarchitecture.entities.*
import com.timothy.spotifyarchitecture.room.SpotifyConverters
import com.timothy.spotifyarchitecture.room.SpotifyDao
import com.timothy.spotifyarchitecture.room.SpotifyDatabase

/**
 * Room database class
 */
@Database(entities = arrayOf(Token::class, SpotifyUser::class, SpotifyAlbum::class,
                             SpotifyArtist::class, SpotifyTrack::class), version = 1)
@TypeConverters(SpotifyConverters::class)
abstract class ServiceDatabase : RoomDatabase(), SpotifyDatabase {
	override abstract fun spotifyDao(): SpotifyDao
}