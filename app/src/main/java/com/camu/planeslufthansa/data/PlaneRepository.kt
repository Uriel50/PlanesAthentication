package com.camu.planeslufthansa.data


import com.camu.planeslufthansa.data.remote.PlanesApi
import com.camu.planeslufthansa.data.remote.model.PlaneDetailDto
import com.camu.planeslufthansa.data.remote.model.PlaneDto
import retrofit2.Call
import retrofit2.Retrofit


class PlaneRepository (private val retrofit: Retrofit){
    private val planesApi: PlanesApi = retrofit.create(PlanesApi::class.java)

    fun getPlanes(url: String): Call<List<PlaneDto>> =
        planesApi.getPlanes(url)

    fun getPlanesApiary(): Call<List<PlaneDto>> =
        planesApi.getPlanesApiary()

    fun getPlaneDetailApiary(id: String?): Call<PlaneDetailDto> =
        planesApi.getPlaneDetailApiary(id)


}