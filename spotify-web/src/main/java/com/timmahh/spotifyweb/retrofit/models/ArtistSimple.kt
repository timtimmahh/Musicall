package com.timmahh.spotifyweb.retrofit.models

import android.arch.persistence.room.ColumnInfo

open class ArtistSimple(open val external_urls: Map<String, String> = mapOf(), open val href: String = "", @ColumnInfo(name = "artist_id")open val id: String = "",
                        open val name: String = "", open val type: String = "", open val uri: String = "")
