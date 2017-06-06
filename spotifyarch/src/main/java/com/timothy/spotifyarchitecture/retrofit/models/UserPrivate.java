package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Entity;
import android.os.Parcel;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#user-object-private">User object (private) model</a>
 */
public class UserPrivate extends UserPublic {
    public String birthdate;
    public String country;
    public String email;
    public String product;

    public UserPrivate() {
    }
}
