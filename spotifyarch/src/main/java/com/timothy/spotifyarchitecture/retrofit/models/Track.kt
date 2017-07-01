package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.os.Parcel

/**
 * [Track object model](https://developer.spotify.com/web-api/object-model/#track-object-full)
 */
open class Track(open val album: AlbumSimple = AlbumSimple(), open val external_ids: Map<String, String> = mapOf(), open val popularity: Int = 0, artists: List<ArtistSimple> = listOf(), available_markets: List<String> = listOf(), is_playable: Boolean = false, linked_from: LinkedTrack = LinkedTrack(), disc_number: Int = 0, duration_ms: Long = 0, explicit: Boolean = false, external_urls: Map<String, String> = mapOf(), href: String = "", id: String = "", name: String = "", preview_url: String = "", type: String = "", uri: String = "") : TrackSimple(artists, available_markets, is_playable, linked_from, disc_number, duration_ms, explicit, external_urls, href, id, name, preview_url, type, uri) {
}