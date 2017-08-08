package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.Embedded

/**
 * [User object (public) model](https://developer.spotify.com/web-api/object-model/#user-object-public)
 */
open class UserPublic(var display_name: String? = "", var external_urls: Map<String, String> = mapOf(), @Embedded(prefix = "follower") var followers: Followers = Followers(),
                      var href: String = "", var id: String = "", var images: List<Image> = listOf(), var type: String = "", var uri: String = "")
