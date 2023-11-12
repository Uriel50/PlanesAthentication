package com.camu.planeslufthansa.application

import android.app.Application
import com.camu.planeslufthansa.data.PlaneRepository
import com.camu.planeslufthansa.data.remote.RetrofitHelper

class PlanesRFApp : Application(){
    private val retrofit by lazy {
        RetrofitHelper().getRetrofit()
    }
    val repository by lazy {
        PlaneRepository(retrofit)
    }
}