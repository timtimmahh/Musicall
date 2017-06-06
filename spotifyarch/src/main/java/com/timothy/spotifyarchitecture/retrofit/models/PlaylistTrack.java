package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#playlist-track-object">Playlist track object model</a>
 */
@Entity(primaryKeys = {"track", "added_by"},
        tableName = "playlist_tracks",
        foreignKeys = @ForeignKey(entity = Playlist.class,
                                    parentColumns = "playlist_id",
                                    childColumns = "track",
                                    onDelete = ForeignKey.CASCADE))
public class PlaylistTrack {

    public String added_at;
    @Embedded
    public UserPublic added_by;
    @Embedded()
    public Track track;
    public Boolean is_local;

    public PlaylistTrack() {
    }

}
