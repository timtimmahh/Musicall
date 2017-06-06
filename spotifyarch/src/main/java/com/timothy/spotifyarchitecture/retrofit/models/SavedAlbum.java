package com.timothy.spotifyarchitecture.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.timothy.spotifyarchitecture.retrofit.models.Album;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#saved-album-object">Saved album object model</a>
 */
public class SavedAlbum {
    public String added_at;
    public Album album;

    public SavedAlbum() {
    }

}