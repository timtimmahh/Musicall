package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.os.Parcel;

import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#track-object-full">Track object model</a>
 */
public class Track extends TrackSimple {
    @Embedded
    public AlbumSimple album;
    public Map<String, String> external_ids;
    public Integer popularity;

    public Track() {
    }
}