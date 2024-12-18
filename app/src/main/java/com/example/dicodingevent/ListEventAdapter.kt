package com.example.dicodingevent

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.ListEventAdapter.MyViewHolder.Companion.DIFF_CALLBACK
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.databinding.ItemEventBinding
import com.example.dicodingevent.ui.DetailEvent

class ListEventAdapter(private val context: Context) : ListAdapter<ListEventsItem, ListEventAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemEventBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(event: ListEventsItem) {
                Log.d("ListEventAdapter", "Event: ${event.name}")
                binding.tvNameEvent.text = event.name
                binding.tvBeginTime.text = event.beginTime
                binding.tvNameOwner.text = event.ownerName
                binding.tvEventQuota.text = event.quota.toString()
                Glide.with(binding.root.context)
                    .load(event.imageLogo)
                    .into(binding.imgPhotoItem)
            }

        companion object {
                val DIFF_CALLBACK = object :DiffUtil.ItemCallback<ListEventsItem>(){
                    override fun areItemsTheSame(
                        oldItem: ListEventsItem,
                        newItem: ListEventsItem
                    ): Boolean {
                        return oldItem == newItem
                    }

                    override fun areContentsTheSame(
                        oldItem: ListEventsItem,
                        newItem: ListEventsItem
                    ): Boolean {
                        return oldItem == newItem
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val eventLists = getItem(position)
        holder.bind(eventLists)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailEvent::class.java)
            intent.putExtra("ID", eventLists.id)
            intent.putExtra("NAME", eventLists.name)
            intent.putExtra("OWNER", eventLists.ownerName)
            intent.putExtra("DESCRIPTION", eventLists.description)
            intent.putExtra("QUOTA", eventLists.quota)
            intent.putExtra("IMAGE", eventLists.imageLogo)
            intent.putExtra("BEGIN", eventLists.beginTime)
            intent.putExtra("END", eventLists.endTime)
            intent.putExtra("REGISTRANTS", eventLists.registrants)
            intent.putExtra("LINK", eventLists.link)
            intent.putExtra("CATEGORY", eventLists.category)
            holder.itemView.context.startActivity(intent)
        }
    }
}