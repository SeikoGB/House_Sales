package uz.itschool.housesales.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductClient{
    const val BASE_URL="https://dummyjson.com"

    fun  getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}