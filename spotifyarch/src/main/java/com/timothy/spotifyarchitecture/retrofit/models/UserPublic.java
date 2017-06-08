package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#user-object-public">User object (public) model</a>
 */
public class UserPublic {
    public String display_name;
    public Map<String, String> external_urls;
    @Embedded(prefix = "follower")
    public Followers followers;
    public String href;
    public String id;
    public List<Image> images;
    public String type;
    public String uri;

    public UserPublic() {
    }

}
