package com.timmahh.spotifyweb.retrofit.models

import android.arch.persistence.room.ColumnInfo

open class AlbumSimple(open val album_type: String = "", open val available_markets: List<String> = listOf(), open val external_urls: Map<String, String> = mapOf(),
                       open val href: String = "", @ColumnInfo(name = "album_id") open val id: String = "", open val images: List<Image> = listOf(), open val name: String = "", open val type: String = "", open val uri: String = "")