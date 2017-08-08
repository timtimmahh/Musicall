package com.timothy.spotifyarchitecture.retrofit.models

/**
 * [User object (private) model](https://developer.spotify.com/web-api/object-model/#user-object-private)
 */
data class UserPrivate(var birthdate: String = "", var country: String = "", var email: String = "", var product: String = "") : UserPublic()