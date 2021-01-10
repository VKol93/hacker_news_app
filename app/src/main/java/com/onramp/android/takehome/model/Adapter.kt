package com.onramp.android.takehome.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onramp.android.takehome.R
import kotlinx.android.synthetic.main.story_item.view.*

class Adapter(val story: List<Story>):RecyclerView.Adapter<StoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val viewHolder = StoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = story.size
}


class StoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(storyData: Story){
        //itemView.postTimeTextView.text.toLong() = storyData.time
        itemView.storyTitleTextView.text = storyData.title
        //itemView.sourceStoryTextView.text = storyData.type

    }
}