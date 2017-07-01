package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable

/**
 * [Copyright object model](https://developer.spotify.com/web-api/object-model/#copyright-object)
 */
class Copyright {
    var text: String = ""
    var type: String = ""

}
