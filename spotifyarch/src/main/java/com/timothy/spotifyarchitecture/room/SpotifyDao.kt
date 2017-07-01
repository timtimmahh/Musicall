package com.timothy.spotifyarchitecture.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import com.timothy.spotifyarchitecture.entities.SpotifyAlbum
import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.retrofit.models.Pager
import com.timothy.spotifyarchitecture.entities.Token

/**
 * Created by tim on 5/31/17.
 */
@Dao
interface SpotifyDao {

    @Query("SELECT * FROM token WHERE _id=:arg0 OR userId=:arg0")
    fun loadAuthToken(id: String): LiveData<Token>

    @Query("SELECT * FROM token WHERE access_token=:arg0")
    fun loadAuthTokenByToken(authToken: String): LiveData<Token>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveToken(token: Token)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateToken(token: Token)

    @Query("SELECT * FROM spotify_users WHERE id=:arg0")
    fun loadMe(id: String): LiveData<SpotifyUser>

    @Query("SELECT * FROM spotify_users WHERE access_token=:arg0")
    fun loadMeByToken(authToken: String): LiveData<SpotifyUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMe(spotifyUser: SpotifyUser)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMe(spotifyUser: SpotifyUser)

}
//BIGSPIN20