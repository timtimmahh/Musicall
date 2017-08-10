package com.timmahh.spotifyweb.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.timmahh.spotifyweb.retrofit.models.*

/**
 * Room Entity class represents a table in an SQL database
 */
@Entity(tableName = "spotify_users")
data class SpotifyUser(@Embedded var token: Token = Token(),
                       var birthdate: String = "",
                       var country: String = "",
                       var email: String = "",
                       var product: String = "",
                       @ColumnInfo(name = "display_name")
                       var displayName: String = "",
                       @ColumnInfo(name = "external_urls")
                       var externalUrls: Map<String, String> = mapOf(),
                       @Embedded(prefix = "follower") var followers: Followers = Followers(),
                       var href: String = "",
                       @PrimaryKey(autoGenerate = false)
                       var id: String = "",
                       var images: List<Image> = listOf(),
                       var type: String = "",
                       var uri: String = "") {
	constructor(token: Token, userPrivate: UserPrivate) : this(token, userPrivate.birthdate, userPrivate.country, userPrivate.email, userPrivate.product, userPrivate.display_name ?: "", userPrivate.external_urls, userPrivate.followers, userPrivate.href, userPrivate.id, userPrivate.images, userPrivate.type, userPrivate.uri)
	constructor() : this(Token(), "", "", "", "", "", mapOf(), Followers(), "", "", listOf(), "", "")
}

/**
 * SpotifyArtist entity object
 */
@Entity(tableName = "spotify_artists")
data class SpotifyArtist(@ColumnInfo(name = "external_urls")
                         var externalUrls: Map<String, String> = mapOf(),
                         @Embedded(prefix = "followers")
                         var followers: Followers = Followers(),
                         var genres: List<String> = listOf(),
                         var href: String = "",
                         @PrimaryKey(autoGenerate = false)
                         var id: String = "",
                         var images: List<Image> = listOf(),
                         var name: String = "",
                         var popularity: Int = 0,
                         var type: String = "",
                         var uri: String = "") {
	constructor(artist: Artist) : this(artist.external_urls, artist.followers, artist.genres, artist.href, artist.id, artist.images, artist.name, artist.popularity, artist.type, artist.uri)
	constructor() : this(mapOf(), Followers(), listOf(), "", "", listOf(), "", 0, "", "")
}

/**
 * SpotifyAlbum entity object
 */
@Entity(tableName = "spotify_albums")
data class SpotifyAlbum(@ColumnInfo(name = "album_type")
                        var albumType: String = "",
                        @ColumnInfo(name = "available_markets")
                        var availableMarkets: List<String> = listOf(),
                        var copyrights: List<Copyright> = listOf(),
                        @ColumnInfo(name = "external_ids")
                        var externalIds: Map<String, String> = mapOf(),
                        @ColumnInfo(name = "external_urls")
                        var externalUrls: Map<String, String> = mapOf(),
                        var genres: List<String> = listOf(),
                        var href: String = "",
                        @PrimaryKey(autoGenerate = false)
                        var id: String = "",
                        var images: List<Image> = listOf(),
                        var label: String = "",
                        var name: String = "",
                        var popularity: Int = 0,
                        @ColumnInfo(name = "release_date")
                        var releaseDate: String = "",
                        @ColumnInfo(name = "release_date_precision")
                        var releaseDatePrecision: String = "",
                        var type: String = "",
                        var uri: String = "") {
	constructor(album: Album) : this(album.album_type, album.available_markets, album.copyrights, album.external_ids, album.external_urls, album.genres, album.href, album.id, album.images, "", album.name, album.popularity, album.release_date, album.release_date_precision, album.type, album.uri)
	constructor() : this("", listOf(), listOf(), mapOf(), mapOf(), listOf(), "", "", listOf(), "", "", 0, "", "", "", "")
}

/**
 * Room Entity class represents a table in an SQL database
 */
@Entity(tableName = "spotify_tracks")
data class SpotifyTrack(@ColumnInfo(name = "available_markets")
                        var availableMarkets: List<String> = listOf(),
                        @ColumnInfo(name = "disc_number")
                        var discNumber: Int = 0,
                        @ColumnInfo(name = "duration_ms")
                        var durationMs: Long = 0,
                        var explicit: Boolean = false,
                        @ColumnInfo(name = "external_ids")
                        var externalIds: Map<String, String> = mapOf(),
                        @ColumnInfo(name = "external_urls")
                        var externalUrls: Map<String, String> = mapOf(),
                        var href: String = "",
                        @PrimaryKey(autoGenerate = false)
                        var id: String = "",
                        @ColumnInfo(name = "is_playable")
                        var isPlayable: Boolean = false,
                        @Embedded(prefix = "linked_")
                        var linkedFrom: LinkedTrack = LinkedTrack(),
                        var name: String = "",
                        var popularity: Int = 0,
                        @ColumnInfo(name = "preview_url")
                        var previewUrl: String = "",
                        @ColumnInfo(name = "track_number")
                        var trackNumber: Int = 0,
                        var type: String = "",
                        var uri: String = "") {
	constructor(track: Track) : this(track.available_markets, track.disc_number, track.duration_ms, track.explicit, track.external_ids, track.external_urls, track.href, track.id, track.is_playable, track.linked_from, track.name, track.popularity, track.preview_url ?: "", 0, track.type, track.uri)
	constructor() : this(listOf(), 0, 0, false, mapOf(), mapOf(), "", "", false, LinkedTrack(), "", 0, "", 0, "", "")
}