package com.ingamedeo.appstats.activities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ingamedeo.appstats.BeginNumUsers
import com.ingamedeo.appstats.models.MainModel
import com.ingamedeo.appstats.R
import com.ingamedeo.appstats.adapters.StatAdapter
import com.ingamedeo.appstats.StatEntry
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    // lateinit -> I promise to init this later
    lateinit var model: MainModel

    private val statEntries: ArrayList<StatEntry> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        statEntriesRv.layoutManager = LinearLayoutManager(this)

        // Access the RecyclerView Adapter and load the data into it
        statEntriesRv.adapter = StatAdapter(statEntries, this)

        sampleButton.setOnClickListener { view ->

            //Clear all prev entries
            statEntries.clear()

            //Request data from the backend
            model.action(BeginNumUsers("2018", "8", "com.ingamedeo.eiriewebtext"))
        }

        initModel()
    }

    private fun initModel() {
        model = ViewModelProviders.of(this).get(MainModel::class.java)
        model.entries.observe { displayStatEntry(it ?: StatEntry(0, "")) }
    }

    private fun displayStatEntry(entry: StatEntry) {
        statEntries.add(StatEntry(entry.number, entry.day))
        statEntriesRv.adapter.notifyDataSetChanged()
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit)
            = observe(this@MainActivity, Observer { observe(it) })
}

/*

Moshi test

val testData = "[{\"number\":124,\"day\":\"10-08-2018\"}, {\"number\":124,\"day\":\"10-08-2018\"}]"
                val type = Types.newParameterizedType(List::class.java, StatEntry::class.java)
                val moshi = Moshi.Builder().build()
                val adapter: JsonAdapter<List<StatEntry>> = moshi.adapter(type)
                val statEntries: List<StatEntry>? = adapter.fromJson(testData)
*/
