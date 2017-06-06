package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;
@Entity(primaryKeys = {"track_id"}, tableName = "spotify_tracks")
public class TrackSimple {
    @Relation(parentColumn = "track_id", entityColumn = "artist_id")
    public List<ArtistSimple> artists;
    public List<String> available_markets;
    public Boolean is_playable;
    @Embedded
    public LinkedTrack linked_from;
    public int disc_number;
    public long duration_ms;
    public Boolean explicit;
    public Map<String, String> external_urls;
    public String href;
    @ColumnInfo(name = "track_id")
    public String id;
    public String name;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;

    public TrackSimple() {
    }
}
