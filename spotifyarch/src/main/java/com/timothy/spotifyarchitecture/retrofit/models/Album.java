package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Relation;

import java.util.List;
import java.util.Map;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#album-object-full">Album object model</a>
 */
public class Album extends AlbumSimple {
    @Relation(parentColumn = "album_id", entityColumn = "artist_id")
    public List<ArtistSimple> artists;
    public List<Copyright> copyrights;
    public Map<String, String> external_ids;
    public List<String> genres;
    public Integer popularity;
    public String release_date;
    public String release_date_precision;
    @Relation(parentColumn = "album_id", entityColumn = "track_id")
    public Pager<TrackSimple> tracks;

    public Album() {
    }
}