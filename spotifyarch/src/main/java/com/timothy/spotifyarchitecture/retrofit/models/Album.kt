package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.Relation

/**
 * [Album object model](https://developer.spotify.com/web-api/object-model/#album-object-full)
 */
open class Album(@Relation(parentColumn = "album_id", entityColumn = "artist_id") open val artists: List<ArtistSimple> = listOf(), open val copyrights: List<Copyright> = listOf(), open val external_ids: Map<String, String> = mapOf(),
                 open val genres: List<String> = listOf(), open val popularity: Int = 0, open val release_date: String = "", open val release_date_precision: String = "", @Relation(parentColumn = "album_id", entityColumn = "track_id")open val tracks: Pager<TrackSimple> = Pager<TrackSimple>(), album_type: String = "", available_markets: List<String> = listOf(), external_urls: Map<String, String> = mapOf(), href: String = "", id: String = "", images: List<Image> = listOf(), name: String = "", type: String = "", uri: String = "") : AlbumSimple(album_type, available_markets, external_urls, href, id, images, name, type, uri) {
    constructor(artists: List<ArtistSimple>, copyrights: List<Copyright>, external_ids: Map<String, String>, genres: List<String>, popularity: Int, release_date: String, release_date_precision: String, tracks: Pager<TrackSimple>, albumSimple: AlbumSimple) : this(artists, copyrights, external_ids, genres, popularity, release_date, release_date_precision, tracks, albumSimple.album_type, albumSimple.available_markets, albumSimple.external_urls, albumSimple.href, albumSimple.id, albumSimple.images, albumSimple.name, albumSimple.type, albumSimple.uri)
}