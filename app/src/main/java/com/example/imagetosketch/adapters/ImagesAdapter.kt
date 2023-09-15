package com.example.imagetosketch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.imagetosketch.databinding.ItemPhotosBinding

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.MyOwnHolder>() {

    private lateinit var binding: ItemPhotosBinding

    private val differCallback = object : ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDiffer: AsyncListDiffer<String> = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesAdapter.MyOwnHolder {
        binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyOwnHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImagesAdapter.MyOwnHolder, position: Int) {
    }

    override fun getItemCount(): Int {
       return 20
    }

    inner class MyOwnHolder(itemView: View) : ViewHolder(itemView) {

    }
}