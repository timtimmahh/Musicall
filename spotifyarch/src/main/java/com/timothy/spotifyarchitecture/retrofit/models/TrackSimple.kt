package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Relation
import android.os.Parcel
import android.os.Parcelable

@Entity(primaryKeys = arrayOf("track_id"), tableName = "spotify_tracks")
open class TrackSimple(@Relation(parentColumn = "track_id", entityColumn = "artist_id") open val artists: List<ArtistSimple> = listOf(),
                       open val available_markets: List<String> = listOf(), open val is_playable: Boolean = false, @Embedded(prefix = "linked") open val linked_from: LinkedTrack = LinkedTrack(),
                       open val disc_number: Int = 0, open val duration_ms: Long = 0, open val explicit: Boolean = false, open val external_urls: Map<String, String> = mapOf(),
                       open val href: String = "", @ColumnInfo(name = "track_id")open val id: String = "", open val name: String = "", open val preview_url: String = "",
                       open val type: String = "", open val uri: String = "") {
}
