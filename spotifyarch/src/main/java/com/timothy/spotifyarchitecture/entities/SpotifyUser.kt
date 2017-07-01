package com.timothy.spotifyarchitecture.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

import com.timothy.spotifyarchitecture.retrofit.models.UserPrivate

import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index
import com.timothy.spotifyarchitecture.retrofit.models.Followers
import com.timothy.spotifyarchitecture.retrofit.models.Image

/**
 * Created by tim on 6/3/17.
 */
@Entity(tableName = "spotify_users", primaryKeys = arrayOf("id"), indices = arrayOf(Index(value = *arrayOf("access_token", "id"), unique = true)), foreignKeys = arrayOf(ForeignKey(entity = Token::class,
        parentColumns = arrayOf("access_token", "userId"), childColumns = arrayOf("access_token", "id"), onDelete = CASCADE)))
data class SpotifyUser constructor(var access_token: String = "", var birthdate: String = "", var country: String = "", var email: String = "", var product: String = "", var display_name: String = "", var external_urls: Map<String, String> = mapOf(), @Embedded(prefix = "follower")var followers: Followers = Followers(), var href: String = "", var id: String = "", var images: List<Image> = listOf(), var type: String = "", var uri: String = ""){
    constructor(access_token: String, userPrivate: UserPrivate) : this(access_token, userPrivate.birthdate, userPrivate.country, userPrivate.email, userPrivate.product, userPrivate.display_name, userPrivate.external_urls, userPrivate.followers, userPrivate.href, userPrivate.id, userPrivate.images, userPrivate.type, userPrivate.uri)
}