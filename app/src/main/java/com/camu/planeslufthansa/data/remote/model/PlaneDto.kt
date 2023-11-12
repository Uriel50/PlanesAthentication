package com.camu.planeslufthansa.data.remote.model

import com.google.gson.annotations.SerializedName


data class PlaneDto(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("thumbnail")
    var thumbnail: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("manufacturing")
    var manufacturing: String? = null,
    @SerializedName("capacity")
    var capacity: String? = null,
    @SerializedName("fleet")
    var fleet: String? = null
)