package com.saintsantos.mstscodinginterview

import io.reactivex.Single
import retrofit2.http.POST

interface CompilationApi {
    @POST("url here mate!")
    fun postPhotoToApi(): Single<CompilationResponse>
}