package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable

class FeaturedPlaylists {
    var message: String = ""
    var playlists: Pager<PlaylistSimple> = Pager<PlaylistSimple>()
}
