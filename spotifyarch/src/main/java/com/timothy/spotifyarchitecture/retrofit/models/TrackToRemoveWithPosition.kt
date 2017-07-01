package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable

import java.util.ArrayList

class TrackToRemoveWithPosition {
    var uri: String = ""
    var positions: List<Int> = listOf()
}
