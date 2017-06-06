package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-full">Playlist object model</a>
 */
@Entity(primaryKeys = {"playlist_id"}, tableName = "spotify_playlists")
public class Playlist extends PlaylistBase {
    public String description;
    @Embedded
    public Followers followers;
    @Relation(parentColumn = "playlist_id", entityColumn = "track")
    public Pager<PlaylistTrack> tracks;

    public Playlist() {
    }
}