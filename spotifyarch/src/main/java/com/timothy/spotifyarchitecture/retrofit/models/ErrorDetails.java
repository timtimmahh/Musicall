package com.timothy.musicall.services.spotify.core.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#error-object">Error object model</a>
 */
public class ErrorDetails {
    public int status;
    public String message;

    public ErrorDetails() {
    }
}
