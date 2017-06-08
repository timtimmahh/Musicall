package com.timothy.spotifyarchitecture.entities;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.timothy.spotifyarchitecture.remote.Resource;
import com.timothy.spotifyarchitecture.retrofit.models.UserPrivate;
import com.timothy.spotifyarchitecture.retrofit.models.UserPublic;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by tim on 6/3/17.
 */
@Entity(tableName = "spotify_users", primaryKeys = {"id"},
        foreignKeys = @ForeignKey(entity = Token.class,
                parentColumns = "access_token", childColumns = "access_token", onDelete = CASCADE))
public class SpotifyUser extends UserPrivate {
    @ColumnInfo(index = true)
    public String access_token;

    public SpotifyUser() {

    }

    public SpotifyUser(UserPublic userPublic) {
        this.display_name = userPublic.display_name;
        this.external_urls = userPublic.external_urls;
        this.followers = userPublic.followers;
        this.href = userPublic.href;
        this.id = userPublic.id;
        this.images = userPublic.images;
        this.type = userPublic.type;
        this.uri = userPublic.uri;
        if (userPublic instanceof UserPrivate) {
            this.birthdate = ((UserPrivate)userPublic).birthdate;
            this.country = ((UserPrivate)userPublic).country;
            this.email = ((UserPrivate)userPublic).email;
            this.product = ((UserPrivate)userPublic).product;
        }
    }

}
