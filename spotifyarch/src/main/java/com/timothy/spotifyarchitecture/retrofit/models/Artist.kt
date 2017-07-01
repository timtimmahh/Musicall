package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.os.Parcel

/**
 * [Artist object model](https://developer.spotify.com/web-api/object-model/#artist-object-full)
 */

open class Artist(@Embedded(prefix = "follower") open val followers: Followers = Followers(), open val genres: List<String> = listOf(), open val images: List<Image> = listOf(), open val popularity: Int = 0, external_urls: Map<String, String> = mapOf(), href: String = "", id: String = "", name: String, type: String = "", uri: String = "") : ArtistSimple(external_urls, href, id, name, type, uri) {
    constructor(followers: Followers, genres: List<String>, images: List<Image>, popularity: Int, artistSimple: ArtistSimple) : this(followers, genres, images, popularity, artistSimple.external_urls, artistSimple.href, artistSimple.id, artistSimple.name, artistSimple.type, artistSimple.uri)
}