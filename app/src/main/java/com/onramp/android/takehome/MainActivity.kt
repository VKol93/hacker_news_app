package com.onramp.android.takehome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.onramp.android.takehome.datasource.RemoteDataSource
import kotlinx.coroutines.launch
import android.util.Log
import com.onramp.android.takehome.model.Story

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        lifecycleScope.launch {
            try {
                val topStoriesIDs = RemoteDataSource.retrofitService.getTopStoriesID()
                val topHundredStories = topStoriesIDs.take(100)
                val storyList:MutableList<Story> = mutableListOf()
                for (id in topHundredStories){
                    val story = RemoteDataSource.retrofitService.getStory(id)
                    storyList.add(story)
                }
                Log.d("_TAG", storyList.toString())
            }
            catch (e:Exception){
                //show error
                Log.d("_TAG", e.message)
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}