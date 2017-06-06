package com.timothy.spotifyarchitecture.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#saved-track-object">Saved track object model</a>
 */
public class SavedTrack {
    public String added_at;
    public Track track;

    public SavedTrack() {
    }
}
