package com.timothy.spotifyarchitecture.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore
import com.timothy.spotifyarchitecture.retrofit.models.*

/**
 * Created by tim on 6/3/17.
 */
@Entity(tableName = "spotify_albums", primaryKeys = arrayOf("album_id"), inheritSuperIndices = true, foreignKeys = arrayOf(ForeignKey(entity = SpotifyArtist::class, parentColumns = arrayOf("artist_id"), childColumns = arrayOf("userId"))))
data class SpotifyAlbum(var added_at: String = "", var userId: String = "", @Ignore var artists: List<ArtistSimple> = listOf(), @Embedded(prefix = "copyright") var copyrights: List<Copyright> = listOf(), var external_ids: Map<String, String> = mapOf(), @Embedded(prefix = "genre")var genres: List<String> = listOf(), var popularity: Int = 0, var release_date: String = "", var release_date_precision: String = "", @Ignore var tracks: Pager<TrackSimple> = Pager<TrackSimple>(), var album_type: String = "", @Embedded(prefix = "markets") var available_markets: List<String> = listOf(), var external_urls: Map<String, String> = mapOf(), var href: String = "", var id: String = "", var images: List<Image> = listOf(), var name: String = "", var type: String = "", var uri: String = "") {
    constructor(added_at: String, userId: String, album: Album) : this(added_at, userId, album.artists, album.copyrights, album.external_ids, album.genres, album.popularity, album.release_date, album.release_date_precision, album.tracks, album.album_type, album.available_markets, album.external_urls, album.href, album.id, album.images, album.name,album.type, album.uri)
}
