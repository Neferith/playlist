package com.nef.playlist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nef.playlist.R
import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.ui.utils.ImageLoader
import kotlinx.android.synthetic.main.playlist_item.view.*


class PlaylistAdapter(val imageLoader: ImageLoader) :
    ListAdapter<PlaylistEntity,
            PlaylistAdapter.PlaylistViewHolder>(
    PlaylistDiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(item = getItem(position) as PlaylistEntity, imageLoader)
    }

    override fun getItemViewType(position: Int): Int = 1

    class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemLayout: ConstraintLayout = itemView.playlist_layout
        private val image:ImageView = view.task_item_ImageView_color
        private val title:TextView = view.task_item_TextView_description


        fun bind(item: PlaylistEntity,imageLoader: ImageLoader) {

            title.text = item.title
            imageLoader.loadImageInView(this.itemLayout,this.image,item.thumbnail)

        }
    }

    object PlaylistDiffCallback : DiffUtil.ItemCallback<PlaylistEntity>() {
        override fun areItemsTheSame(
            oldItem: PlaylistEntity,
            newItem: PlaylistEntity
        ): Boolean = (oldItem.id == newItem.id)

        override fun areContentsTheSame(
            oldItem: PlaylistEntity,
            newItem: PlaylistEntity
        ): Boolean = oldItem == newItem
    }

}
