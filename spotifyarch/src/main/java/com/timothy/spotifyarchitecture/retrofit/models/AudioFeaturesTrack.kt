package com.timothy.spotifyarchitecture.retrofit.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.os.Parcel
import android.os.Parcelable

/**
 * [Audio Features Object](https://developer.spotify.com/web-api/object-model/#audio-features-object)
 */
@Entity(primaryKeys = arrayOf("feature_id"), tableName = "spotify_audio_features", foreignKeys = arrayOf(ForeignKey(entity = Track::class, parentColumns = arrayOf("track_id"), childColumns = arrayOf("feature_id"))))
class AudioFeaturesTrack {

    var acousticness: Float = 0.toFloat()
    var analysis_url: String = ""
    var danceability: Float = 0.toFloat()
    var duration_ms: Int = 0
    var energy: Float = 0.toFloat()
    @ColumnInfo(name = "feature_id")
    var id: String = ""
    var instrumentalness: Float = 0.toFloat()
    var key: Int = 0
    var liveness: Float = 0.toFloat()
    var loudness: Float = 0.toFloat()
    var mode: Int = 0
    var speechiness: Float = 0.toFloat()
    var tempo: Float = 0.toFloat()
    var time_signature: Int = 0
    var track_href: String = ""
    var type: String = ""
    var uri: String = ""
    var valence: Float = 0.toFloat()
}
