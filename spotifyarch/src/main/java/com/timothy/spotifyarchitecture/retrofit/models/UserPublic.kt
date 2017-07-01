package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * [User object (public) model](https://developer.spotify.com/web-api/object-model/#user-object-public)
 */
open class UserPublic(open val display_name: String = "", open val external_urls: Map<String, String> = mapOf(), @Embedded(prefix = "follower") open val followers: Followers = Followers(),
                      open val href: String = "", open val id: String = "", open val images: List<Image> = listOf(), open val type: String = "", open val uri: String = "")
