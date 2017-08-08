package com.timothy.spotifyarchitecture.retrofit

import com.timothy.spotifyarchitecture.retrofit.models.*
import retrofit2.Call
import retrofit2.http.*

interface SpotifyService {

    /**
     * Get the currently logged in user profile information.
     * The contents of the User object may differ depending on application's scope.

     * @return The current user
     * *
     * @see [Get Current User's Profile](https://developer.spotify.com/web-api/get-current-users-profile/)
     */
    @get:GET("me")
    val me: Call<UserPrivate>

    /**
     * Get a user's profile information.

     * @param userId The user's User ID
     * *
     * @return The user's profile information.
     * *
     * @see [Get User's Public Profile](https://developer.spotify.com/web-api/get-users-profile/)
     */
    @GET("users/{id}")
    fun getUser(@Path("id") userId: String): Call<UserPublic>

    /**
     * Get a list of the playlists owned or followed by the current Spotify user.

     * @return List of user's playlists wrapped in a `Pager` object
     */
    @get:GET("me/playlists")
    val myPlaylists: Call<Pager<PlaylistSimple>>

    /**
     * Get a list of the playlists owned or followed by the current Spotify user.

     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-a-list-of-current-users-playlists/)
     * *
     * @return List of user's playlists wrapped in a `Pager` object
     */
    @GET("me/playlists")
    fun getMyPlaylists(@QueryMap options: Map<String, String>): Call<Pager<PlaylistSimple>>

    /**
     * Get a list of the playlists owned or followed by a Spotify user.

     * @param userId  The user's Spotify user ID.
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-list-users-playlists/)
     * *
     * @return List of user's playlists wrapped in a `Pager` object
     * *
     * @see [Get a List of a User’s Playlists](https://developer.spotify.com/web-api/get-list-users-playlists/)
     */
    @GET("users/{id}/playlists")
    fun getPlaylists(@Path("id") userId: String, @QueryMap options: Map<String, String>): Call<Pager<PlaylistSimple>>

    /**
     * Get a list of the playlists owned or followed by a Spotify user.

     * @param userId The user's Spotify user ID.
     * *
     * @return List of user's playlists wrapped in a `Pager` object
     * *
     * @see [Get a List of a User’s Playlists](https://developer.spotify.com/web-api/get-list-users-playlists/)
     */
    @GET("users/{id}/playlists")
    fun getPlaylists(@Path("id") userId: String): Call<Pager<PlaylistSimple>>

