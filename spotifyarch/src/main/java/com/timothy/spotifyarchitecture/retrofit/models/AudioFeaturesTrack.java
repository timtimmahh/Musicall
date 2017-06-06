package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#audio-features-object">Audio Features Object</a>
 */
@Entity(primaryKeys = {"feature_id"},
        tableName = "spotify_audio_features",
        foreignKeys = @ForeignKey(entity = Track.class,
        parentColumns = "track_id",
        childColumns = "feature_id"))
public class AudioFeaturesTrack {

    public float acousticness;
    public String analysis_url;
    public float danceability;
    public int duration_ms;
    public float energy;
    @ColumnInfo(name = "feature_id")
    public String id;
    public float instrumentalness;
    public int key;
    public float liveness;
    public float loudness;
    public int mode;
    public float speechiness;
    public float tempo;
    public int time_signature;
    public String track_href;
    public String type;
    public String uri;
    public float valence;

    public AudioFeaturesTrack() {
    }
}
