package com.onramp.android.takehome.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onramp.android.takehome.HackerNewsApp
import com.onramp.android.takehome.R
import com.onramp.android.takehome.model.StoriesAdapter
import com.onramp.android.takehome.model.Story
import kotlinx.android.synthetic.main.fragment_top_stories.*

class BookmarksFragment : Fragment(), BookmarksViewContract {

    lateinit var presenter: BookmarksPresenter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bookmarks, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = (activity?.application as HackerNewsApp).db
        presenter = BookmarksPresenter(this, db)
        presenter.refreshBookmarks()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_refresh) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun startLoading() {
        progressBar.visibility =  View.VISIBLE
    }

    override fun stopLoading() {
        progressBar.visibility =  View.INVISIBLE
    }

    override fun displayStoriesToUI(stories: List<Story>) {
        stories_recycler_view.visibility = View.VISIBLE
        errorTextView.visibility = View.INVISIBLE
        val listener = object : StoriesAdapter.Listener{
            override fun onBookmarkButtonClick(story: Story) {
                presenter.onBookmarkButtonClick(story)

            }
        }
        val adapter = StoriesAdapter(stories, listener)
        stories_recycler_view.adapter = adapter
    }

    override fun displayErrorMessage(msg: String) {
        stories_recycler_view.visibility = View.INVISIBLE
        errorTextView.visibility = View.VISIBLE
        errorTextView.text = msg    }
}