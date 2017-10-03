package com.timmahh.spotifyweb.retrofit.models

import android.arch.persistence.room.Entity

@Entity(primaryKeys = arrayOf("id"), tableName = "spotify_category")
class Category {
    var href: String = ""
    var icons: List<Image> = listOf()
    var id: String = ""
    var name: String = ""

}
