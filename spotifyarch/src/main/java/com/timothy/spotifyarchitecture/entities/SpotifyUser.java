package com.timothy.spotifyarchitecture.entities;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.timothy.spotifyarchitecture.remote.Resource;
import com.timothy.spotifyarchitecture.retrofit.models.UserPrivate;
import com.timothy.spotifyarchitecture.retrofit.models.UserPublic;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by tim on 6/3/17.
 */
@Entity(tableName = "spotify_users", primaryKeys = {"user_id"},
        foreignKeys = @ForeignKey(entity = Token.class,
                parentColumns = "id", childColumns = "user_id", onDelete = CASCADE))
public class SpotifyUser extends UserPrivate {
    @Embedded
    public Token token;


    public static SpotifyUser createSpotifyUser(UserPublic userPublic, @NonNull LiveData<Resource<Token>> token) {
        SpotifyUser user = new SpotifyUser();
        user.display_name = userPublic.display_name;
        user.external_urls = userPublic.external_urls;
        user.followers = userPublic.followers;
        user.href = userPublic.href;
        user.id = userPublic.id;
        user.images = userPublic.images;
        user.type = userPublic.type;
        user.uri = userPublic.uri;
        if (userPublic instanceof UserPrivate) {
            user.birthdate = ((UserPrivate)userPublic).birthdate;
            user.country = ((UserPrivate)userPublic).country;
            user.email = ((UserPrivate)userPublic).email;
            user.product = ((UserPrivate)userPublic).product;
        }
        token.getValue().data.id = user.id;
        user.token = token.getValue().data;
        user.token.id = user.id;
        return user;
    }

}
