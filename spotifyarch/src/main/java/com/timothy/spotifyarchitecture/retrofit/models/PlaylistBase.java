package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Base class for {@link Playlist} and
 * {@link PlaylistSimple}
 */
@Entity(primaryKeys = {"playlist_id"}, tableName = "spotify_playlists")
public abstract class PlaylistBase {
    public Boolean collaborative;
    public Map<String, String> external_urls;
    public String href;
    @ColumnInfo(name = "playlist_id")
    public String id;
    public List<Image> images;
    public String name;
    @Embedded
    public UserPublic owner;
    @SerializedName("public")
    public Boolean is_public;
    public String snapshot_id;
    public String type;
    public String uri;

    protected PlaylistBase() {
    }
}
