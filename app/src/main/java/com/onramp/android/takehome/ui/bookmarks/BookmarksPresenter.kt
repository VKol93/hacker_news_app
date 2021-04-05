package com.onramp.android.takehome.ui.bookmarks
import com.onramp.android.takehome.localsource.StoriesDB
import com.onramp.android.takehome.model.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

interface BookmarksViewContract{
    fun startLoading()
    fun stopLoading()
    fun displayStoriesToUI(stories: List<Story>)
    fun displayErrorMessage(msg: String)
}

class BookmarksPresenter(val view: BookmarksViewContract, val db: StoriesDB) {
    fun refreshBookmarks(){
        GlobalScope.launch(Dispatchers.Main) {
            view.startLoading()
            try {
                val bookmarkedStories = db.storiesDAO().getBookmarked()
                view.displayStoriesToUI(bookmarkedStories)
            } catch(e: Exception){
                view.displayErrorMessage(e.toString())
            }
            view.stopLoading()
        }
    }
    private fun addBookmark(story:Story){
        GlobalScope.launch {
            story.isBookmarked = true
            db.storiesDAO().insertStory(story)
            refreshBookmarks()
        }
    }
    private fun deleteBookmark(story: Story){
        GlobalScope.launch {
            story.isBookmarked = false
            db.storiesDAO().deleteStory(story)
            refreshBookmarks()
        }
    }
    fun onBookmarkButtonClick(story:Story){
        if(story.isBookmarked)
            deleteBookmark(story)
        else
            addBookmark(story)
    }
}