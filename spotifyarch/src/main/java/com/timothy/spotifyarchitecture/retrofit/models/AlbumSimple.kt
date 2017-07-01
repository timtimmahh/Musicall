package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

open class AlbumSimple(open val album_type: String = "", open val available_markets: List<String> = listOf(), open val external_urls: Map<String, String> = mapOf(),
                       open val href: String = "", @ColumnInfo(name = "album_id") open val id: String = "", open val images: List<Image> = listOf(), open val name: String = "", open val type: String = "", open val uri: String = "") {

}