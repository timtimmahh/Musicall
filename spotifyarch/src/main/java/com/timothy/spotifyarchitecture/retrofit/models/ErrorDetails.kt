package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable

/**
 * [Error object model](https://developer.spotify.com/web-api/object-model/#error-object)
 */
class ErrorDetails {
    var status: Int = 0
    var message: String = ""
}