    /**
     * Get a playlist owned by a Spotify user.

     * @param userId     The user's Spotify user ID.
     * *
     * @param playlistId The Spotify ID for the playlist.
     * *
     * @param options    Optional parameters. For list of supported parameters see
     * *                   [endpoint documentation](https://developer.spotify.com/web-api/get-playlist/)
     * *
     * @return Requested Playlist.
     * *
     * @see [Get a Playlist](https://developer.spotify.com/web-api/get-playlist/)
     */
    @GET("users/{user_id}/playlists/{playlist_id}")
    fun getPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @QueryMap options: Map<String, String>): Call<Playlist>

    /**
     * Get a playlist owned by a Spotify user.

     * @param userId     The user's Spotify user ID.
     * *
     * @param playlistId The Spotify ID for the playlist.
     * *
     * @return Requested Playlist.
     * *
     * @see [Get a Playlist](https://developer.spotify.com/web-api/get-playlist/)
     */
    @GET("users/{user_id}/playlists/{playlist_id}")
    fun getPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String): Call<Playlist>

    /**
     * Get full details of the tracks of a playlist owned by a Spotify user.

     * @param userId     The user's Spotify user ID.
     * *
     * @param playlistId The Spotify ID for the playlist.
     * *
     * @param options    Optional parameters. For list of supported parameters see
     * *                   [endpoint documentation](https://developer.spotify.com/web-api/get-playlists-tracks/)
     * *
     * @return List of playlist's tracks wrapped in a `Pager` object
     * *
     * @see [Get a Playlist’s Tracks](https://developer.spotify.com/web-api/get-playlists-tracks/)
     */
    @GET("users/{user_id}/playlists/{playlist_id}/tracks")
    fun getPlaylistTracks(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @QueryMap options: Map<String, String>): Call<Pager<PlaylistTrack>>

    /**
     * Get full details of the tracks of a playlist owned by a Spotify user.

     * @param userId     The user's Spotify user ID.
     * *
     * @param playlistId The Spotify ID for the playlist.
     * *
     * @return List of playlist's tracks wrapped in a `Pager` object
     * *
     * @see [Get a Playlist’s Tracks](https://developer.spotify.com/web-api/get-playlists-tracks/)
     */
    @GET("users/{user_id}/playlists/{playlist_id}/tracks")
    fun getPlaylistTracks(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String): Call<Pager<PlaylistTrack>>

    /**
     * Create a playlist

     * @param userId  The playlist's owner's User ID
     * *
     * @param options The body parameters
     * *
     * @return The created playlist
     * *
     * @see [Create a Playlist](https://developer.spotify.com/web-api/create-playlist/)
     */
    @POST("users/{user_id}/playlists")
    fun createPlaylist(@Path("user_id") userId: String, @Body options: Map<String, String>): Playlist

    /**
     * Add tracks to a playlist

     * @param userId          The owner of the playlist
     * *
     * @param playlistId      The playlist's ID
     * *
     * @param queryParameters Query parameters
     * *
     * @param body            The body parameters
     * *
     * @return A snapshot ID (the version of the playlist)
     * *
     * @see [Add Tracks to a Playlist](https://developer.spotify.com/web-api/add-tracks-to-playlist/)
     */
    @POST("users/{user_id}/playlists/{playlist_id}/tracks")
    fun addTracksToPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @QueryMap queryParameters: Map<String, String>, @Body body: Map<String, String>): Call<SnapshotId>

    /**
     * Remove one or more tracks from a user’s playlist.

     * @param userId         The owner of the playlist
     * *
     * @param playlistId     The playlist's Id
     * *
     * @param tracksToRemove A list of tracks to remove
     * *
     * @return A snapshot ID (the version of the playlist)
     * *
     * @see [Remove Tracks from a Playlist](https://developer.spotify.com/web-api/remove-tracks-playlist/)
     */
    @DELETE("users/{user_id}/playlists/{playlist_id}/tracks")
    fun removeTracksFromPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @Body tracksToRemove: TracksToRemove): Call<SnapshotId>

    /**
     * Remove one or more tracks from a user’s playlist.

     * @param userId                     The owner of the playlist
     * *
     * @param playlistId                 The playlist's Id
     * *
     * @param tracksToRemoveWithPosition A list of tracks to remove, together with their specific positions
     * *
     * @return A snapshot ID (the version of the playlist)
     * *
     * @see [Remove Tracks from a Playlist](https://developer.spotify.com/web-api/remove-tracks-playlist/)
     */
    @DELETE("users/{user_id}/playlists/{playlist_id}/tracks")
    fun removeTracksFromPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @Body tracksToRemoveWithPosition: TracksToRemoveWithPosition): Call<SnapshotId>

    /**
     * Replace all the tracks in a playlist, overwriting its existing tracks. This powerful request can be useful for
     * replacing tracks, re-ordering existing tracks, or clearing the playlist.

     * @param userId     The owner of the playlist
     * *
     * @param playlistId The playlist's Id
     * *
     * @param trackUris  A list of comma-separated track uris
     * *
     * @return An empty result
     * *
     * @see [Replace a Playlist’s Tracks](https://developer.spotify.com/web-api/replace-playlists-tracks/)
     */
    @PUT("users/{user_id}/playlists/{playlist_id}/tracks")
    fun replaceTracksInPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @Query("uris") trackUris: String): Call<Result>

    /**
     * Change a playlist’s name and public/private state. (The user must, of course, own the playlist.)

     * @param userId     The Spotify user ID of the user who owns the playlist.
     * *
     * @param playlistId The playlist's Id
     * *
     * @param body       The body parameters. For list of supported parameters see [endpoint documentation](https://developer.spotify.com/web-api/change-playlist-details/)
     * *
     * @return An empty result
     * *
     * @see [Change a Playlist's Details](https://developer.spotify.com/web-api/change-playlist-details/)
     */
    @PUT("users/{user_id}/playlists/{playlist_id}")
    fun changePlaylistDetails(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @Body body: Map<String, String>): Call<Result>

    /**
     * Add the current user as a follower of a playlist.

     * @param userId     The Spotify user ID of the user who owns the playlist.
     * *
     * @param playlistId The Spotify ID of the playlist
     * *
     * @return An empty result
     * *
     * @see [Follow a Playlist](https://developer.spotify.com/web-api/follow-playlist/)
     */
    @PUT("users/{user_id}/playlists/{playlist_id}/followers")
    fun followPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String): Call<Result>

    /**
     * Add the current user as a follower of a playlist.

     * @param userId                The Spotify user ID of the user who owns the playlist.
     * *
     * @param playlistId            The Spotify ID of the playlist
     * *
     * @param playlistFollowPrivacy The privacy state of the playlist
     * *
     * @return An empty result
     * *
     * @see [Follow a Playlist](https://developer.spotify.com/web-api/follow-playlist/)
     */
    @PUT("users/{user_id}/playlists/{playlist_id}/followers")
    fun followPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @Body playlistFollowPrivacy: PlaylistFollowPrivacy): Call<Result>

    /**
     * Unfollow a Playlist

     * @param userId     The Spotify user ID of the user who owns the playlist.
     * *
     * @param playlistId The Spotify ID of the playlist
     * *
     * @return An empty result
     * *
     * @see [Unfollow a Playlist](https://developer.spotify.com/web-api/unfollow-playlist/)
     */
    @DELETE("users/{user_id}/playlists/{playlist_id}/followers")
    fun unfollowPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String): Call<Result>

    /**
     * Reorder a Playlist's tracks

     * @param userId     The Spotify user ID of the user who owns the playlist.
     * *
     * @param playlistId The Spotify ID of the playlist
     * *
     * @param body       The body parameters. For list of supported parameters see [endpoint documentation](https://developer.spotify.com/web-api/reorder-playlists-tracks/)
     * *
     * @return A snapshot ID (the version of the playlist)
     * *
     * @see [Reorder a Playlist](https://developer.spotify.com/web-api/reorder-playlists-tracks/)
     */
    @PUT("users/{user_id}/playlists/{playlist_id}/tracks")
    fun reorderPlaylistTracks(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @Body body: Map<String, String>): Call<SnapshotId>


    /**********
     * Albums *
     */

    /**
     * Get Spotify catalog information for a single album.

     * @param albumId The Spotify ID for the album.
     * *
     * @return Requested album information
     * *
     * @see [Get an Album](https://developer.spotify.com/web-api/get-album/)
     */
    @GET("albums/{id}")
    fun getAlbum(@Path("id") albumId: String): Call<Album>

    /**
     * Get Spotify catalog information for a single album.

     * @param albumId The Spotify ID for the album.
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-album/)
     * *
     * @return Requested album information
     * *
     * @see [Get an Album](https://developer.spotify.com/web-api/get-album/)
     */
    @GET("albums/{id}")
    fun getAlbum(@Path("id") albumId: String, @QueryMap options: Map<String, String>): Call<Album>

    /**
     * Get Spotify catalog information for multiple albums identified by their Spotify IDs.

     * @param albumIds A comma-separated list of the Spotify IDs for the albums
     * *
     * @return Object whose key is "albums" and whose value is an array of album objects.
     * *
     * @see [Get Several Albums](https://developer.spotify.com/web-api/get-several-albums/)
     */
    @GET("albums")
    fun getAlbums(@Query("ids") albumIds: String): Call<Albums>

    /**
     * Get Spotify catalog information for multiple albums identified by their Spotify IDs.

     * @param albumIds A comma-separated list of the Spotify IDs for the albums
     * *
     * @param options  Optional parameters. For list of supported parameters see
     * *                 [endpoint documentation](https://developer.spotify.com/web-api/get-several-albums/)
     * *
     * @return Object whose key is "albums" and whose value is an array of album objects.
     * *
     * @see [Get Several Albums](https://developer.spotify.com/web-api/get-several-albums/)
     */
    @GET("albums")
    fun getAlbums(@Query("ids") albumIds: String, @QueryMap options: Map<String, String>): Call<Albums>

    /**
     * Get Spotify catalog information about an album’s tracks.

     * @param albumId The Spotify ID for the album.
     * *
     * @return List of simplified album objects wrapped in a Pager object
     * *
     * @see [Get an Album’s Tracks](https://developer.spotify.com/web-api/get-albums-tracks/)
     */
    @GET("albums/{id}/tracks")
    fun getAlbumTracks(@Path("id") albumId: String): Call<Pager<Track>>

    /**
     * Get Spotify catalog information about an album’s tracks.

     * @param albumId The Spotify ID for the album.
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-albums-tracks/)
     * *
     * @return List of simplified album objects wrapped in a Pager object
     * *
     * @see [Get an Album’s Tracks](https://developer.spotify.com/web-api/get-albums-tracks/)
     */
    @GET("albums/{id}/tracks")
    fun getAlbumTracks(@Path("id") albumId: String, @QueryMap options: Map<String, String>): Call<Pager<Track>>


    /***********
     * Artists *
     */

    /**
     * Get Spotify catalog information for a single artist identified by their unique Spotify ID.

     * @param artistId The Spotify ID for the artist.
     * *
     * @return Requested artist information
     * *
     * @see [Get an Artist](https://developer.spotify.com/web-api/get-artist/)
     */
    @GET("artists/{id}")
    fun getArtist(@Path("id") artistId: String): Call<Artist>

    /**
     * Get Spotify catalog information for several artists based on their Spotify IDs.

     * @param artistIds A comma-separated list of the Spotify IDs for the artists
     * *
     * @return An object whose key is "artists" and whose value is an array of artist objects.
     * *
     * @see [Get Several Artists](https://developer.spotify.com/web-api/get-several-artists/)
     */
    @GET("artists")
    fun getArtists(@Query("ids") artistIds: String): Call<Artists>

    /**
     * Get Spotify catalog information about an artist’s albums.

     * @param artistId The Spotify ID for the artist.
     * *
     * @return An array of simplified album objects wrapped in a paging object.
     * *
     * @see [Get an Artist's Albums](https://developer.spotify.com/web-api/get-artists-albums/)
     */
    @GET("artists/{id}/albums")
    fun getArtistAlbums(@Path("id") artistId: String): Call<Pager<Album>>

    /**
     * Get Spotify catalog information about an artist’s albums.

     * @param artistId The Spotify ID for the artist.
     * *
     * @param options  Optional parameters. For list of supported parameters see
     * *                 [endpoint documentation](https://developer.spotify.com/web-api/get-artists-albums/)
     * *
     * @return An array of simplified album objects wrapped in a paging object.
     * *
     * @see [Get an Artist's Albums](https://developer.spotify.com/web-api/get-artists-albums/)
     */
    @GET("artists/{id}/albums")
    fun getArtistAlbums(@Path("id") artistId: String, @QueryMap options: Map<String, String>): Call<Pager<Album>>

    /**
     * Get Spotify catalog information about an artist’s top tracks by country.

     * @param artistId The Spotify ID for the artist.
     * *
     * @param country  The country: an ISO 3166-1 alpha-2 country code.
     * *
     * @return An object whose key is "tracks" and whose value is an array of track objects.
     * *
     * @see [Get an Artist’s Top Tracks](https://developer.spotify.com/web-api/get-artists-top-tracks/)
     */
    @GET("artists/{id}/top-tracks")
    fun getArtistTopTrack(@Path("id") artistId: String, @Query("country") country: String): Call<Tracks>

    /**
     * Get Spotify catalog information about artists similar to a given artist.

     * @param artistId The Spotify ID for the artist.
     * *
     * @return An object whose key is "artists" and whose value is an array of artist objects.
     * *
     * @see [Get an Artist’s Related Artists](https://developer.spotify.com/web-api/get-related-artists/)
     */
    @GET("artists/{id}/related-artists")
    fun getRelatedArtists(@Path("id") artistId: String): Call<Artists>


    /**********
     * Tracks *
     */

    /**
     * Get Spotify catalog information for a single track identified by their unique Spotify ID.

     * @param trackId The Spotify ID for the track.
     * *
     * @return Requested track information
     * *
     * @see [Get a Track](https://developer.spotify.com/web-api/get-track/)
     */
    @GET("tracks/{id}")
    fun getTrack(@Path("id") trackId: String): Call<Track>

    /**
     * Get Spotify catalog information for a single track identified by their unique Spotify ID.

     * @param trackId The Spotify ID for the track.
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-track/)
     * *
     * @return Requested track information
     * *
     * @see [Get a Track](https://developer.spotify.com/web-api/get-track/)
     */
    @GET("tracks/{id}")
    fun getTrack(@Path("id") trackId: String, @QueryMap options: Map<String, String>): Call<Track>

    /**
     * Get Several Tracks

     * @param trackIds A comma-separated list of the Spotify IDs for the tracks
     * *
     * @return An object whose key is "tracks" and whose value is an array of track objects.
     * *
     * @see [Get Several Tracks](https://developer.spotify.com/web-api/get-several-tracks/)
     */
    @GET("tracks")
    fun getTracks(@Query("ids") trackIds: String): Call<Tracks>

    /**
     * Get Several Tracks

     * @param trackIds A comma-separated list of the Spotify IDs for the tracks
     * *
     * @param options  Optional parameters. For list of supported parameters see
     * *                 [endpoint documentation](https://developer.spotify.com/web-api/get-several-tracks/)
     * *
     * @return An object whose key is "tracks" and whose value is an array of track objects.
     * *
     * @see [Get Several Tracks](https://developer.spotify.com/web-api/get-several-tracks/)
     */
    @GET("tracks")
    fun getTracks(@Query("ids") trackIds: String, @QueryMap options: Map<String, String>): Call<Tracks>


    /**********
     * Browse *
     */

    /**
     * Get a list of Spotify featured playlists (shown, for example, on a Spotify player’s “Browse” tab).

     * @return A FeaturedPlaylists object with the featured playlists
     * *
     * @see [Get a List of Featured Playlists](https://developer.spotify.com/web-api/get-list-featured-playlists/)
     */
    @get:GET("browse/featured-playlists")
    val featuredPlaylists: Call<FeaturedPlaylists>

    /**
     * Get a list of Spotify featured playlists (shown, for example, on a Spotify player’s “Browse” tab).

     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-list-featured-playlists/)
     * *
     * @return n FeaturedPlaylists object with the featured playlists
     * *
     * @see [Get a List of Featured Playlists](https://developer.spotify.com/web-api/get-list-featured-playlists/)
     */
    @GET("browse/featured-playlists")
    fun getFeaturedPlaylists(@QueryMap options: Map<String, String>): Call<FeaturedPlaylists>

    /**
     * Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).

     * @return A NewReleases object with the new album releases
     * *
     * @see [Get a List of New Releases](https://developer.spotify.com/web-api/get-list-new-releases/)
     */
    @get:GET("browse/new-releases")
    val newReleases: Call<NewReleases>

    /**
     * Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).

     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-list-new-releases/)
     * *
     * @return A NewReleases object with the new album releases
     * *
     * @see [Get a List of New Releases](https://developer.spotify.com/web-api/get-list-new-releases/)
     */
    @GET("browse/new-releases")
    fun getNewReleases(@QueryMap options: Map<String, String>): Call<NewReleases>

    /**
     * Retrieve Spotify categories. Categories used to tag items in
     * Spotify (on, for example, the Spotify player’s “Browse” tab).

     * @param options Optional parameters.
     * *
     * @return A paging object containing categories.
     * *
     * @see [Get a List of Categories](https://developer.spotify.com/web-api/get-list-categories/)
     */
    @GET("browse/categories")
    fun getCategories(@QueryMap options: Map<String, String>): Call<CategoriesPager>

    /**
     * Retrieve a Spotify category.

     * @param categoryId The category's ID.
     * *
     * @param options    Optional parameters.
     * *
     * @return A Spotify category.
     * *
     * @see [Get a Spotify Category](https://developer.spotify.com/web-api/get-category/)
     */
    @GET("browse/categories/{category_id}")
    fun getCategory(@Path("category_id") categoryId: String, @QueryMap options: Map<String, String>): Call<Category>

    /**
     * Retrieve playlists for a Spotify Category.

     * @param categoryId The category's ID.
     * *
     * @param options    Optional parameters.
     * *
     * @return Playlists for a Spotify Category.
     * *
     * @see [Get playlists for a Spotify Category](https://developer.spotify.com/web-api/get-categorys-playlists/)
     */
    @GET("browse/categories/{category_id}/playlists")
    fun getPlaylistsForCategory(@Path("category_id") categoryId: String, @QueryMap options: Map<String, String>): Call<PlaylistsPager>


    /************************
     * Library / Your Music *
     */

    /**
     * Get a list of the songs saved in the current Spotify user’s “Your Music” library.

     * @return A paginated list of saved tracks
     * *
     * @see [Get a User’s Saved Tracks](https://developer.spotify.com/web-api/get-users-saved-tracks/)
     */
    @get:GET("me/tracks")
    val mySavedTracks: Call<Pager<SavedTrack>>

    /**
     * Get a list of the songs saved in the current Spotify user’s “Your Music” library.

     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-users-saved-tracks/)
     * *
     * @return A paginated list of saved tracks
     * *
     * @see [Get a User’s Saved Tracks](https://developer.spotify.com/web-api/get-users-saved-tracks/)
     */
    @GET("me/tracks")
    fun getMySavedTracks(@QueryMap options: Map<String, String>): Call<Pager<SavedTrack>>

    /**
     * Check if one or more tracks is already saved in the current Spotify user’s “Your Music” library.

     * @param ids A comma-separated list of the Spotify IDs for the tracks
     * *
     * @return An array with boolean values that indicate whether the tracks are in the current Spotify user’s “Your Music” library.
     * *
     * @see [Check User’s Saved Tracks](https://developer.spotify.com/web-api/check-users-saved-tracks/)
     */
    @GET("me/tracks/contains")
    fun containsMySavedTracks(@Query("ids") ids: String): Call<Array<Boolean>>

    /**
     * Save one or more tracks to the current user’s “Your Music” library.

     * @param ids A comma-separated list of the Spotify IDs for the tracks
     * *
     * @return An empty result
     * *
     * @see [Save Tracks for User](https://developer.spotify.com/web-api/save-tracks-user/)
     */
    @PUT("me/tracks")
    fun addToMySavedTracks(@Query("ids") ids: String): Call<Result>

    /**
     * Remove one or more tracks from the current user’s “Your Music” library.

     * @param ids A comma-separated list of the Spotify IDs for the tracks
     * *
     * @return An empty result
     * *
     * @see [Remove User’s Saved Tracks](https://developer.spotify.com/web-api/remove-tracks-user/)
     */
    @DELETE("me/tracks")
    fun removeFromMySavedTracks(@Query("ids") ids: String): Call<Result>

    /**
     * Get a list of the albums saved in the current Spotify user’s “Your Music” library.

     * @return A paginated list of saved albums
     * *
     * @see [Get a User’s Saved Albums](https://developer.spotify.com/web-api/get-users-saved-albums/)
     */
    @get:GET("me/albums")
    val mySavedAlbums: Call<Pager<SavedAlbum>>

    /**
     * Get a list of the albums saved in the current Spotify user’s “Your Music” library.

     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-users-saved-albums/)
     * *
     * @return A paginated list of saved albums
     * *
     * @see [Get a User’s Saved Albums](https://developer.spotify.com/web-api/get-users-saved-albums/)
     */
    @GET("me/albums")
    fun getMySavedAlbums(@QueryMap options: Map<String, String>): Call<Pager<SavedAlbum>>

    /**
     * Check if one or more albums is already saved in the current Spotify user’s “Your Music” library.

     * @param ids A comma-separated list of the Spotify IDs for the albums
     * *
     * @return An array with boolean values that indicate whether the albums are in the current Spotify user’s “Your Music” library.
     * *
     * @see [Check User’s Saved Albums](https://developer.spotify.com/web-api/check-users-saved-albums/)
     */
    @GET("me/albums/contains")
    fun containsMySavedAlbums(@Query("ids") ids: String): Call<Array<Boolean>>

    /**
     * Save one or more albums to the current user’s “Your Music” library.

     * @param ids A comma-separated list of the Spotify IDs for the albums
     * *
     * @return An empty result
     * *
     * @see [Save Albums for User](https://developer.spotify.com/web-api/save-albums-user/)
     */
    @PUT("me/albums")
    fun addToMySavedAlbums(@Query("ids") ids: String): Call<Result>

    /**
     * Remove one or more albums from the current user’s “Your Music” library.

     * @param ids A comma-separated list of the Spotify IDs for the albums
     * *
     * @return An empty result
     * *
     * @see [Remove User’s Saved Albums](https://developer.spotify.com/web-api/remove-albums-user/)
     */
    @DELETE("me/albums")
    fun removeFromMySavedAlbums(@Query("ids") ids: String): Call<Result>

    /**********
     * Follow *
     */

    /**
     * Add the current user as a follower of one or more Spotify users.

     * @param ids A comma-separated list of the Spotify IDs for the users
     * *
     * @return An empty result
     * *
     * @see [Follow Artists or Users](https://developer.spotify.com/web-api/follow-artists-users/)
     */
    @PUT("me/following?type=user")
    fun followUsers(@Query("ids") ids: String): Call<Result>

    /**
     * Add the current user as a follower of one or more Spotify artists.

     * @param ids A comma-separated list of the Spotify IDs for the artists
     * *
     * @return An empty result
     * *
     * @see [Follow Artists or Users](https://developer.spotify.com/web-api/follow-artists-users/)
     */
    @PUT("me/following?type=artist")
    fun followArtists(@Query("ids") ids: String): Call<Result>

    /**
     * Remove the current user as a follower of one or more Spotify users.

     * @param ids A comma-separated list of the Spotify IDs for the users
     * *
     * @return An empty result
     * *
     * @see [Unfollow Artists or Users](https://developer.spotify.com/web-api/unfollow-artists-users/)
     */
    @DELETE("me/following?type=user")
    fun unfollowUsers(@Query("ids") ids: String): Call<Result>

    /**
     * Remove the current user as a follower of one or more Spotify artists.

     * @param ids A comma-separated list of the Spotify IDs for the artists
     * *
     * @return An empty result
     * *
     * @see [Unfollow Artists or Users](https://developer.spotify.com/web-api/unfollow-artists-users/)
     */
    @DELETE("me/following?type=artist")
    fun unfollowArtists(@Query("ids") ids: String): Call<Result>

    /**
     * Check to see if the current user is following one or more other Spotify users.

     * @param ids A comma-separated list of the Spotify IDs for the users
     * *
     * @return An array with boolean values indicating whether the users are followed
     * *
     * @see [Check if Current User Follows Artists or Users](https://developer.spotify.com/web-api/check-current-user-follows/)
     */
    @GET("me/following/contains?type=user")
    fun isFollowingUsers(@Query("ids") ids: String): Call<Array<Boolean>>

    /**
     * Check to see if the current user is following one or more other Spotify artists.

     * @param ids A comma-separated list of the Spotify IDs for the artists
     * *
     * @return An array with boolean values indicating whether the artists are followed
     * *
     * @see [Check if Current User Follows Artists or Users](https://developer.spotify.com/web-api/check-current-user-follows/)
     */
    @GET("me/following/contains?type=artist")
    fun isFollowingArtists(@Query("ids") ids: String): Call<Array<Boolean>>

    /**
     * Check to see if one or more Spotify users are following a specified playlist.

     * @param userId     The Spotify user ID of the person who owns the playlist.
     * *
     * @param playlistId The Spotify ID of the playlist.
     * *
     * @param ids        A comma-separated list of the Spotify IDs for the users
     * *
     * @return An array with boolean values indicating whether the playlist is followed by the users
     * *
     * @see [Check if Users Follow a Playlist](https://developer.spotify.com/web-api/check-user-following-playlist/)
     */
    @GET("users/{user_id}/playlists/{playlist_id}/followers/contains")
    fun areFollowingPlaylist(@Path("user_id") userId: String, @Path("playlist_id") playlistId: String, @Query("ids") ids: String): Call<Array<Boolean>>

    /**
     * Get the current user's followed artists.

     * @return Object containing a list of artists that user follows wrapped in a cursor object.
     * *
     * @see [Get User's Followed Artists](https://developer.spotify.com/web-api/get-followed-artists/)
     */
    @get:GET("me/following?type=artist")
    val followedArtists: Call<ArtistsCursorPager>


    /**
     * Get the current user's followed artists.

     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-followed-artists/)
     * *
     * @return Object containing a list of artists that user follows wrapped in a cursor object.
     * *
     * @see [Get User's Followed Artists](https://developer.spotify.com/web-api/get-followed-artists/)
     */
    @GET("me/following?type=artist")
    fun getFollowedArtists(@QueryMap options: Map<String, String>): Call<ArtistsCursorPager>

    /**********
     * Search *
     */

    /**
     * Get Spotify catalog information about tracks that match a keyword string.

     * @param q The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=track")
    fun searchTracks(@Query("q") q: String): Call<TracksPager>

    /**
     * Get Spotify catalog information about tracks that match a keyword string.

     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/search-item/)
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=track")
    fun searchTracks(@Query("q") q: String, @QueryMap options: Map<String, String>): Call<TracksPager>

    /**
     * Get Spotify catalog information about artists that match a keyword string.

     * @param q The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=artist")
    fun searchArtists(@Query("q") q: String): Call<ArtistsPager>

    /**
     * Get Spotify catalog information about artists that match a keyword string.

     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/search-item/)
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=artist")
    fun searchArtists(@Query("q") q: String, @QueryMap options: Map<String, String>): Call<ArtistsPager>

    /**
     * Get Spotify catalog information about albums that match a keyword string.

     * @param q The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=album")
    fun searchAlbums(@Query("q") q: String): Call<AlbumsPager>

    /**
     * Get Spotify catalog information about albums that match a keyword string.

     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/search-item/)
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=album")
    fun searchAlbums(@Query("q") q: String, @QueryMap options: Map<String, String>): Call<AlbumsPager>

    /**
     * Get Spotify catalog information about playlists that match a keyword string.

     * @param q The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=playlist")
    fun searchPlaylists(@Query("q") q: String): Call<PlaylistsPager>

    /**
     * Get Spotify catalog information about playlists that match a keyword string.

     * @param q       The search query's keywords (and optional field filters and operators), for example "roadhouse+blues"
     * *
     * @param options Optional parameters. For list of supported parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/search-item/)
     * *
     * @return A paginated list of results
     * *
     * @see [Search for an Item](https://developer.spotify.com/web-api/search-item/)
     */
    @GET("search?type=playlist")
    fun searchPlaylists(@Query("q") q: String, @QueryMap options: Map<String, String>): Call<PlaylistsPager>

    /******************
     * Audio features *
     */

    /**
     * Get audio features for multiple tracks based on their Spotify IDs.

     * @param ids A comma-separated list of the Spotify IDs for the tracks. Maximum: 100 IDs
     * *
     * @return An object whose key is "audio_features" and whose value is an array of audio features objects.
     */
    @GET("/audio-features")
    fun getTracksAudioFeatures(@Query("ids") ids: String): Call<AudioFeaturesTracks>


    /**
     * Get audio feature information for a single track identified by its unique Spotify ID.

     * @param id The Spotify ID for the track.
     * *
     * @return Audio features object
     */
    @GET("/audio-features/{id}")
    fun getTrackAudioFeatures(@Path("id") id: String): Call<AudioFeaturesTrack>

    /*******************
     * Recommendations *
     */

    /**
     * Create a playlist-style listening experience based on seed artists, tracks and genres.

     * @param options Optional parameters. For list of available parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-recommendations/)
     * *
     * @return Recommendations response object
     */
    @GET("/recommendations")
    fun getRecommendations(@QueryMap options: Map<String, String>): Call<Recommendations>


    /**
     * Retrieve a list of available genres seed parameter values for recommendations.

     * @return An object whose key is "genres" and whose value is an array of available genres.
     */
    @get:GET("/recommendations/available-genre-seeds")
    val seedsGenres: Call<SeedsGenres>


    /*****************************
     * User Top Artists & Tracks *
     */

    /**
     * Get the current user’s top artists based on calculated affinity.

     * @return The objects whose response body contains an artists or tracks object.
     * * The object in turn contains a paging object of Artists or Tracks
     */
    @get:GET("/me/top/artists")
    val topArtists: Call<Pager<Artist>>

    /**
     * Get the current user’s top artists based on calculated affinity.

     * @param options Optional parameters. For list of available parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-users-top-artists-and-tracks/)
     * *
     * @return The objects whose response body contains an artists or tracks object.
     * * The object in turn contains a paging object of Artists or Tracks
     */
    @GET("/me/top/artists")
    fun getTopArtists(@QueryMap options: Map<String, String>): Call<Pager<Artist>>

    /**
     * Get the current user’s top tracks based on calculated affinity.

     * @return The objects whose response body contains an artists or tracks object.
     * * The object in turn contains a paging object of Artists or Tracks
     */
    @get:GET("/me/top/tracks")
    val topTracks: Call<Pager<Track>>

    /**
     * Get the current user’s top tracks based on calculated affinity.

     * @param options Optional parameters. For list of available parameters see
     * *                [endpoint documentation](https://developer.spotify.com/web-api/get-users-top-artists-and-tracks/)
     * *
     * @return The objects whose response body contains an artists or tracks object.
     * * The object in turn contains a paging object of Artists or Tracks
     */
    @GET("/me/top/tracks")
    fun getTopTracks(@QueryMap options: Map<String, String>): Call<Pager<Track>>
}
