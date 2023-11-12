package com.camu.planeslufthansa.data.remote


import com.camu.planeslufthansa.data.remote.model.PlaneDetailDto
import com.camu.planeslufthansa.data.remote.model.PlaneDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface PlanesApi {

    @GET
    fun getPlanes(
        @Url url: String?
    ): Call<List<PlaneDto>>


    @GET("plane/plane_list")
    fun getPlanesApiary(): Call<List<PlaneDto>>

    @GET("plane/plane_detail/{id}")
    fun getPlaneDetailApiary(
        @Path("id") id: String?
    ): Call<PlaneDetailDto>

}