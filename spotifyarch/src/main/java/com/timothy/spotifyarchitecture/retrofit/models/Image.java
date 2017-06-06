package com.timothy.spotifyarchitecture.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#image-object">Image object model</a>
 */
public class Image {
    public Integer width;
    public Integer height;
    public String url;

    public Image() {
    }
}
