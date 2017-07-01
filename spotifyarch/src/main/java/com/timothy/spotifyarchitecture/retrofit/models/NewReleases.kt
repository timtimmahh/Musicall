package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable

import com.timothy.spotifyarchitecture.retrofit.models.AlbumSimple

class NewReleases {
    var albums: Pager<AlbumSimple> = Pager<AlbumSimple>()
}
