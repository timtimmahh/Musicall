package com.timothy.spotifyarchitecture.entities

import android.arch.persistence.room.*

/**
 * Entity and Pojo used to create a many to many relationship since Room doesn't support it.
 */
@Entity(tableName = "spotify_user_albums", indices = arrayOf(Index(value = "user_id", unique = true)))
data class SpotifyUserAlbums(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                             var userId: String = "",
                             var albumId: String = "")

data class SpotifyUserSavedAlbums(@Embedded var user: SpotifyUser = SpotifyUser(),
                                  @Relation(entity = SpotifyUserAlbums::class,
                                            parentColumn = "id",
		                                  entityColumn = "user_id")
                                  var album: SpotifyAlbum)