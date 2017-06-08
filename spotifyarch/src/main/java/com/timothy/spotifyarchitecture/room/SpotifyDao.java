package com.timothy.spotifyarchitecture.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.timothy.spotifyarchitecture.entities.SpotifyAlbum;
import com.timothy.spotifyarchitecture.entities.SpotifyUser;
import com.timothy.spotifyarchitecture.retrofit.models.Pager;
import com.timothy.spotifyarchitecture.entities.Token;

import java.util.List;

/**
 * Created by tim on 5/31/17.
 */
@Dao
public interface SpotifyDao {

    @Query("SELECT * FROM token")
    LiveData<List<Token>> loadAuthToken();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveToken(Token token);

    @Query("SELECT * FROM spotify_users WHERE id=:arg0")
    LiveData<SpotifyUser> loadMe(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMe(SpotifyUser spotifyUser);

}
//BIGSPIN20