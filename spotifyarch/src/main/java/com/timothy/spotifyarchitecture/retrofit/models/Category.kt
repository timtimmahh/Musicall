package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable

@Entity(primaryKeys = arrayOf("id"), tableName = "spotify_category")
class Category {
    var href: String = ""
    var icons: List<Image> = listOf()
    var id: String = ""
    var name: String = ""

}
