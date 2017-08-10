package com.timothy.musicall

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.timothy.spotifyarchitecture.entities.SpotifyAlbum
import com.timothy.spotifyarchitecture.entities.SpotifyArtist
import com.timothy.spotifyarchitecture.entities.SpotifyTrack
import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.room.SpotifyConverters
import com.timothy.spotifyarchitecture.room.SpotifyDao
import com.timothy.spotifyarchitecture.room.SpotifyDatabase

/**
 * Room database class
 */
@Database(entities = arrayOf(SpotifyUser::class, SpotifyAlbum::class,
		SpotifyArtist::class, SpotifyTrack::class), version = 3)
@TypeConverters(SpotifyConverters::class)
abstract class ServiceDatabase : RoomDatabase(), SpotifyDatabase {
	override abstract fun spotifyDao(): SpotifyDao
}