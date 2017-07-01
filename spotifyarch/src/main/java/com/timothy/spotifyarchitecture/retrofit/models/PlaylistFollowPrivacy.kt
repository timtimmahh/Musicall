package com.timothy.spotifyarchitecture.retrofit.models

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

class PlaylistFollowPrivacy {
    @SerializedName("public")
    var is_public: Boolean? = false
}
