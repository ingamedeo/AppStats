package com.ingamedeo.appstats.models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ingamedeo.appstats.Action
import com.ingamedeo.appstats.BeginNumUsers
import com.ingamedeo.appstats.repos.Repository
import com.ingamedeo.appstats.StatEntry
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.actor

class MainModel: ViewModel() {

    private val mutableEntries = MutableLiveData<StatEntry>()
    val entries: LiveData<StatEntry> = mutableEntries

    //Channel.CONFLATED
    private val actor = actor<Action>(UI) {
        for (action in this) when (action) {
            is BeginNumUsers -> {
                val statEntries = getNumUsersPerDay(action.year, action.month, action.app_package)

                Log.i("POINT", "PASSED ${statEntries.size}")
                for (entry in statEntries) {
                    Log.i("ENTRY", entry.day)

                    /*
                    * Update MutableLiveData with what we have just received from the backend
                    * */
                    mutableEntries.value = entry
                }
            }
        }
    }

    fun action(action: Action) = actor.offer(action)

    /*
    Preload data
    */
    //init { action(BeginNumUsers("2018", "8", "com.ingamedeo.eiriewebtext")) }

    /*
    This method will be called when this ViewModel is no longer used and will be destroyed.
    It is useful when ViewModel observes some data and you need to clear this subscription to prevent a leak of this ViewModel.
    */
    override fun onCleared() {
        actor.close()
    }

    private suspend fun getNumUsersPerDay(year: String, month: String, app_package: String): List<StatEntry> = Repository.getNumUsersPerDay(year, month, app_package)
}
