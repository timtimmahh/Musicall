package com.timmahh.spotifyweb.retrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * Base class for [Playlist] and
 * [PlaylistSimple]
 */
@Entity(primaryKeys = arrayOf("playlist_id"), tableName = "spotify_playlists")
abstract class PlaylistBase(open var collaborative: Boolean = false, open val external_urls: Map<String, String> = mapOf(), open val href: String = "",
                            @ColumnInfo(name = "playlist_id") open val id: String = "", open val images: List<Image> = listOf(), open val name: String = "",
                            open val owner: UserPublic = UserPublic(), @SerializedName("public") open val is_public: Boolean? = false, open val snapshot_id: String = "", open val type: String = "", open val uri: String = "")
