package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable

open class ArtistSimple(open val external_urls: Map<String, String> = mapOf(), open val href: String = "", @ColumnInfo(name = "artist_id")open val id: String = "",
                        open val name: String = "", open val type: String = "", open val uri: String = "")
