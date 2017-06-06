package com.timothy.spotifyarchitecture.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.timothy.spotifyarchitecture.retrofit.models.Album;

/**
 * Created by tim on 6/3/17.
 */
@Entity(tableName = "spotify_albums",
        primaryKeys = {"album_id"},
        inheritSuperIndices = true,
        foreignKeys = @ForeignKey(entity = SpotifyArtist.class,
                parentColumns = "artist_id",
                childColumns = "userId"))
public class SpotifyAlbum extends Album {
    public String added_at;
    public String userId;
}
