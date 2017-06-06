package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class AlbumSimple {
    public String album_type;
    public List<String> available_markets;
    public Map<String, String> external_urls;
    public String href;
    @ColumnInfo(name = "album_id")
    public String id;
    public List<Image> images;
    public String name;
    public String type;
    public String uri;

    public AlbumSimple() {
    }

}
