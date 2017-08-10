package com.timmahh.spotifyweb.retrofit.models

/**
 * [Playlist object model (simplified)](https://developer.spotify.com/web-api/object-model/#playlist-object-simplified)
 */
class PlaylistSimple(val tracks: PlaylistTracksInformation = PlaylistTracksInformation(), collaborative: Boolean, external_urls: Map<String, String>, href: String, id: String, images: List<Image>, name: String, owner: UserPublic, is_public: Boolean, snapshot_id: String, type: String, uri: String) : PlaylistBase(collaborative, external_urls, href, id, images, name, owner, is_public, snapshot_id, type, uri)
