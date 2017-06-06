package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class ArtistSimple {
    public Map<String, String> external_urls;
    public String href;
    @ColumnInfo(name = "artist_id")
    public String id;
    public String name;
    public String type;
    public String uri;

    public ArtistSimple() {
    }
}
