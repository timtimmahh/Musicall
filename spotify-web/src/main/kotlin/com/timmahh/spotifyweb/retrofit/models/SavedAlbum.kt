package com.timmahh.spotifyweb.retrofit.models

/**
 * [Saved album object model](https://developer.spotify.com/web-api/object-model/#saved-album-object)
 */
class SavedAlbum {
    var added_at: String = ""
    var album: Album = Album()
}