package com.timothy.spotifyarchitecture.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FeaturedPlaylists {
    public String message;
    public Pager<PlaylistSimple> playlists;

    public FeaturedPlaylists() {
    }
}
