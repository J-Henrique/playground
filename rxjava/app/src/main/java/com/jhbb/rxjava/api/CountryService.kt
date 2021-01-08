package com.jhbb.rxjava.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CountryService {

    @GET("country")
    fun getCountriesSingle(@Header("x-mock-response-name") mock: String): Single<List<Country>>

    @GET("country")
    fun getCountriesCompletable(@Header("x-mock-response-name") mock: String): Completable

    @GET("country")
    fun getCountriesMaybe(@Header("x-mock-response-name") mock: String): Maybe<List<Country>>
}