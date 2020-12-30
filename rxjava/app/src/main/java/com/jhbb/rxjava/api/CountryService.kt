package com.jhbb.rxjava.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountryService {

    @GET("country")
    fun getCountries(): Single<List<Country>>
}