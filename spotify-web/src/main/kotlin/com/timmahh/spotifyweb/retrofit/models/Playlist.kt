package com.timmahh.spotifyweb.retrofit.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Relation

/**
 * [Playlist object model](https://developer.spotify.com/web-api/object-model/#playlist-object-full)
 */
@Entity(primaryKeys = arrayOf("playlist_id"), tableName = "spotify_playlists")
class Playlist(var description: String? = "", val followers: Followers = Followers(), @Relation(parentColumn = "playlist_id", entityColumn = "track") val tracks: Pager<PlaylistTrack> = Pager<PlaylistTrack>(), collaborative: Boolean = false, external_urls: Map<String, String> = mapOf(), href: String = "", id: String = "", images: List<Image> = listOf(), name: String = "", owner: UserPublic = UserPublic(), is_public: Boolean? = false, snapshot_id: String = "", type: String = "", uri: String = "") : PlaylistBase(collaborative, external_urls, href, id, images, name, owner, is_public, snapshot_id, type, uri) {
    constructor(description: String, followers: Followers, tracks: Pager<PlaylistTrack>, playlistBase: PlaylistBase) : this(description, followers, tracks, playlistBase.collaborative, playlistBase.external_urls, playlistBase.href, playlistBase.id, playlistBase.images, playlistBase.name, playlistBase.owner, playlistBase.is_public, playlistBase.snapshot_id, playlistBase.type, playlistBase.uri)
}