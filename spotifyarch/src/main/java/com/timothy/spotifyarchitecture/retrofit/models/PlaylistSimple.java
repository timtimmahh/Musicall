package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Embedded;
import android.os.Parcel;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-object-simplified">Playlist object model (simplified)</a>
 */
public class PlaylistSimple extends PlaylistBase {
    @Embedded
    public PlaylistTracksInformation tracks;

    public PlaylistSimple() {
    }
}
