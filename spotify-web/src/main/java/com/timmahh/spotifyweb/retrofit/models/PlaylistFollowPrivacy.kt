package com.timmahh.spotifyweb.retrofit.models

import com.google.gson.annotations.SerializedName

class PlaylistFollowPrivacy {
    @SerializedName("public")
    var is_public: Boolean? = false
}
