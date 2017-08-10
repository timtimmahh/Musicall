package com.timmahh.musicall

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.timmahh.spotifyweb.entities.SpotifyAlbum
import com.timmahh.spotifyweb.entities.SpotifyArtist
import com.timmahh.spotifyweb.entities.SpotifyTrack
import com.timmahh.spotifyweb.entities.SpotifyUser
import com.timmahh.spotifyweb.room.SpotifyConverters
import com.timmahh.spotifyweb.room.SpotifyDao
import com.timmahh.spotifyweb.room.SpotifyDatabase

/**
 * Room database class
 */
@Database(entities = arrayOf(SpotifyUser::class, SpotifyAlbum::class,
		SpotifyArtist::class, SpotifyTrack::class), version = 3)
@TypeConverters(SpotifyConverters::class)
abstract class ServiceDatabase : RoomDatabase(), SpotifyDatabase {
	override abstract fun spotifyDao(): SpotifyDao
}