package com.timothy.spotifyarchitecture.retrofit.models

/**
 * [Paging object model](https://developer.spotify.com/web-api/object-model/#paging-object)

 * @param <T> expected object that is paged
</T> */
class Pager<T> {
    var href: String = ""
    var items: List<T> = listOf()
    var limit: Int = 0
	var next: String? = ""
    var offset: Int = 0
	var previous: String? = ""
    var total: Int = 0
}
