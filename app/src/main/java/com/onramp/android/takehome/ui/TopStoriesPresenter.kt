package com.onramp.android.takehome.ui
import com.onramp.android.takehome.datasource.RemoteDataSource
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

class TopStoriesPresenter(val view: TopStoriesViewContract) {

    fun refreshTopStories(){
        GlobalScope.launch(Dispatchers.Main) {
            view.startLoading()
            try {
                val topStories = RemoteDataSource.getTopStories()
                view.displayStoriesToUI(topStories)
            } catch(e: Exception){
                view.displayErrorMessage(e.toString())
            }
            view.stopLoading()
        }

    }
}