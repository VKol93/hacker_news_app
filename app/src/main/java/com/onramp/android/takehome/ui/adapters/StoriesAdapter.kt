package com.onramp.android.takehome.model

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onramp.android.takehome.R
import com.onramp.android.takehome.ui.utils.longToDateTime
import kotlinx.android.synthetic.main.story_item.view.*
import java.net.URL

class StoriesAdapter(val stories: List<Story>, val listener: Listener):RecyclerView.Adapter<StoryViewHolder>(){
    interface Listener{
        fun  onBookmarkButtonClick(story:Story)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        val viewHolder = StoryViewHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.itemView.bookmarkButton.setOnClickListener {
            listener.onBookmarkButtonClick(story)
        }
        holder.bind(story)
    }

    override fun getItemCount(): Int = stories.size
}


class StoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(storyData: Story){
        itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(storyData.url))
            itemView.context.startActivity(intent)
        }
        itemView.postTimeTextView.text = longToDateTime(storyData.time)
        itemView.storyTitleTextView.text = storyData.title
        if (storyData.url != null)
            itemView.sourceStoryTextView.text = URL(storyData.url).host
        if(storyData.isBookmarked)
            itemView.bookmarkButton.setBackgroundResource(R.drawable.ic_baseline_bookmark_24)
        else
            itemView.bookmarkButton.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24)
    }
}