package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable

/**
 * [Saved track object model](https://developer.spotify.com/web-api/object-model/#saved-track-object)
 */
class SavedTrack {
    var added_at: String = ""
    var track: Track = Track()
}
