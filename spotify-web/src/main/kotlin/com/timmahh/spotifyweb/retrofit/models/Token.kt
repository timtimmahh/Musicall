package com.timmahh.spotifyweb.retrofit.models

/**
 * Room Entity class represents a table in an SQL database
 */
data class Token(var access_token: String = "",
                 var user_id: String = "",
                 var token_type: String = "Bearer",
                 var scope: String = "",
                 var expires_in: Long = 0,
                 var refresh_token: String = "")