package com.timothy.spotifyarchitecture.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaylistFollowPrivacy {
    @SerializedName("public")
    public Boolean is_public;

    public PlaylistFollowPrivacy() {
    }
}
