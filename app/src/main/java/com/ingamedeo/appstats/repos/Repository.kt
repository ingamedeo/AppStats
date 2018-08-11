package com.ingamedeo.appstats.repos

import com.ingamedeo.appstats.StatEntry
import com.ingamedeo.appstats.apis.AppStatsApi
import com.ingamedeo.appstats.await

/*
Call the underlying API method, call await (ref: CommonUtils.kt)
The network call will be executed in a co-routine in the background.
*/

object Repository {
    suspend fun getNumUsersPerDay(year: String, month: String, app_package: String): List<StatEntry> =
            AppStatsApi.appStatsApiInterface.getNumUsersPerDay(year, month, app_package).await()
}

