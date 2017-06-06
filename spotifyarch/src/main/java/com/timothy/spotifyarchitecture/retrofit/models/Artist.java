package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.os.Parcel;

import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#artist-object-full">Artist object model</a>
 */

public class Artist extends ArtistSimple {
    @Embedded
    public Followers followers;
    public List<String> genres;
    public List<Image> images;
    public Integer popularity;

    public Artist() {
    }
}