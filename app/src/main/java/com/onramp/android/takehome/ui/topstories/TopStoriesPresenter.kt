package com.onramp.android.takehome.ui
import com.onramp.android.takehome.datasource.RemoteDataSource
import com.onramp.android.takehome.localsource.StoriesDB
import com.onramp.android.takehome.model.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

interface TopStoriesViewContract{
    fun startLoading()
    fun stopLoading()
    fun displayStoriesToUI(stories: List<Story>)
    fun displayErrorMessage(msg: String)
}

class TopStoriesPresenter(val view: TopStoriesViewContract, val db: StoriesDB) {

    fun refreshTopStories(){
        GlobalScope.launch(Dispatchers.Main) {
            view.startLoading()
            try {
                val topStories = RemoteDataSource.getTopStories()

                val bookmarkedStories = db.storiesDAO().getBookmarked()
                for(topStory in topStories)
                    if(bookmarkedStories.find { it.id == topStory.id} != null)
                        topStory.isBookmarked = true
                view.displayStoriesToUI(topStories)
            } catch(e: Exception){
                view.displayErrorMessage(e.message.toString())
            }
            view.stopLoading()
        }
    }

    private fun addBookmark(story:Story){
        GlobalScope.launch {
            story.isBookmarked = true
            db.storiesDAO().insertStory(story)
            refreshTopStories()
        }
    }
    private fun deleteBookmark(story: Story){
        GlobalScope.launch {
            story.isBookmarked = false
            db.storiesDAO().deleteStory(story)
            refreshTopStories()
        }
    }

    fun onBookmarkButtonClick(story:Story){
        if(story.isBookmarked)
            deleteBookmark(story)
        else
            addBookmark(story)
    }
}