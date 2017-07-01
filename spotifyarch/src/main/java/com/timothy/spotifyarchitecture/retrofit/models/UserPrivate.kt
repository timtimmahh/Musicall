package com.timothy.spotifyarchitecture.retrofit.models

/**
 * [User object (private) model](https://developer.spotify.com/web-api/object-model/#user-object-private)
 */
open class UserPrivate(open val birthdate: String = "", open val country: String = "", open val email: String = "", open val product: String = "", override val display_name: String, override val external_urls: Map<String, String>, override val followers: Followers, override val href: String, override val id: String, override val images: List<Image>, override val type: String, override val uri: String) : UserPublic(display_name, external_urls, followers, href, id, images, type, uri) {
    constructor(birthdate: String, country: String, email: String, product: String, userPublic: UserPublic) : this(birthdate, country, email, product, userPublic.display_name, userPublic.external_urls, userPublic.followers, userPublic.href, userPublic.id, userPublic.images, userPublic.type, userPublic.uri)
}