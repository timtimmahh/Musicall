package com.timmahh.spotifyweb.retrofit.models

/**
 * [Saved track object model](https://developer.spotify.com/web-api/object-model/#saved-track-object)
 */
class SavedTrack {
    var added_at: String = ""
    var track: Track = Track()
}
