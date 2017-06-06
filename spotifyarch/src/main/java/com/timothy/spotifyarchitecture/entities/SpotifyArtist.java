package com.timothy.spotifyarchitecture.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Relation;

import com.timothy.spotifyarchitecture.retrofit.models.Artist;

import java.util.List;

/**
 * Created by tim on 6/3/17.
 */
@Entity(tableName = "spotify_artists", primaryKeys = {"artist_id"},
        foreignKeys = @ForeignKey(entity = SpotifyUser.class, parentColumns = "user_id", childColumns = "user_id"))
public class SpotifyArtist extends Artist {
    @ColumnInfo(name = "user_id")
    public String userId;
}
