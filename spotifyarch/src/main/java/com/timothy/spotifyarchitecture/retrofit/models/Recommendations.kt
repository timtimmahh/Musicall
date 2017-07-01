package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable

class Recommendations {

    var seeds: List<Seed> = listOf()

    var tracks: List<Track> = listOf()
}
