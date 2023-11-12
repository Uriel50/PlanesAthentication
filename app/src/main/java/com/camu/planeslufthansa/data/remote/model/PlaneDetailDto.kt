package com.camu.planeslufthansa.data.remote.model


import com.google.gson.annotations.SerializedName


data class PlaneDetailDto(
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("length")
    var length: String? = null,
    @SerializedName("height")
    var height: String? = null,
    @SerializedName("engine")
    var engine: String? = null,
    @SerializedName("maximum_speed")
    var maximumSpeed: String? = null,
    @SerializedName("wingspan")
    var wingspan: String? = null,
    @SerializedName("autonomy")
    var autonomy: String? = null,
    @SerializedName("video")
    var video: String? = null,
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null,
    @SerializedName("airport")
    var airport: String? = null
)
