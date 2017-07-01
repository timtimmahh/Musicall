package com.timothy.spotifyarchitecture.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

import com.google.gson.Gson

import android.arch.persistence.room.ForeignKey.CASCADE

/**
 * Created by tim on 4/14/17.
 */
@Entity(tableName = "token", indices = arrayOf(Index(value = *arrayOf("access_token", "userId"), unique = true)))
data class Token(var access_token: String = "", var userId: String = "", var token_type: String = "", var scope: String = "", var expires_in: Long = 0, var refresh_token: String = ""){
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0

}
