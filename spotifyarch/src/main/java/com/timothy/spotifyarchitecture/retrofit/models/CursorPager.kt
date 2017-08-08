package com.timothy.spotifyarchitecture.retrofit.models

/**
 * [Cursor-based paging object model](https://developer.spotify.com/web-api/object-model/#cursor-based-paging-object)

 * @param <T> expected object that is paged
</T> */
class CursorPager<T> {
    var href: String = ""
    var items: List<T> = listOf()
    var limit: Int = 0
	var next: String? = ""
    var cursors: Cursor? = null
    var total: Int = 0
}
