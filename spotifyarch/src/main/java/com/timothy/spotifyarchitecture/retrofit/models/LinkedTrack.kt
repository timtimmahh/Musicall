package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable


class LinkedTrack {
    var external_urls: Map<String, String> = mapOf()
    var href: String = ""
    var id: String = ""
    var type: String = ""
    var uri: String = ""
}
