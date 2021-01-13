package com.onramp.android.takehome.ui.topstories

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.onramp.android.takehome.HackerNewsApp
import com.onramp.android.takehome.R
import com.onramp.android.takehome.model.StoriesAdapter
import com.onramp.android.takehome.model.Story
import com.onramp.android.takehome.ui.TopStoriesPresenter
import com.onramp.android.takehome.ui.TopStoriesViewContract
import kotlinx.android.synthetic.main.fragment_top_stories.*

class TopStoriesFragment : Fragment(), TopStoriesViewContract {
    lateinit var presenter: TopStoriesPresenter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_top_stories, container, false)
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = (activity?.application as HackerNewsApp).db
        presenter = TopStoriesPresenter(this, db)
        presenter.refreshTopStories()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when(id){
            R.id.action_refresh -> presenter.refreshTopStories()
        }
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
        errorTextView.text = msg
    }
}
