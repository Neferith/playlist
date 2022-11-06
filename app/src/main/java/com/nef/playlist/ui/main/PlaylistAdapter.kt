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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.nef.playlist.R
import com.nef.playlist.data.model.PlaylistEntity
import kotlinx.android.synthetic.main.playlist_item.view.*


class PlaylistAdapter : ListAdapter<PlaylistEntity, PlaylistAdapter.PlaylistViewHolder>(
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
        holder.bind(item = getItem(position) as PlaylistEntity)
    }

    override fun getItemViewType(position: Int): Int = 1

    class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemLayout: ConstraintLayout = itemView.playlist_layout
        private val image:ImageView = view.task_item_ImageView_color
        private val title:TextView = view.task_item_TextView_description


        fun bind(item: PlaylistEntity) {

            // HACK : Les images ne chargent pas avec l'user agent android (erreur 410)
            // Ca passe en changeant le user agent et en demande une image .JPG.
            val url = GlideUrl(
                item.thumbnail+".JPG", LazyHeaders.Builder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                    .build()
            )

            title.text = item.title
            Glide.with(this.itemLayout).load(url)
                .placeholder(com.google.android.material.R.color.material_grey_300)
                .error(com.google.android.material.R.color.material_grey_300)
                .into(this.image)

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
