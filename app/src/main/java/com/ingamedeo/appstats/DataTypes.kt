package com.ingamedeo.appstats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class Action
data class BeginNumUsers(val year: String, val month: String, val app_package: String) : Action()

@JsonClass(generateAdapter = true)
data class StatEntry(@Json(name = "number") val number: Int, @Json(name = "day") val day: String)