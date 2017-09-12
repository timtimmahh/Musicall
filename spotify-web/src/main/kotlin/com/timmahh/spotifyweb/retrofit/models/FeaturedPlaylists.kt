package com.timmahh.spotifyweb.retrofit.models

class FeaturedPlaylists {
    var message: String = ""
    var playlists: Pager<PlaylistSimple> = Pager<PlaylistSimple>()
}
