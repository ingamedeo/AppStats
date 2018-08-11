package com.ingamedeo.appstats.apis

import com.ingamedeo.appstats.StatEntry
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
The object will be instantiated and its init blocks will be executed lazily upon first access, in a thread-safe way.
To achieve this, a Kotlin object actually relies on a Java static initialization block.
https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
*/
object AppStatsApi {

    interface AppStatsApiInterface {
        @GET("graph/numUsersPerDay")
        fun getNumUsersPerDay(@Query("year") year: String,
                              @Query("month") month: String,
                              @Query("app_package") app_package: String)
                : Call<List<StatEntry>>
    }

    val appStatsApiInterface: AppStatsApiInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.amedeobaragiola.me/appStats-1.1/stats/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        appStatsApiInterface = retrofit.create(AppStatsApiInterface::class.java)
    }

}

