package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

/**
 * [Playlist track object model](https://developer.spotify.com/web-api/object-model/#playlist-track-object)
 */
@Entity(primaryKeys = arrayOf("track", "added_by"), tableName = "playlist_tracks", foreignKeys = arrayOf(ForeignKey(entity = Playlist::class, parentColumns = arrayOf("playlist_id"), childColumns = arrayOf("track"), onDelete = ForeignKey.CASCADE)))
class PlaylistTrack(val added_at: String? = "", val added_by: UserPublic? = UserPublic(), val track: Track = Track(), val is_local: Boolean = false)