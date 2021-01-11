package com.onramp.android.takehome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.onramp.android.takehome.datasource.RemoteDataSource
import kotlinx.coroutines.launch
import android.view.View
import com.onramp.android.takehome.model.StoriesAdapter
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        lifecycleScope.launch {
            progressBar.visibility =  View.VISIBLE
            try {
                val topStories = RemoteDataSource.getTopStories()
                stories_recycler_view.visibility = View.VISIBLE
                errorTextView.visibility = View.INVISIBLE
                stories_recycler_view.adapter = StoriesAdapter(topStories)
            } catch(e: Exception){
                stories_recycler_view.visibility = View.INVISIBLE
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = e.toString()
            }
            progressBar.visibility =  View.INVISIBLE
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