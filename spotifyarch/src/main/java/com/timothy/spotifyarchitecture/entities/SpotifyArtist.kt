package com.timothy.spotifyarchitecture.entities

import android.arch.persistence.room.*

import com.timothy.spotifyarchitecture.retrofit.models.Artist
import com.timothy.spotifyarchitecture.retrofit.models.Followers
import com.timothy.spotifyarchitecture.retrofit.models.Image

/**
 * Created by tim on 6/3/17.
 */
@Entity(tableName = "spotify_artists", primaryKeys = arrayOf("artist_id"), foreignKeys = arrayOf(ForeignKey(entity = SpotifyUser::class, parentColumns = arrayOf("user_id"), childColumns = arrayOf("user_id"))))
data class SpotifyArtist(@ColumnInfo(name = "user_id") var userId: String = "", @Embedded(prefix = "follower")var followers: Followers = Followers(), @Embedded(prefix = "genre")var genres: List<String> = listOf(), var images: List<Image> = listOf(), var popularity: Int = 0, var external_urls: Map<String, String> = mapOf(), var href: String = "", var id: String = "", var name: String = "", var type: String = "", var uri: String = "") {
    constructor(userId: String, artist: Artist) : this(userId, artist.followers, artist.genres, artist.images, artist.popularity, artist.external_urls, artist.href, artist.id, artist.name, artist.type, artist.uri)
}
