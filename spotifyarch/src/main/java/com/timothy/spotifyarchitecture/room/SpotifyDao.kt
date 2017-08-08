@file:Suppress("unused")

package com.timothy.spotifyarchitecture.room

import android.arch.persistence.room.*
import com.timothy.spotifyarchitecture.entities.*

/**
 * Dao for Room Database
 */
@Dao
interface SpotifyDao {
	//Token dao methods
	@Query("SELECT * FROM token WHERE id=:arg0 OR user_id=:arg0 OR access_token=:arg1")
	fun loadAuthToken(id: String, authToken: String): Token
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun saveToken(token: Token)
	
	@Update(onConflict = OnConflictStrategy.REPLACE)
	fun updateToken(token: Token)
	
	
	//User dao methods
	@Query("SELECT * FROM spotify_users WHERE id=:arg0 OR access_token=:arg1")
	fun loadMe(id: String, authToken: String): SpotifyUser
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun saveMe(spotifyUser: SpotifyUser)
	
	@Update(onConflict = OnConflictStrategy.REPLACE)
	fun updateMe(spotifyUser: SpotifyUser)


//    @Query("SELECT * FROM spotify_user_albums INNER JOIN spotify_albums ON userId=:arg0 AND spotify_user_albums.albumId=spotify_albums.id")
//    fun loadMySavedAlbums(userId: String): List<SpotifyUserAlbums>
	
	
	//Album dao methods
	@Query("SELECT * FROM spotify_albums WHERE id=:arg0")
	fun loadMyAlbum(id: String): SpotifyAlbum
	
	@Query("SELECT * FROM spotify_albums")
	fun loadMyAlbums(): List<SpotifyAlbum>
	
	@Update(onConflict = OnConflictStrategy.REPLACE)
	fun updateMyAlbum(spotifyAlbum: SpotifyAlbum)
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAlbums(albums: List<SpotifyAlbum>)
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAlbum(album: SpotifyAlbum)
	
	
	//Track dao methods
	@Query("SELECT * FROM spotify_tracks WHERE id=:arg0")
	fun loadMyTrack(id: String): SpotifyTrack
	
	@Query("SELECT * FROM spotify_tracks")
	fun loadMyTracks(): List<SpotifyTrack>
	
	@Update(onConflict = OnConflictStrategy.REPLACE)
	fun updateMyTrack(spotifyTrack: SpotifyTrack)
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertTracks(tracks: List<SpotifyTrack>)
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertTrack(track: SpotifyTrack)
	
	
	//Artist dao methods
	@Query("SELECT * FROM spotify_artists WHERE id=:arg0")
	fun loadArtist(id: String): SpotifyArtist
	
	@Query("SELECT * FROM spotify_artists")
	fun loadMyArtists(): List<SpotifyArtist>
	
	@Update(onConflict = OnConflictStrategy.REPLACE)
	fun updateMyArtist(spotifyArtist: SpotifyArtist)
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertArtists(artists: List<SpotifyArtist>)
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertArtist(artist: SpotifyArtist)
	
}